//LOGIN Y REGISTER
const loginFront = document.getElementById('login-front');
const loginBack = document.getElementById('login-back');
const registerButton = document.getElementById('register-button');
const registerForm = document.getElementById('register-form');

registerButton.addEventListener('click', (event) => {
    event.preventDefault();
    loginFront.style.transform = 'rotateY(-180deg)';
    loginBack.style.transform = 'rotateY(0deg)';

});

registerForm.addEventListener('submit', (event) => {
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

    const response = fetch('/clientes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(clientData)
    });
});