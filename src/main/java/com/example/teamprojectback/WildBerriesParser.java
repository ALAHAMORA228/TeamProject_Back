package com.example.teamprojectback;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
class WildBerriesParser {
    private List<Map<String, Object>> productCards = new ArrayList<>();
    private final String localCataloguePath = "wb_catalogue.json";
    private final String url = "https://static-basket-01.wb.ru/vol0/data/main-menu-ru-ru-v2.json";

    public String downloadCurrentCatalogue() {
        if (!fileExistsOrUpToDate(localCataloguePath)) {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            saveCatalogueToFile(response, localCataloguePath);
        }
        return localCataloguePath;
    }

    private boolean fileExistsOrUpToDate(String filePath) {
        File file = new File(filePath);
        LocalDate fileLastModifiedDate = LocalDate.ofEpochDay(file.lastModified() / 86_400_000); // Convert milliseconds to days
        LocalDate currentDate = LocalDate.now();
        return !file.exists() || fileLastModifiedDate.isAfter(currentDate);
    }

    private void saveCatalogueToFile(String content, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<JsonNode> traverseJson(JsonNode parentCategory) {
        List<JsonNode> flattenedCatalogue = new ArrayList<>();
        if (parentCategory.isArray()) {
            for (JsonNode category : parentCategory) {
                try {
                    JsonNode categoryNode = new ObjectMapper().createObjectNode()
                            .put("name", category.get("name").asText())
                            .put("url", category.get("url").asText())
                            .put("shard", category.get("shard").asInt())
                            .put("query", category.get("query").asText());
                    flattenedCatalogue.add(categoryNode);
                } catch (NullPointerException ignored) {
                    // Handle missing key exception
                }
                if (category.has("childs")) {
                    JsonNode childCategories = category.get("childs");
                    flattenedCatalogue.addAll(traverseJson(childCategories));
                }
            }
        }
        return flattenedCatalogue;
    }

    public List<JsonNode> processCatalogue(String localCataloguePath) {
        List<JsonNode> catalogue = new ArrayList<>();
        try {
            JsonNode root = new ObjectMapper().readTree(new File(localCataloguePath));
            catalogue.addAll(traverseJson(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return catalogue;
    }

    public List<Object> extractCategoryData(List<Map<String, Object>> catalogue, String userInput) {
        List<Object> categoryData = new ArrayList<>();
        for (Map<String, Object> category : catalogue) {
            if (userInput.endsWith(category.get("url").toString()) || userInput.equals(category.get("name").toString())) {
                categoryData.add(category.get("name"));
                categoryData.add(category.get("shard"));
                categoryData.add(category.get("query"));
                break;
            }
        }
        return categoryData;
    }

    public List<Map<String, Object>> getProductsOnPage(Map<String, Object> pageData) {
        List<Map<String, Object>> productsOnPage = new ArrayList<>();

        // Первый вызов get, затем необходимо привести к типу соответствующему вложенному объекту
        Map<String, Object> data = (Map<String, Object>) pageData.get("data");

        if (data != null && data.containsKey("products")) {
            List<Map<String, Object>> products = (List<Map<String, Object>>) data.get("products");

            if (products != null) {
                for (Map<String, Object> item : products) {
                    Map<String, Object> productData = Map.of(
                            "Ссылка", "https://www.wildberries.ru/catalog/" + item.get("id") + "/detail.aspx",
                            "Артикул", item.get("id"),
                            "Наименование", item.get("name"),
                            "Бренд", item.get("brand"),
                            "ID бренда", item.get("brandId"),
                            "Цена", Math.toIntExact((long) item.get("priceU") / 100),
                            "Цена со скидкой", Math.toIntExact((long) item.get("salePriceU") / 100),
                            "Рейтинг", item.get("rating"),
                            "Отзывы", item.get("feedbacks")
                    );
                    productsOnPage.add(productData);
                }
            }
        }

        return productsOnPage;
    }


    public boolean addDataFromPage(String url) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> pageData = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> products = getProductsOnPage(pageData);

        if (!products.isEmpty()) {
            productCards.addAll(products);
            System.out.println("Added products: " + products.size());
        } else {
            System.out.println("Product loading completed");
            return true;
        }
        return false;
    }

    public void getAllProductsInCategory(String category, String shard, String query) {
        for (int page = 1; page <= 100; page++) {
            System.out.println("Loading products from page: " + page);
            String url = "https://catalog.wb.ru/catalog/" + shard + "/catalog";
            url += "?appType=1&" + query + "&curr=rub&dest=-1257786&page=" + page + "&sort=popular&spp=24";

            if (addDataFromPage(url)) {
                break;
            }
        }
    }



    private String directory;
    private LocalDate runDate;

    public void getSalesData() {
        for (Map<String, Object> card : productCards) {
            String url = "https://product-order-qnt.wildberries.ru/by-nm/?nm=" + card.get("Артикул");
            RestTemplate restTemplate = new RestTemplate();
            try {
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);
                card.put("Продано", response.get("qnt"));
            } catch (Exception e) {
                card.put("Продано", "нет данных");
            }
            System.out.println("Собрано карточек: " + (productCards.indexOf(card) + 1) + " из " + productCards.size());
        }
    }

//    public String saveToExcel(String fileName) {
//        String resultPath = directory + fileName + "_" + runDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".xlsx";
//        try (FileOutputStream fileOut = new FileOutputStream(resultPath)) {
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("Data");
//
//            int rowNum = 0;
//            for (Map<String, Object> card : productCards) {
//                Row row = sheet.createRow(rowNum++);
//                int cellNum = 0;
//                for (String key : card.keySet()) {
//                    Cell cell = row.createCell(cellNum++);
//                    cell.setCellValue(card.get(key).toString());
//                }
//            }
//
//            workbook.write(fileOut);
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return resultPath;
//    }

    public void getProductsInSearchResult(String keyword) {
        for (int page = 1; page <= 100; page++) {
            System.out.println("Loading products from page: " + page);
            String formattedKeyword = keyword.replaceAll("\\s", "%20");
            String url = String.format("https://search.wb.ru/exactmatch/ru/common/v4/search?appType=1&curr=rub&dest=-1257786&page=%d&query=%s&resultset=catalog&sort=popular&spp=24&suppressSpellcheck=false", page, formattedKeyword);
            if (addDataFromPage(url)) {
                break;
            }
        }
    }

    public void runParser() {
//        Scanner scanner = Scanner(System.in);
//        System.out.println("Введите 1 для парсинга категории целиком, 2 — по ключевым словам: ");
//        String mode = scanner.nextLine();
//
//        if (mode.equals("1")) {
//
//        }
//
//        if (mode.equals("2")) {
//            System.out.print("Введите запрос для поиска: ");
//            String keyword = scanner.nextLine();
//            getAllProductsInSearchResult(keyword);
//            getSalesData();
//            System.out.println("Данные сохранены в: " + saveToExcel(keyword));
//        }
//        scanner.close();
        Scanner scanner = new Scanner(System.in);

        String localCataloguePath = downloadCurrentCatalogue();
        System.out.println("Каталог сохранен: " + localCataloguePath);
        List<Map<String, Object>> processedCatalogue = processCatalogue(localCataloguePath);
        System.out.print("Введите название категории или ссылку: ");
        String inputCategory = scanner.nextLine();
        Map<String, Object> categoryData = extractCategoryData(processedCatalogue, inputCategory);

        if (categoryData == null) {
            System.out.println("Категория не найдена!");
        } else {
            System.out.println("Найдена категория: " + categoryData.get("name"));
        }

        getAllProductsInCategory(categoryData);
        getSalesData();
        System.out.println("ФИНИШ");
    }
}
