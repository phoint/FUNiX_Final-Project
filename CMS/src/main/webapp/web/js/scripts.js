$(document).ready(function() {
  'use strict';
  // Scripts for validating form input
  $('#newPwd').on('change', function() {
    $('#newPwd-retype').attr('pattern', $(this).val())
  });
  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.getElementsByClassName('needs-validation');
  // Loop over them and prevent submission
  var validation = Array.prototype.filter.call(forms, function(form) {
    form.addEventListener('submit', function(event) {
      if (form.checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
      }

      form.classList.add('was-validated');
    }, false);
  });
  
  // Scripts for interacting with comment & reply box
  $('#reset-button').click(function(e) {
    $('.textarea').val('');
  });
  $('#new-comment').focus(function(e) {
    $('.reply-comment-block').hide();
  });
  $('.reply-comment-block').hide();
  $('.reply-button').click(function() {
    $('.reply-comment-block').hide();
    $(this).closest('.row').siblings('.reply-comment-block').show('slow');
  });
  $('.reset-reply-comment').click(function(e) {
    $('.textarea').val('');
    $(this).closest('.reply-comment-block').hide();
  });
});