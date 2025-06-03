//LOGIN Y REGISTER
const registerButton = document.getElementById('register-button');
const loginFront = document.getElementById('login-front');
const loginBack = document.getElementById('login-back');

registerButton.addEventListener('click', (event) => {
    event.preventDefault();
    loginFront.style.transform = 'rotateY(-180deg)';
    loginBack.style.transform = 'rotateY(0deg)';

});


(() => {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  const forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.from(forms).forEach(form => {
    form.addEventListener('submit', event => {
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
      }

      form.classList.add('was-validated')
    }, false)
  })
})()