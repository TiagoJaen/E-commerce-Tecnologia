fetch('/products')
  .then(res => res.json())
  .then(data => {
    const contenedor = document.getElementById("products-display");
    data.forEach(p => {
        let priceARS = (p.price).toLocaleString('es-AR', {style: 'currency',
            currency: 'ARS',
            maximumFractionDigits: 0
        });
        const stockHtml = p.stock > 0
        ? `<i class="fa-solid fa-circle-check me-2" style="color: #59ef68; font-size: .9em;">  En stock</i>`
        : `<i class="fa-solid fa-circle-xmark me-2" style="color: #ff4444; font-size: .9em;">  Sin stock</i>`;

        contenedor.innerHTML += `
        <div class="product-item d-flex flex-column align-items-center">
            <img src="${p.image}" class="product-img mt-2">
            <div class="product-body">
                <h5 class="product-title text-center mb-3">${p.name}</h5>
                <div class="product-price-stock d-flex justify-content-between align-items-center">
                    <h3 class="product-price fs-5 ms-2 mb-0">${priceARS}</h3>
                    ${stockHtml}
                </div>
                <div class="product-buttons d-flex justify-content-around">
                    <button type="button" class="btn btn-primary modalButton rounded-pill" data-bs-toggle="modal" data-bs-target="#modal-${p.id}">
                        Ver detalles
                    </button>
                    <button type="button" class="btn btn-primary modalButton rounded-pill" data-bs-toggle="modal" data-bs-target="#modal-${p.id}">
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
                                            <div id="modal-buttons">
                                                <button type="button" class="btn btn-secondary modal-agregar" data-bs-dismiss="modal">
                                                    Agregar al Carrito
                                                    <i class="fa-solid fa-cart-shopping" style="color: #84e98e;"></i>
                                                </button>
                                                <label for="heart-input" id="modal-heart">
                                                    <input type="checkbox" name="" id="heart-input">
                                                    <span id="heart-span">
                                                        <i class="fa-regular fa-heart" style="color: #004346;"></i>
                                                    </span>
                                                    <span id="heart-fav">
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
        </div>
      `;
    });
  });

  