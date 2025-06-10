fetch('/products')
.then(res => res.json())
.then(products => {
    const productTableBody = document.getElementById('products-table-body');
    productTableBody.innerHTML = "";
    products.forEach(p => {
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
    });
});