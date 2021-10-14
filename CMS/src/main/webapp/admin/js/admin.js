$(document).ready(function() {

  setTimeout(function(){
        $("#message").hide();
        $("#error").hide();
        }, 5000);
  
  $("#select-all").click(function() {
    $('.select-item').not(this).prop('checked', this.checked)
  });
  (function() {
    'use strict'
    feather.replace()
  })();
});