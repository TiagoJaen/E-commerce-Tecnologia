//LOGIN Y REGISTER
const loginFront = document.getElementById('login-front');
const loginBack = document.getElementById('login-back');
const loginButton = document.getElementById('change-login-button');
const registerButton = document.getElementById('change-register-button');
const registerForm = document.getElementById('register-form');
const toastRegisterFail = document.getElementById('fail-register-toast');
const toastRegisterBody = document.getElementById('fail-register-toast-body');
const toastRegisterSuccess = document.getElementById('success-register-toast');

// New: Get the login form and error display element
const loginForm = document.getElementById('sign-in-form');
const loginErrorDiv = document.getElementById('login-error'); // Assuming this is for displaying login errors

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

// --- NEW LOGIN LOGIC ---
if (loginForm) {
    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault(); // Stop the default form submission!

        const usernameInput = loginForm.querySelector('input[name="username"]');
        const passwordInput = loginForm.querySelector('input[name="password"]');

        const username = usernameInput.value;
        const password = passwordInput.value;

        try {
            const response = await fetch('/auth/login', { // Your JWT login endpoint
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' // Crucial: send as JSON
                },
                body: JSON.stringify({ // Convert data to JSON string
                    username: username,
                    password: password
                })
            });

            if (response.ok) {
                const data = await response.json();
                const jwtToken = data.token;
                console.log('JWT Token recibido:', jwtToken);
                console.log('Login successful! JWT Token:', jwtToken);

                // Store the token (e.g., in localStorage) for future authenticated requests
                localStorage.setItem('jwtToken', jwtToken);

                // Hide error message if it was visible
                if (loginErrorDiv) {
                    loginErrorDiv.style.display = 'none';
                }

                // Redirect to a protected page, or update the UI to show logged-in state
                window.location.href = '/'; // Example: redirect to homepage
            } else {
                // Login failed (e.g., 401 Unauthorized, 403 Forbidden, etc.)
                const errorData = await response.json().catch(() => ({ message: 'Error de autenticación' }));
                console.error('Login failed:', errorData);
                if (loginErrorDiv) {
                    loginErrorDiv.textContent = errorData.message || 'Usuario o contraseña incorrectos';
                    loginErrorDiv.style.display = 'block';
                }
            }
        } catch (error) {
            console.error('Network or unexpected error during login:', error);
            if (loginErrorDiv) {
                loginErrorDiv.textContent = 'Ocurrió un error de conexión. Inténtalo de nuevo.';
                loginErrorDiv.style.display = 'block';
            }
        }
    });
}

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