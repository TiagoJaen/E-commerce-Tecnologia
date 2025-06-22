const cardTableBody = document.getElementById('cards-table-body');
const addCardForm = document.getElementById('add-card-form');
const successToastBody = document.getElementById('success-card-toast-body');
const successToast = document.getElementById('success-card-toast');
const failToastHeader = document.getElementById('fail-card-toast-header');
const failToastBody = document.getElementById('fail-card-toast-body');
const failToast = document.getElementById('fail-card-toast');
let timeout = null;
let pageSize = 5;

// Cargar tarjetas a la tabla
cargarTarjetas();

function cargarTarjetas() {
    const name = getDecodedToken().name;
    const lastname = getDecodedToken().lastname;
    const holder = name + " " + lastname;

    authFetch(`/card/holder?holder=${holder}`)
    .then(res => res.json())
    .then(data => {
        if (!data.content || data.content.length === 0) {
            cardTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun tarjeta.</td></tr>`;
        }else{
            cardTableBody.innerHTML = "";
            paginationContainer.innerHTML = "";
            // Imprimir tarjetas
            document.querySelectorAll('.delete-card-btn').forEach(btn => {
                btn.addEventListener('click', (e) => {
                const id = e.target.dataset.id;
                if (confirm("¿Estás seguro de que querés eliminar esta tarjeta?")) {
                  authFetch(`/card?id=${id}`, { method: 'DELETE' })
                  .then(async response => {
                      if (!response.ok) {
                          toastFail("Error al eliminar tarjeta", await response.text());
                      } else {
                          cargarTarjetas();
                          toastSuccess("Tarjeta eliminada correctamente");
                      }
                  });
                }
              });
            });
        }
    });
}

//Funcion para imprimir tarjeta
function imprimirTarjeta(p){
    const tr = document.createElement('tr');
    tr.classList.add('card-table-item', 'crud-table-item');
    tr.innerHTML = `<td class="card-table-holder">${p.holder}</td>
                    <td class="card-table-number">${p.number}</td>
                    <td class="card-table-expires">${p.expirationDate}</td>
                    <td class="crud-table-actions">
                        <button class="modify-card-btn modify-crud-btn" data-bs-toggle="modal" data-bs-target="#modal-modify-card">Modificar</button>
                        <button class="delete-card-btn delete-crud-btn" data-id="${p.id}">Eliminar</button>
                    </td>`;
    cardTableBody.appendChild(tr);
}

// Formulario para agregar tarjeta
addCardForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(addCardForm);
    const cardData = {
        holder: formData.get('holder'),
        number: formData.get('number'),
        expirationDate: formData.get('expirationDate'),
        cvv: parseInt(formData.get('cvv'))
    };
    console.log(cardData);
    authFetch('/card', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(cardData)
    })
    .then(async response => {
        if (!response.ok) {
            toastFail("Error al agregar tarjeta", await response.text())
        }else{
            let modal = document.getElementById('modal-add-card');
            bootstrap.Modal.getInstance(modal).hide();

            addCardForm.reset();
            cargarTarjetas();
            toastSuccess("Tarjeta agregada correctamente");
        }
    });
});
//Formato de tarjeta para input
document.getElementById('card-number').addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, ''); // Quitamos todo lo que no sea número
    value = value.substring(0, 16); // Máximo 16 dígitos
    let formatted = value.match(/.{1,4}/g); // Agrupamos de a 4
    if (formatted) {
        e.target.value = formatted.join(' ');
    } else {
        e.target.value = value;
    }
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