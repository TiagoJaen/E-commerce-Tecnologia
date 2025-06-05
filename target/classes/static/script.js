//LOGIN Y REGISTER
const loginFront = document.getElementById('login-front');
const loginBack = document.getElementById('login-back');
const loginButton = document.getElementById('change-login-button');
const registerButton = document.getElementById('change-register-button');
const registerForm = document.getElementById('register-form');
const toastRegister = document.getElementById('fail-regiser-toast')
const toastRegisterBody = document.getElementById('fail-register-toast-body')

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

    console.log(JSON.stringify(clientData))

    const response = await fetch('/clients', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(clientData)
    });

    if (response.ok) {
        alert('Usuario registrado correctamente');
    } else {
        const errorData = await response.json();
        console.error('Error al registrar el usuario:', errorData);
    }
});
