//LOGIN Y REGISTER
const registerButton = document.getElementById('register-button');
const loginFront = document.getElementById('login-front');
const loginBack = document.getElementById('login-back');

registerButton.addEventListener('click', (event) => {

    event.preventDefault();

    loginFront.style.transform = 'rotateY(-180deg)';

    loginBack.style.transform = 'rotateY(0deg)';

});
