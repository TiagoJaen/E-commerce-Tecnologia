//LOGIN Y REGISTER
const loginFront = document.getElementById('login-front');
const loginBack = document.getElementById('login-back');
const loginButton = document.getElementById('change-login-button');
const registerButton = document.getElementById('change-register-button');
const registerForm = document.getElementById('register-form');
const toastRegisterFail = document.getElementById('fail-register-toast');
const toastRegisterBody = document.getElementById('fail-register-toast-body');
const toastRegisterSuccess = document.getElementById('success-register-toast');

const loginForm = document.getElementById('sign-in-form');
const loginErrorDiv = document.getElementById('login-error');

registerButton.addEventListener('click', (e) => {
    e.preventDefault();
    loginFront.style.transform = 'rotateY(-180deg)';
    loginBack.style.transform = 'rotateY(0deg)';
});

loginButton.addEventListener('click', (e) => {
    e.preventDefault();
    loginFront.style.transform = 'rotateY(0deg)';
    loginBack.style.transform = 'rotateY(180deg)';
});

//REGISTRAR
registerForm.addEventListener('submit', async (e) => {
    e.preventDefault();
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

//LOGIN
if (loginForm) {
    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const usernameInput = loginForm.querySelector('input[name="username"]');
        const passwordInput = loginForm.querySelector('input[name="password"]');

        const username = usernameInput.value;
        const password = passwordInput.value;

        try {
            const response = await fetch('/auth/login', { //JWT login endpoint
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });

            if (response.ok) {
                const data = await response.json();
                const jwtToken = data.token;

                localStorage.setItem('jwtToken', jwtToken);

                if (loginErrorDiv) {
                    loginErrorDiv.style.display = 'none';
                }

                window.location.href = '/';
            } else {
                const errorData = await response.json().catch(() => ({ message: 'Error de autenticación' }));
                console.error('Login failed:', errorData);
                if (loginErrorDiv) {
                    loginErrorDiv.textContent = errorData.message || 'Usuario o contraseña incorrectos';
                    loginErrorDiv.style.display = 'block';
                }
            }
        } catch (error) {
            if (loginErrorDiv) {
                loginErrorDiv.textContent = 'Usuario o contraseña incorrectos.';
                loginErrorDiv.style.display = 'block';
            }
        }
    });
}