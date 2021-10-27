$(document).ready(function() {

  /** Set timeout for displaying message or error */
  setTimeout(function() {
    $("#message").hide();
    $("#error").hide();
  }, 10000);

  /** Select All checkbox */
  $("#select-all").click(function() {
    $('.select-item').not(this).prop('checked', this.checked)
  });

  /** Bulk approving for comment management (pending) */
  // $(".comment-approve, .comment-unapprove").on("click", function() {
  //   $(this).siblings('input[type=hidden]').each().prop('form', 'multiselect');
  //   $('#multiselect').submit();
  //   $(this).siblings('input[type=hidden]').each().removeProp('form');
  // });

  /** Form Validation */
  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.getElementsByClassName('needs-validation');
  // Loop over them and prevent submission
  var validation = Array.prototype.filter.call(forms, function(form) {
    form.addEventListener('submit', function(event) {
      if (form.checkValidity() === false) {
        event.preventDefault();
        // event.stopPropagation();
      }
      form.classList.add('was-validated');
    }, false);
  });

  /** Using feather icon */
  (function() {
    'use strict'
    feather.replace()
  })();
});
