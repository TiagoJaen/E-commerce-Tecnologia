const managerTableBody = document.getElementById('managers-table-body');
const paginationContainer = document.querySelector(".pagination-container");
const addManagerForm = document.getElementById('add-manager-form');
const modifyManagerForm = document.getElementById('modify-manager-form');
const successToastBody = document.getElementById('success-manager-toast-body');
const successToast = document.getElementById('success-manager-toast');
const failToastHeader = document.getElementById('fail-manager-toast-header');
const failToastBody = document.getElementById('fail-manager-toast-body');
const failToast = document.getElementById('fail-manager-toast');
let timeout = null;
let pageSize = 5;

// Cargar gestores a la tabla
cargarGestores();

function cargarGestores(page = 0) {
    authFetch(`/managers/paginated?page=${page}&size=${pageSize}`)
    .then(res => res.json())
    .then(data => {
        if (!data.content || data.content.length === 0) {
            managerTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun gestor.</td></tr>`;
        }else{
            managerTableBody.innerHTML = "";
            paginationContainer.innerHTML = "";
            // Imprimir gestores
            data.content.forEach(p => imprimirGestor(p));

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
                    cargarGestores(nuevaPagina);
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
                    cargarGestores(nuevaPagina);
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
                    cargarGestores(nuevaPagina);
                }
            });
            paginationContainer.appendChild(nextBtn);
        }
    });
}

//Barra de busqueda
document.getElementById('managers-search-bar').addEventListener('input', function(){
    clearTimeout(timeout);
    timeout = setTimeout(() => {
        const username = this.value.trim();
         // Regex: solo letras, números, espacios, tildes y ñ
        const validName = /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\s]*$/;
        if (username.length === 0 || !validName.test(username)) {
          cargarGestores();
          return
        }
        authFetch(`/managers/username/${encodeURIComponent(username)}`)
        .then(response => response.json())
        .then(managers => {
            if (!managers || managers.length === 0) {
                managerTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun gestor.</td></tr>`;
            }else{
                managerTableBody.innerHTML = '';

                managers.forEach(p => {
                    imprimirGestor(p);
                });
            }
        });
    }, 800);
});

//Funcion para imprimir gestor
function imprimirGestor(p){
    const tr = document.createElement('tr');
    tr.classList.add('manager-table-item', 'crud-table-item');
    tr.innerHTML = `<td class="manager-table-id">${p.id}</td>
                    <td class="manager-table-name">${p.name}</td>
                    <td class="manager-table-lastname">${p.lastname}</td>
                    <td class="manager-table-username">${p.username}</td>
                    <td class="manager-table-email">${p.email}</td>
                    <td class="manager-table-phone">${p.phone}</td>
                    <td class="crud-table-actions">
                        <button class="modify-manager-btn modify-crud-btn" data-bs-toggle="modal" data-bs-target="#modal-modify-manager">Modificar</button>
                        <button class="delete-manager-btn delete-crud-btn" data-id="${p.id}">Eliminar</button>
                    </td>`;
    managerTableBody.appendChild(tr);
}

// Funciones de botones
managerTableBody.addEventListener('click', (e) => {
    const btn = e.target;

    //Boton de modificar
    if (btn.classList.contains('modify-manager-btn')) {
        const row = btn.closest('tr');
        document.getElementById('manager-id-input').value = row.querySelector('.manager-table-id').innerText;
        document.getElementById('manager-name-input').value = row.querySelector('.manager-table-name').innerText;
        document.getElementById('manager-lastname-input').value = row.querySelector('.manager-table-lastname').innerText;
        document.getElementById('manager-username-input').value = row.querySelector('.manager-table-username').innerText;
        document.getElementById('manager-email-input').value = row.querySelector('.manager-table-email').innerText;
        document.getElementById('manager-phone-input').value = row.querySelector('.manager-table-phone').innerText.trim();
        document.getElementById('manager-password-input').value = '';
    }

    //Boton de eliminar
    if (btn.classList.contains('delete-manager-btn')) {
        const id = btn.dataset.id;
        if (confirm("¿Estás seguro de que querés eliminar este gestor?")) {
            authFetch(`/managers/id/${id}`, { method: 'DELETE' })
            .then(async response => {
                if (!response.ok) {
                    toastFail("Error al eliminar gestor", await response.text());
                } else {
                    const urlParams = new URLSearchParams(window.location.search);
                    const currentPage = urlParams.get('page') || 0;
                    cargarGestores(currentPage);
                    toastSuccess("Gestor eliminado correctamente");
                }
            });
        }
    }
});


// Formulario para modificar gestor
modifyManagerForm.addEventListener('submit', async(e)=>{
    e.preventDefault();
    const formData = new FormData(modifyManagerForm);
    const managerData = {
        "id": parseInt(document.getElementById('manager-id-input').value),
        "name": formData.get('name'),
        "lastname": formData.get('lastname'),
        "email": formData.get('email'),
        "phone": formData.get('phone'),
        "username": formData.get('username'),
        "password": formData.get('password')
    };

    authFetch('/managers', {
        method : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(managerData)
    })
    .then(async response => {
        if(!response.ok){
            toastFail("  Error al modificar el gestor", await response.text());
        }else{
            const urlParams = new URLSearchParams(window.location.search);
            const currentPage = urlParams.get('page') || 0;
            let modal = document.getElementById('modal-modify-manager');
            bootstrap.Modal.getInstance(modal).hide();

            cargarGestores(currentPage);
            toastSuccess("Gestor modificado correctamente");
        }
    })
})

// Formulario para agregar gestor
addManagerForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(addManagerForm);
    const managerData = {
        name: formData.get('name'),
        lastname: formData.get('lastname'),
        email: formData.get('email'),
        phone: formData.get('phone'),
        username: formData.get('username'),
        password: formData.get('password')
    };
    authFetch('/managers', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(managerData)
    })
    .then(async response => {
        if (!response.ok) {
            toastFail("Error al agregar gestor", await response.text())
        }else{
            let modal = document.getElementById('modal-add-manager');
            bootstrap.Modal.getInstance(modal).hide();

            addManagerForm.reset();
            cargarGestores();
            toastSuccess("Gestor agregado correctamente");
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