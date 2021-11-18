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

  /** Auto fill the permalink field when add new post or edit post */
  $('input[name=title]').blur(function (e) { 
    e.preventDefault();
    let urlstring = $('input[name=title]').val();
    $('input[name=postUrl]').val(slugify(urlstring));
  });

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

  /** Changing Vietnamese character to non-accent */
  function slugify(text) {
  const from = "ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ·/_,:;"
  const to = "AAAAEEEIIOOOOUUADIUOaaaaeeeiioooouuadiuoUAAAAAAAAAAAAEEEEEEuaaaaaaaaaaaaeeeeeeEEIIOOOOOOOOOOOOUUUUeeiioooooooooooouuuuUUUYYYYYuuuyyyy------"

  for (var i = 0, len = from.length; i < len; i++) {
    text = text.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
  }

  return text
    .toString()                     // Cast to string
    .toLowerCase()                  // Convert the string to lowercase letters
    .trim()                         // Remove whitespace from both sides of a string
    .replace(/\s+/g, '-')           // Replace spaces with -
    .replace(/&/g, '-y-')           // Replace & with 'and'
    .replace(/[^\w\-]+/g, '')       // Remove all non-word chars
    .replace(/\-\-+/g, '-');        // Replace multiple - with single -
}
