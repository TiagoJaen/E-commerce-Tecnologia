const clientTableBody = document.getElementById('clients-table-body');
const paginationContainer = document.querySelector(".pagination-container");
const addClientForm = document.getElementById('add-client-form');
const modifyClientForm = document.getElementById('modify-client-form');
const successToastBody = document.getElementById('success-client-toast-body');
const successToast = document.getElementById('success-client-toast');
const failToastHeader = document.getElementById('fail-client-toast-header');
const failToastBody = document.getElementById('fail-client-toast-body');
const failToast = document.getElementById('fail-client-toast');
let timeout = null;
let pageSize = 5;

// Cargar clientes a la tabla
cargarClientes();

function cargarClientes(page = 0) {
    fetch(`/clients/paginated?page=${page}&size=${pageSize}`)
    .then(res => res.json())
    .then(data => {
        if (!data.content || data.content.length === 0) {
            clientTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun cliente.</td></tr>`;
        }else{
            clientTableBody.innerHTML = "";
            paginationContainer.innerHTML = "";
            // Imprimir clientes
            data.content.forEach(p => imprimirCliente(p));

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
                    cargarClientes(nuevaPagina);
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
                    cargarClientes(nuevaPagina);
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
                    cargarClientes(nuevaPagina);
                }
            });
            paginationContainer.appendChild(nextBtn);
        }
    });
}

//Barra de busqueda
document.getElementById('clients-search-bar').addEventListener('input', function(){
    clearTimeout(timeout);
    timeout = setTimeout(() => {
        const username = this.value.trim();
         // Regex: solo letras, números, espacios, tildes y ñ
        const validName = /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\s]*$/;
        if (username.length === 0 || !validName.test(username)) {
          cargarClientes();
          return
        }
        fetch(`/clients/username/${encodeURIComponent(username)}`)
        .then(response => response.json())
        .then(clients => {
            if (!clients || clients.length === 0) {
                clientTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun cliente.</td></tr>`;
            }else{
                clientTableBody.innerHTML = '';

                clients.forEach(p => {
                    imprimirCliente(p);
                });
            }
        });
    }, 800);
});

//Funcion para imprimir cliente
function imprimirCliente(p){
    const tr = document.createElement('tr');
    tr.classList.add('client-table-item', 'crud-table-item');
    tr.innerHTML = `<td class="client-table-id">${p.id}</td>
                    <td class="client-table-name">${p.name}</td>
                    <td class="client-table-lastname">${p.lastname}</td>
                    <td class="client-table-username">${p.username}</td>
                    <td class="client-table-email">${p.email}</td>
                    <td class="client-table-phone">${p.phone}</td>
                    <td class="crud-table-actions">
                        <button class="modify-client-btn modify-crud-btn" data-bs-toggle="modal" data-bs-target="#modal-modify-client">Modificar</button>
                        <button class="delete-client-btn delete-crud-btn" data-id="${p.id}">Eliminar</button>
                    </td>`;
    clientTableBody.appendChild(tr);
}

// Funciones de botones
clientTableBody.addEventListener('click', (e) => {
    const btn = e.target;

    //Boton de modificar
    if (btn.classList.contains('modify-client-btn')) {
        // const currentUser = getCurrentUser();
        const row = btn.closest('tr');
        document.getElementById('client-id-input').value = row.querySelector('.client-table-id').innerText;
        document.getElementById('client-name-input').value = row.querySelector('.client-table-name').innerText;
        document.getElementById('client-lastname-input').value = row.querySelector('.client-table-lastname').innerText;
        document.getElementById('client-username-input').value = row.querySelector('.client-table-username').innerText;
        document.getElementById('client-email-input').value = row.querySelector('.client-table-email').innerText;
        document.getElementById('client-phone-input').value = row.querySelector('.client-table-phone').innerText.trim();
        document.getElementById('client-password-input').value = '';
    }

    //Boton de eliminar
    if (btn.classList.contains('delete-client-btn')) {
        const id = btn.dataset.id;
        if (confirm("¿Estás seguro de que querés eliminar este cliente?")) {
            fetch(`/clients/${id}`, { method: 'DELETE' })
            .then(async response => {
                if (!response.ok) {
                    toastFail("Error al eliminar cliente", await response.text());
                } else {
                    const urlParams = new URLSearchParams(window.location.search);
                    const currentPage = urlParams.get('page') || 0;
                    cargarClientes(currentPage);
                    toastSuccess("Cliente eliminado correctamente");
                }
            });
        }
    }
});

//Obtener el usuario de la sesión
async function getCurrentUser() {
    const response = await fetch('/user', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        });
        if (!response.ok) {
            return;
        }

    const user = await response.json();
    return user;
}


// Formulario para modificar cliente
modifyClientForm.addEventListener('submit', async(e)=>{
    e.preventDefault();
    const formData = new FormData(modifyClientForm);
    const productData = {
        "id": parseInt(document.getElementById('client-id-input').value),
        "name": formData.get('name'),
        "lastname": formData.get('lastname'),
        "email": formData.get('email'),
        "phone": formData.get('phone'),
        "username": formData.get('username'),
        "password": formData.get('password')
    };

    fetch('/clients', {
        method : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(productData)
    })
    .then(async response => {
        if(!response.ok){
            toastFail("  Error al modificar el cliente", await response.text());
        }else{
            const urlParams = new URLSearchParams(window.location.search);
            const currentPage = urlParams.get('page') || 0;
            let modal = document.getElementById('modal-modify-client');
            bootstrap.Modal.getInstance(modal).hide();

            cargarClientes(currentPage);
            toastSuccess("Cliente modificado correctamente");
        }
    })
})

// Formulario para agregar cliente
addClientForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(addClientForm);
    const productData = {
        name: formData.get('name'),
        lastname: formData.get('lastname'),
        email: formData.get('email'),
        phone: formData.get('phone'),
        username: formData.get('username'),
        password: formData.get('password')
    };
    fetch('/clients', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(productData)
    })
    .then(async response => {
        if (!response.ok) {
            toastFail("Error al agregar cliente", await response.text())
        }else{
            let modal = document.getElementById('modal-add-client');
            bootstrap.Modal.getInstance(modal).hide();

            addClientForm.reset();
            cargarClientes();
            toastSuccess("Cliente agregado correctamente");
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