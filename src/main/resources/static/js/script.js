document.addEventListener('DOMContentLoaded', function() {
  var wishlistButtons = document.querySelectorAll('.wishlist-button');
  wishlistButtons.forEach(function(button) {
    button.addEventListener('click', function() {
      button.classList.toggle('clicked');
    });
  });
});
