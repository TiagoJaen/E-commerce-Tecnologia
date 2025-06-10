//LOGIN Y REGISTER
const loginFront = document.getElementById('login-front');
const loginBack = document.getElementById('login-back');
const loginButton = document.getElementById('change-login-button');
const registerButton = document.getElementById('change-register-button');
const registerForm = document.getElementById('register-form');
const toastRegisterFail = document.getElementById('fail-register-toast');
const toastRegisterBody = document.getElementById('fail-register-toast-body');
const toastRegisterSuccess = document.getElementById('success-register-toast');

registerButton.addEventListener('click', (event) => {
    event.preventDefault();
    loginFront.style.transform = 'rotateY(-180deg)';
    loginBack.style.transform = 'rotateY(0deg)';
});

loginButton.addEventListener('click', (event) => {
    event.preventDefault();
    loginFront.style.transform = 'rotateY(0deg)';
    loginBack.style.transform = 'rotateY(180deg)';
});

//REGISTRAR
registerForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const formData = new FormData(registerForm);
    const clientData = {
        name: formData.get('name'),
        lastname: formData.get('lastname'),
        email: formData.get('email'),
        phone: formData.get('phone'),
        username: formData.get('username'),
        password: formData.get('password')
    };

    const response = await fetch('/clients', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(clientData)
    })
    .then(async response => {
        if(!response.ok){
            toastRegisterFail.classList.remove('show');

            const responseBody = await response.text();
            toastRegisterBody.innerText = responseBody;
            
            toastRegisterSuccess.classList.remove('show');
            toastRegisterFail.classList.add('show');
        }else{
            toastRegisterFail.classList.remove('show');
            toastRegisterSuccess.classList.remove('show');
            toastRegisterSuccess.classList.add('show');
            loginFront.style.transform = 'rotateY(0deg)';
            loginBack.style.transform = 'rotateY(180deg)';
        }
    })
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