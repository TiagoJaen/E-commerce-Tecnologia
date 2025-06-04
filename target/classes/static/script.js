//LOGIN Y REGISTER
const loginFront = document.getElementById('login-front');
const loginBack = document.getElementById('login-back');
const loginButton = document.getElementById('change-login-button');
const registerButton = document.getElementById('change-register-button');
const registerForm = document.getElementById('register-form');

loginButton.addEventListener('click', (event) => {
    event.preventDefault();
    loginFront.style.transform = 'rotateY(0deg)';
    loginBack.style.transform = 'rotateY(180deg)';
});

registerButton.addEventListener('click', (event) => {
    event.preventDefault();
    loginFront.style.transform = 'rotateY(-180deg)';
    loginBack.style.transform = 'rotateY(0deg)';

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

    try {
        const response = await fetch('/clients', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(clientData)
        });

        if (response.ok) {
            alert('✅ Registro exitoso');
            registerForm.reset();
            console.log('Registro exitoso:', clientData);
        } else {
            const error = await response.text();
            alert('⚠️ ' + error);
        }
    } catch (err) {
        alert('❌ Error de conexión');
        console.error(err);
    }
});