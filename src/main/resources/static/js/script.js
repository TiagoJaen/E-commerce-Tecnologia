//Toasts
const successToastBody = document.getElementById('success-product-toast-body');
const successToast = document.getElementById('success-product-toast');
const failToastHeader = document.getElementById('fail-product-toast-header');
const failToastBody = document.getElementById('fail-product-toast-body');
const failToast = document.getElementById('fail-product-toast');

const productDisplay = document.getElementById('products-display');
const paginationContainer = document.querySelector(".pagination-container");
let timeout = null;
let pageSize = 8;

// Cargar productos a la tabla
cargarProductos();

function cargarProductos(page = 0) {
    fetch(`/products/paginated?page=${page}&size=${pageSize}`)
    .then(res => res.json())
    .then(data => {
        if (!data.content || data.content.length === 0) {
            productDisplay.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun producto.</td></tr>`;
        }else{
            productDisplay.innerHTML = "";
            paginationContainer.innerHTML = "";
            // Imprimir productos
            data.content.forEach(p => imprimirProducto(p));

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
                    cargarProductos(nuevaPagina);
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
                    cargarProductos(nuevaPagina);
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
                    cargarProductos(nuevaPagina);
                }
            });
            paginationContainer.appendChild(nextBtn);

            document.querySelectorAll('.btn-details').forEach(button => {
                button.addEventListener('click', () => {
                    const id = button.dataset.id;
                    fetch(`/products/id/${id}`)
                    .then(response => response.json())
                    .then(p =>{
                        document.getElementById('modal-product-name').textContent = p.name;
                        document.getElementById('modal-product-price').textContent = Number(p.price).toLocaleString('es-AR', {style: 'currency',
                                                                                                                    currency:  'ARS',
                                                                                                                    maximumFractionDigits: 0});
                        document.getElementById('modal-product-img').src = p.image;
                        document.getElementById('modal-product-description').textContent = p.description;
                        if(p.stock <= 0){
                            document.getElementById('modal-product-stock').innerHTML = `<i class="fa-solid fa-circle-xmark me-2" style="color: #ff4444; font-size: .9em;">  Sin stock</i>`;
                            document.getElementById('modal-add-cart').classList.add('d-none');
                        }else{
                            document.getElementById('modal-product-stock').innerHTML = `<i class="fa-solid fa-circle-check me-2" style="color: #59ef68; font-size: .9em;">  En stock</i>`;
                            document.getElementById('modal-add-cart').classList.remove('d-none');
                        }
                    })
                });
            });
        }
    });
}

//Barra de busqueda
document.getElementById('products-search-bar').addEventListener('input', function(){
    clearTimeout(timeout);
    timeout = setTimeout(() => {
        const name = this.value.trim();
         // Regex: solo letras, números, espacios, tildes y ñ
        const validName = /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\s]*$/;
        if (name.length === 0 || !validName.test(name)) {
          cargarProductos();
          return
        }
        authFetch(`/products/name/${encodeURIComponent(name)}`)
        .then(response => response.json())
        .then(products => {
            if (!products || products.length === 0) {
                productDisplay.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun producto.</td></tr>`;
            }else{
                productDisplay.innerHTML = '';

                products.forEach(p => {
                    imprimirProducto(p);
                });
            }
        });
    }, 800);
});


//Funcion para imprimir producto
function imprimirProducto(p){
    let priceARS = (p.price).toLocaleString('es-AR', {style: 'currency',
            currency: 'ARS',
            maximumFractionDigits: 0
        });
        const stockHtml = p.stock > 0
        ? `<i class="fa-solid fa-circle-check me-2" style="color: #59ef68; font-size: .9em;">  En stock</i>`
        : `<i class="fa-solid fa-circle-xmark me-2" style="color: #ff4444; font-size: .9em;">  Sin stock</i>`;
        const li = document.createElement('li');
        li.classList.add('product-item', 'd-flex', 'flex-column', 'align-items-center');
        li.innerHTML += `<img src="${p.image}" class="product-img mt-2">
                        <div class="product-body">
                            <h5 class="product-title text-center mb-3">${p.name}</h5>
                            <div class="product-price-stock d-flex justify-content-between align-items-center">
                                <h3 class="product-price fs-5 ms-2 mb-0">${priceARS}</h3>
                                ${stockHtml}
                            </div>
                            <div class="product-buttons d-flex justify-content-around">
                                <button 
                                    type="button" 
                                    class="btn-style-1 btn-details" 
                                    data-bs-toggle="modal" 
                                    data-bs-target="#modal-product"
                                    data-id="${p.id}"
                                >
                                    Ver detalles
                                </button>
                            </div>
                        </div>
                    `;  

    productDisplay.appendChild(li);
}

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