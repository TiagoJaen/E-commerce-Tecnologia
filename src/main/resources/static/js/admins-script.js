const adminTableBody = document.getElementById('admins-table-body');
const paginationContainer = document.querySelector(".pagination-container");
const addAdminForm = document.getElementById('add-admin-form');
const modifyAdminForm = document.getElementById('modify-admin-form');
const successToastBody = document.getElementById('success-admin-toast-body');
const successToast = document.getElementById('success-admin-toast');
const failToastHeader = document.getElementById('fail-admin-toast-header');
const failToastBody = document.getElementById('fail-admin-toast-body');
const failToast = document.getElementById('fail-admin-toast');
let timeout = null;
let pageSize = 5;

// Cargar administradores a la tabla
cargarAdministradores();

function cargarAdministradores(page = 0) {
    authFetch(`/admins/paginated?page=${page}&size=${pageSize}`)
    .then(res => res.json())
    .then(data => {
        if (!data.content || data.content.length === 0) {
            adminTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun administrador.</td></tr>`;
        }else{
            adminTableBody.innerHTML = "";
            paginationContainer.innerHTML = "";
            // Imprimir administrador
            data.content.forEach(p => imprimirAdmin(p));

            //Navegacion de paginacion
            // Botón anterior
            const prevBtn = document.createElement("li");
            prevBtn.className = `page-item ${page === 0 ? 'disabled' : ''}`;
            prevBtn.innerHTML = `<a class="page-link" href="#">Anterior</a>`;
            prevBtn.addEventListener('click', (e) => {
                e.preventDefault();
                if (page > 0) {
                    const nuevaPagina = page - 1;
                    window.history.pushState({}, '', `?page=${nuevaPagina}`);
                    cargarAdministradores(nuevaPagina);
                }
            });
            paginationContainer.appendChild(prevBtn);

            // Botones de número de página
            const maxButtons = 5;
            let startPage = Math.max(0, page - 2);
            let endPage = startPage + maxButtons;

            if (endPage >= data.totalPages) {
                endPage = data.totalPages;
                startPage = Math.max(0, endPage - maxButtons);
            }
            for (let i = startPage;i < endPage ; i++) {
                const li = document.createElement('li');
                li.className = `page-item ${i === page ? 'active' : ''}`;
                li.innerHTML = `<a class="page-link" href="#">${i + 1}</a>`;
                li.addEventListener('click', (e) => {
                    e.preventDefault();
                    const nuevaPagina = i;
                    window.history.pushState({}, '', `?page=${nuevaPagina}`);
                    cargarAdministradores(nuevaPagina);
                });
                paginationContainer.appendChild(li);
            }

            // Botón siguiente
            const nextBtn = document.createElement('li');
            nextBtn.className = `page-item ${page === data.totalPages - 1 ? 'disabled' : ''}`;
            nextBtn.innerHTML = `<a class="page-link" href="#">Siguiente</a>`;
            nextBtn.addEventListener('click', (e) => {
                e.preventDefault();
                if (page < data.totalPages - 1) {
                    const nuevaPagina = page + 1;
                    window.history.pushState({}, '', `?page=${nuevaPagina}`);
                    cargarAdministradores(nuevaPagina);
                }
            });
            paginationContainer.appendChild(nextBtn);
        }
    });
}

//Barra de busqueda
document.getElementById('admins-search-bar').addEventListener('input', function(){
    clearTimeout(timeout);
    timeout = setTimeout(() => {
        const username = this.value.trim();
         // Regex: solo letras, números, espacios, tildes y ñ
        const validName = /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\s]*$/;
        if (username.length === 0 || !validName.test(username)) {
          cargarAdministradores();
          return
        }
        authFetch(`/admins/username/${encodeURIComponent(username)}`)
        .then(response => response.json())
        .then(admins => {
            if (!admins || admins.length === 0) {
                adminTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun administrador.</td></tr>`;
            }else{
                adminTableBody.innerHTML = '';

                admins.forEach(p => {
                    imprimirAdmin(p);
                });
            }
        });
    }, 800);
});

//Funcion para imprimir administrador
function imprimirAdmin(p){
    const tr = document.createElement('tr');
    tr.classList.add('admin-table-item', 'crud-table-item');
    tr.innerHTML = `<td class="admin-table-id">${p.id}</td>
                    <td class="admin-table-name">${p.name}</td>
                    <td class="admin-table-lastname">${p.lastname}</td>
                    <td class="admin-table-username">${p.username}</td>
                    <td class="admin-table-email">${p.email}</td>
                    <td class="admin-table-phone">${p.phone}</td>
                    <td class="crud-table-actions">
                        <button class="modify-admin-btn modify-crud-btn" data-bs-toggle="modal" data-bs-target="#modal-modify-admin">Modificar</button>
                        <button class="delete-admin-btn delete-crud-btn" data-id="${p.id}">Eliminar</button>
                    </td>`;
    adminTableBody.appendChild(tr);
}

// Funciones de botones
adminTableBody.addEventListener('click', (e) => {
    const btn = e.target;

    //Boton de modificar
    if (btn.classList.contains('modify-admin-btn')) {
        const row = btn.closest('tr');
        document.getElementById('admin-id-input').value = row.querySelector('.admin-table-id').innerText;
        document.getElementById('admin-name-input').value = row.querySelector('.admin-table-name').innerText;
        document.getElementById('admin-lastname-input').value = row.querySelector('.admin-table-lastname').innerText;
        document.getElementById('admin-username-input').value = row.querySelector('.admin-table-username').innerText;
        document.getElementById('admin-email-input').value = row.querySelector('.admin-table-email').innerText;
        document.getElementById('admin-phone-input').value = row.querySelector('.admin-table-phone').innerText.trim();
        document.getElementById('admin-password-input').value = '';
    }

    //Boton de eliminar
    if (btn.classList.contains('delete-admin-btn')) {
        const id = btn.dataset.id;
        if (confirm("¿Estás seguro de que querés eliminar este administrador?")) {
            authFetch(`/admins/id/${id}`, { method: 'DELETE' })
            .then(async response => {
                if (!response.ok) {
                    toastFail("Error al eliminar administrador", await response.text());
                } else {
                    const urlParams = new URLSearchParams(window.location.search);
                    const currentPage = urlParams.get('page') || 0;
                    cargarAdministradores(currentPage);
                    toastSuccess("Admin eliminado correctamente");
                }
            });
        }
    }
});


// Formulario para modificar administrador
modifyAdminForm.addEventListener('submit', async(e)=>{
    e.preventDefault();
    const formData = new FormData(modifyAdminForm);
    const adminData = {
        "id": parseInt(document.getElementById('admin-id-input').value),
        "name": formData.get('name'),
        "lastname": formData.get('lastname'),
        "email": formData.get('email'),
        "phone": formData.get('phone'),
        "username": formData.get('username'),
        "password": formData.get('password')
    };

    authFetch('/admins', {
        method : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(adminData)
    })
    .then(async response => {
        if(!response.ok){
            toastFail("  Error al modificar el administrador", await response.text());
        }else{
            const urlParams = new URLSearchParams(window.location.search);
            const currentPage = urlParams.get('page') || 0;
            let modal = document.getElementById('modal-modify-admin');
            bootstrap.Modal.getInstance(modal).hide();

            cargarAdministradores(currentPage);
            toastSuccess("Admin modificado correctamente");
        }
    })
})

// Formulario para agregar administrador
addAdminForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(addAdminForm);
    const adminData = {
        name: formData.get('name'),
        lastname: formData.get('lastname'),
        email: formData.get('email'),
        phone: formData.get('phone'),
        username: formData.get('username'),
        password: formData.get('password')
    };
    authFetch('/admins', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(adminData)
    })
    .then(async response => {
        if (!response.ok) {
            toastFail("Error al agregar administrador", await response.text())
        }else{
            let modal = document.getElementById('modal-add-admin');
            bootstrap.Modal.getInstance(modal).hide();

            addAdminForm.reset();
            cargarAdministradores();
            toastSuccess("Admin agregado correctamente");
        }
    });
});
//Funcion para toast
function toastSuccess(msg){
    successToastBody.innerText = msg;
    failToast.classList.remove('show');
    successToast.classList.remove('show');
    successToast.classList.add('show');

    setTimeout(() => {
        successToast.classList.remove('show');
    },5000)
}
function toastFail(msg, response){
    failToast.classList.remove('show');
    failToastHeader.innerText = msg;
    failToastBody.innerText = response;
    
    successToast.classList.remove('show');
    failToast.classList.add('show');

    setTimeout(() => {
        failToast.classList.remove('show');
    },5000)
}