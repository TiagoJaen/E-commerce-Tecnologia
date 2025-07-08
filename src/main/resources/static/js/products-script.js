const productTableBody = document.getElementById('products-table-body');
const paginationContainer = document.querySelector(".pagination-container");
const addProductForm = document.getElementById('add-product-form');
const modifyProductForm = document.getElementById('modify-product-form');
const successToastBody = document.getElementById('success-product-toast-body');
const successToast = document.getElementById('success-product-toast');
const failToastHeader = document.getElementById('fail-product-toast-header');
const failToastBody = document.getElementById('fail-product-toast-body');
const failToast = document.getElementById('fail-product-toast');
let timeout = null;
let pageSize = 5;

// Cargar productos a la tabla
cargarProductos();

function cargarProductos(page = 0) {
    authFetch(`/products/paginated?page=${page}&size=${pageSize}`)
    .then(res => res.json())
    .then(data => {
        if (!data.content || data.content.length === 0) {
            productTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun producto.</td></tr>`;
        }else{
            productTableBody.innerHTML = "";
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
        authFetch(`/products?name=${encodeURIComponent(name)}`)
        .then(response => response.json())
        .then(products => {
            if (!products || products.length === 0) {
                productTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun producto.</td></tr>`;
            }else{
                productTableBody.innerHTML = '';

                products.forEach(p => {
                    imprimirProducto(p);
                });
            }
        });
    }, 800);
});

//Funcion para imprimir producto
function imprimirProducto(p){
    let priceARS = (p.price).toLocaleString('es-AR', {
        style: 'currency',
        currency: 'ARS',
        maximumFractionDigits: 0
    });

    const tr = document.createElement('tr');
    tr.classList.add('product-table-item', 'crud-table-item');
    tr.innerHTML = `<td class="crud-table-image">
                        <img src="${p.image}" alt="Imagen" class="crud-table-image">
                    </td>
                    <td class="product-table-id">${p.id}</td>
                    <td class="product-table-name">${p.name}</td>
                    <td class="product-table-description">${p.description}</td>
                    <td class="product-table-stock">${p.stock}</td>
                    <td class="product-table-price">${priceARS}</td>
                    <td class="crud-table-actions">
                        <button class="modify-product-btn modify-crud-btn" data-bs-toggle="modal" data-bs-target="#modal-modify-product">Modificar</button>
                        <button class="delete-product-btn delete-crud-btn" data-id="${p.id}">Eliminar</button>
                    </td>`;
    productTableBody.appendChild(tr);
}

// Funciones de botones
productTableBody.addEventListener('click', (e) => {
    const btn = e.target;

    //Boton de modificar
    if (btn.classList.contains('modify-product-btn')) {
        const row = btn.closest('tr');
        document.getElementById('product-id-input').value = row.querySelector('.product-table-id').innerText;
        document.getElementById('product-name-input').value = row.querySelector('.product-table-name').innerText;
        document.getElementById('product-description-input').value = row.querySelector('.product-table-description').innerText;
        document.getElementById('product-stock-input').value = row.querySelector('.product-table-stock').innerText;
        document.getElementById('product-price-input').value = row.querySelector('.product-table-price').innerText.replace(/[^\d]/g, '');
        document.getElementById('product-image-input').value = row.querySelector('img').src;
        document.getElementById('product-image-source').src = row.querySelector('img').src;
    }

    //Boton de eliminar
    if (btn.classList.contains('delete-product-btn')) {
        const id = btn.dataset.id;
        if (confirm("¿Estás seguro de que querés eliminar este producto?")) {
            authFetch(`/products/id/${id}`, { method: 'DELETE' })
            .then(async response => {
                if (!response.ok) {
                    toastFail("Error al eliminar producto", await response.text());
                } else {
                    const urlParams = new URLSearchParams(window.location.search);
                    const currentPage = urlParams.get('page') || 0;
                    cargarProductos(currentPage);
                    toastSuccess("Producto eliminado correctamente");
                }
            });
        }
    }
});


// Formulario para modificar producto
modifyProductForm.addEventListener('submit', async(e)=>{
    e.preventDefault();
    const formData = new FormData(modifyProductForm);
    const productData = {
        "id": parseInt(document.getElementById('product-id-input').value),
        "name": formData.get('name'),
        "price": parseFloat(formData.get('price')),
        "description": formData.get('description'),
        "image": formData.get('image'),
        "stock": parseInt(formData.get('stock'))
    };
    if(productData.price < 0 || productData.stock <0){
        toastFail("  Error al modificar producto", "El stock y el precio no pueden ser negativos.");
    }else{
        authFetch('/products', {
            method : 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(productData)
        })
        .then(async response => {
            if(!response.ok){
                toastFail("  Error al modificar el producto", await response.text());
            }else{
                const urlParams = new URLSearchParams(window.location.search);
                const currentPage = urlParams.get('page') || 0;
                let modal = document.getElementById('modal-modify-product');
                bootstrap.Modal.getInstance(modal).hide();

                cargarProductos(currentPage);
                toastSuccess("Producto modificado correctamente");
            }
        })
    }
})

// Formulario para agregar producto
addProductForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(addProductForm);
    const productData = {
        "name": formData.get('name'),
        "price": formData.get('price'),
        "description": formData.get('description'),
        "image": formData.get('image'),
        "stock": formData.get('stock')
    };
    if(productData.price < 0 || productData.stock <0){
        toastFail("  Error al agregar producto", "El stock y el precio no pueden ser negativos.");
    }else{
        authFetch('/products', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(productData)
        })
        .then(async response => {
            if (!response.ok) {
                toastFail("Error al agregar producto", await response.text())
            }else{
                let modal = document.getElementById('modal-add-product');
                bootstrap.Modal.getInstance(modal).hide();

                addProductForm.reset();
                cargarProductos();
                toastSuccess("Producto agregado correctamente");
            }
        })
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