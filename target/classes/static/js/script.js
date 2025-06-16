const productDisplay = document.getElementById('products-display');
const paginationContainer = document.querySelector(".pagination-container");
let timeout = null;
let pageSize = 8;

// Cargar productos a la tabla
cargarProductos();

function cargarProductos(page = 0) {
    authFetch(`/products/paginated?page=${page}&size=${pageSize}`)
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
        }
    });
}

//Funcion para imprimir producto
function imprimirProducto(p){
    let priceARS = (p.price).toLocaleString('es-AR', {style: 'currency',
            currency: 'ARS',
            maximumFractionDigits: 0
        });
        const stockHtml = p.stock > 0
        ? `<i class="fa-solid fa-circle-check me-2" style="color: #59ef68; font-size: .9em;">  En stock</i>`
        : `<i class="fa-solid fa-circle-xmark me-2" style="color: #ff4444; font-size: .9em;">  Sin stock</i>`;
        const div = document.createElement('div');
        div.classList.add('product-item', 'd-flex', 'flex-column', 'align-items-center');
        div.innerHTML += `<img src="${p.image}" class="product-img mt-2">
                        <div class="product-body">
                            <h5 class="product-title text-center mb-3">${p.name}</h5>
                            <div class="product-price-stock d-flex justify-content-between align-items-center">
                                <h3 class="product-price fs-5 ms-2 mb-0">${priceARS}</h3>
                                ${stockHtml}
                            </div>
                            <div class="product-buttons d-flex justify-content-around">
                                <button type="button" class="btn-style-1" data-bs-toggle="modal" data-bs-target="#modal-${p.id}">
                                    Ver detalles
                                </button>
                                <button type="button" class="btn-style-1" data-bs-toggle="modal" data-bs-target="#modal-${p.id}">
                                    Agregar
                                    <i class="fa-solid fa-cart-shopping"></i>
                                </button>
                            </div>
                        </div>
                        <div class="product-modal">
                            <div class="modal fade" id="modal-${p.id}" tabindex="-1" aria-labelledby="modal-${p.id}-label" style="display: none;" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5 titles" id="modal-${p.id}">${p.name}</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-5">
                                                    <img src="${p.image}" class="w-100" alt="">
                                                </div>
                                                <div class="col-7 d-flex justify-content-between flex-column">
                                                    <p>${p.description}</p>
                                                    <div class="modal-bottom">
                                                        <h6 class="titles">Precio</h6>
                                                        <p>${priceARS}</p>
                                                        <div class="modal-buttons">
                                                            <button type="button" class="btn btn-secondary modal-agregar" data-bs-dismiss="modal">
                                                                Agregar al Carrito
                                                                <i class="fa-solid fa-cart-shopping" style="color: #84e98e;"></i>
                                                            </button>
                                                            <label for="heart-input" class="modal-heart">
                                                                <input type="checkbox" class="heart-input">
                                                                <span class="heart-span">
                                                                    <i class="fa-regular fa-heart" style="color: #004346;"></i>
                                                                </span>
                                                                <span class="heart-fav">
                                                                    <i class="fa-solid fa-heart" style="color: #004346;"></i>
                                                                </span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;  

    productDisplay.appendChild(div);
}