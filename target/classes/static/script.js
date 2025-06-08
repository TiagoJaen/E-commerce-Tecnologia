fetch('/products')
  .then(res => res.json())
  .then(data => {
    const contenedor = document.getElementById("products-display");
    data.forEach(p => {
        contenedor.innerHTML += `
        <div class="product-item col-3">
            <div class="card">
                <img src="${p.image}" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${p.name}</h5>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">$${p.price}</li>
                </ul>
                <div class="card-body">
                    <button type="button" class="btn btn-primary modalButton rounded-pill" data-bs-toggle="modal" data-bs-target="#modal-${p.id}">
                        Ver más
                    </button>
                    <div class="product-modal-more">
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
                                                    <p>$${p.price}</p>
                                                    <div id="modal-buttons">
                                                        <button type="button" class="btn btn-secondary modal-agregar" data-bs-dismiss="modal">
                                                            Agregar al Carrito
                                                            <i class="fa-solid fa-cart-shopping" style="color: #ba47e9;"></i>
                                                        </button>
                                                        <label for="heart-input" id="modal-heart">
                                                                <input type="checkbox" name="" id="heart-input">
                                                                <span id="heart-span">
                                                                    <i class="fa-regular fa-heart" style="color: #ba47e9;"></i>
                                                                </span>
                                                                <span id="heart-fav">
                                                                    <i class="fa-solid fa-heart" style="color: #ba47e9;"></i>
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
                    <button type="button" class="btn btn-primary modalButton rounded-pill" data-bs-toggle="modal" data-bs-target="#modal-${p.id}">
                        <i class="fa-solid fa-cart-shopping"></i>
                    </button>
                </div>
            </div>
        </div>
      `;
    });
  });