$(document).ready(function() {

  setTimeout(function(){
        $("#message").hide();
        $("#error").hide();
        }, 5000);
  
  $("#select-all").click(function() {
    $('.select-item').not(this).prop('checked', this.checked)
  });
  
  $(".comment-approve, .comment-unapprove").on("click", function () {
    $(this).siblings('input[type=hidden]').each().prop('form', 'multiselect');
    $('#multiselect').submit();
    $(this).siblings('input[type=hidden]').each().removeProp('form');
});
  
  (function() {
    'use strict'
    feather.replace()
  })();
});