const profileContainer = document.getElementById('profile-container');
const successToastBody = document.getElementById('success-profile-toast-body');
const successToast = document.getElementById('success-profile-toast');
const failToastHeader = document.getElementById('fail-profile-toast-header');
const failToastBody = document.getElementById('fail-profile-toast-body');
const failToast = document.getElementById('fail-profile-toast');

cargarDatos();

async function cargarDatos(){
    const response = await fetch('/user', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        });
        if (!response.ok) {
            profileContainer.innerHTML = `<h2 style="color:red;">Error al cargar datos.</h2>`;
            return;
        }

    const user = await response.json();
    profileContainer.innerHTML = 
        `<h3 class="title">PERFIL</h3>
            <form class="row g-3 client-form justify-content-center text-center" id="modify-profile-form">
                <div class="col-md-5">
                    <label class="form-label">Nombre</label>
                    <input type="text" value="${user.name}" class="form-control profile-input" id="profile-name-input" placeholder="Nombre" name="name" required>
                </div>
                <div class="col-md-5">
                    <label class="form-label">Apellido</label>
                    <input type="text" value="${user.lastname}" class="form-control profile-input" id="profile-lastname-input" placeholder="Apellido" name="lastname" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Correo electrónico</label>
                    <input type="email" value="${user.email}" class="form-control profile-input" id="profile-email-input" placeholder="Email" name="email" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Teléfono</label>
                    <div class="input-group">
                        <input type="text" value="${user.phone}" class="form-control profile-input" id="profile-phone-input"
                        pattern="[0-9]{10}"
                        title="El teléfono debe tener el formato de Argentina(10 números)." placeholder="2238452914" name="phone" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Usuario</label>
                    <input type="text" value="${user.username}" class="form-control profile-input" id="profile-username-input" placeholder="Usuario" name="username" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Contraseña</label>
                    <input type="password" class="form-control profile-input" id="profile-password-input"
                    pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_=+\-\.]).{8,15}$"
                    title="La contraseña debe tener entre 8 y 15 caracteres, al menos una letra mayúscula, una minúscula y un carácter especial (!@#$%^&*_=+-.)" placeholder="••••••••••••" name="password">
                </div>
                <div class="col-md-12">
                    <p style="color: #bffff6;">Dejar la contraseña vacía si no se quiere cambiar.</p>
                </div>
                <div class="profile-form-buttons col-8 d-flex flex-column justify-content-around align-items-center ">
                    <input class="btn-style-3" type="submit" value="Modificar datos"></input>
                </div>
            </form>
            <div id="delete-account-control" class="col-md-12 text-center">
                <input type="password" class="hidden form-control profile-input m-auto" id="delete-account-password" placeholder="••••••••••••" required>
                <p style="color: #bffff6;" class="hidden mt-2 mb-0" id="delete-account-advice">Por favor, confirme su contraseña para continuar.</p>
                <button id="delete-user-btn" class="mt-2">Darse de baja</button>
            </div>`;

    document.getElementById('delete-user-btn').addEventListener('click', (e)=>{
    const passwordInput = document.getElementById('delete-account-password');
    passwordInput.classList.remove('hidden');
    document.getElementById('delete-account-advice').classList.remove('hidden');
    const password = passwordInput.value;

    if(password.trim()){
        fetch(`/user/${password}`, {
            method : 'DELETE'
        })
        .then(async response =>{
            if(!response.ok){
                toastFail("  Error al darse de baja", await response.text());
            }else{
                window.location.replace('/login.html');
            }
        })
    }
    });
        
    const modifyProfileForm = document.getElementById('modify-profile-form');
    modifyProfileForm.addEventListener('submit', (e) =>{
        e.preventDefault();
        const formData = new FormData(modifyProfileForm);
        const userData = {
            "id": user.id,
            "name": formData.get('name'),
            "lastname": formData.get('lastname'),
            "email": formData.get('email'),
            "phone": formData.get('phone'),
            "username": formData.get('username'),
            "password": formData.get('password')
        };

        fetch('/user', {
            method : 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        })
        .then(async response => {
            if(!response.ok){
                toastFail("  Error al modificar datos", await response.text());
            }else{
                location.reload();
            }
        })
    });
}

//Funcion para toast
function toastSuccess(msg){
    successToastBody.innerText = msg;
    failToast.classList.remove('show');
    successToast.classList.remove('show');
    successToast.classList.add('show');

    setTimeout(() => {
        successToast.classList.remove('show');
    },4000)
}
function toastFail(msg, response){
    failToast.classList.remove('show');
    failToastHeader.innerText = msg;
    failToastBody.innerText = response;
    
    successToast.classList.remove('show');
    failToast.classList.add('show');

    setTimeout(() => {
        failToast.classList.remove('show');
    },4000)
}