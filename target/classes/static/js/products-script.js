const productTableBody = document.getElementById('products-table-body');
let timeout = null;

// Cargar productos a la tabla
cargarProductos();

function cargarProductos() {
    fetch('/products')
    .then(res => res.json())
    .then(products => {
        if (products.length === 0) {
            productTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun producto.</td></tr>`;
        }else{
            productTableBody.innerHTML = "";
            products.forEach(p => {
                imprimirProducto(p);
            });
        }
    });
}
//Barra de busqueda
document.getElementById('products-search-bar').addEventListener('input', function () {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
        const name = this.value.trim();
        if (name.length === 0) {
          cargarProductos();
          return
        }
        fetch(`/products?name=${encodeURIComponent(name)}`)
        .then(response => response.json())
        .then(products => {
            if (products.length === 0) {
                productTableBody.innerHTML = `<tr><td class="text-center" colspan="6">No se ha encontrado ningun producto.</td></tr>`;
            }else{
                productTableBody.innerHTML = '';

                products.forEach(p => {
                    imprimirProducto(p);
                });
            }
        });
    }, 300);
});

//Funcion para imprimir producto
function imprimirProducto(p){
    let priceARS = (p.price).toLocaleString('es-AR', {style: 'currency',
            currency: 'ARS',
            maximumFractionDigits: 0
        });
    productTableBody.innerHTML += `
    <tr class="product-table-item">
        <th scope="row">${p.id}</th>
        <td class="product-table-name">${p.name}</td>
        <td class="product-table-description">${p.description}</td>
        <td class="product-table-image"><a href="${p.image}" target="_blank">Imagen</a></td>
        <td class="product-table-stock">${p.stock}</td>
        <td class="product-table-price">${priceARS}</td>
        <td class="products-table-actions">
            <button class="modify-product-btn" data-id="${p.id}">Modificar</button>
            <button class="delete-product-btn" data-id="${p.id}">Eliminar</button>
        </td>
    </tr>
    `;
}