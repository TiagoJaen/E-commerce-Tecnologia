const headerContainer = document.querySelector('.header-container');
const unloggedNav = `<!-- header sin logear -->
            <div class="fixed-top container-fluid nav-container unlogged-nav p-0 d-flex justify-content-center align-items-center">
                <div class="position-absolute login-nav start-0 ms-4">
                    <a href="login.html" id="btn-unlogged" class="btn-style-1 d-flex align-items-center justify-content-center border-0 rounded-pill" type="button">
                        <i class="fa-solid fa-user"></i>
                        <span class="text-button">Iniciar Sesión</span>
                    </a>
                </div>
                <a href="/" id="logo-container" >
                    <h1 id="title">FOR GAMERS</h1>
                    <i class="fa-duotone fa-solid fa-gamepad" id="logo"></i>
                </a>
            </div>`;

const loggedNav = `<!-- header logeado -->
            <div class="fixed-top container-fluid nav-container logged-nav p-0 d-flex justify-content-center align-items-center">
                <div class="profile-offcanvas position-absolute start-0 ms-4">

                </div>
                <a href="/" id="logo-container" >
                    <h1 id="title">FOR GAMERS</h1>
                    <i class="fa-duotone fa-solid fa-gamepad" id="logo"></i>
                </a>
            </div>`;

renderMenu();
// MENUS DISTINTOS POR ROL
function renderMenu() {
    const user = getDecodedToken();
    if(user == null || !user){
        headerContainer.innerHTML = unloggedNav;
    }else if (user.role === 'ROLE_CLIENT') {
        clientMenu(user.name, user.lastname, user.id);
        //Cargo el carrito
        loadCart(user.id);
        totalCart(user.id);
        addProductButton(user.id);
    } else if (user.role === 'ROLE_ADMIN') {
        adminMenu(user.name, user.lastname);
    } else if (user.role === 'ROLE_MANAGER') {
        managerMenu(user.name, user.lastname);
    }
};

function adminMenu(name, lastname){
    headerContainer.innerHTML = loggedNav;
    const offcanvas = document.querySelector('.profile-offcanvas');
    offcanvas.innerHTML = `<button class="btn-offcanvas-logged btn-style-1 d-flex align-items-center justify-content-center p-0 border-0 rounded-pill" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasProfileAdmin" aria-controls="offcanvasProfileAdmin">
                                <div>
                                    <i class="fa-solid fa-user"></i>
                                </div>
                            </button>
                            <!-- offcanvas -->
                            <div class="offcanvas offcanvas-start offcanvas-profile" tabindex="-1" id="offcanvasProfileAdmin" aria-labelledby="offcanvasProfileAdminLabel">
                                <div class="offcanvas-header justify-content-center logged">
                                    <h2 class="text-center m-0">${name} ${lastname}</h2>
                                    <button type="button" class="btn-offcanvas-close" style="right: .5em;" data-bs-dismiss="offcanvas" aria-label="Close">
                                        <i class="fa-solid fa-xmark"></i>
                                    </button>
                                </div>
                                <div class="offcanvas-body d-flex flex-column justify-content-between">
                                    <ul class="offcanvas-menu text-center">
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="profile.html" class="offcanvas-profile-item btn-style-1 d-flex justify-content-center align-items-center" type="button">
                                                <i class="fa-solid fa-user me-2"></i>
                                                <span class="me-2">Ver perfil</span>
                                            </a>
                                        </li>
                                        <h2 class="mb-2">GESTIONAR</h2>
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="products.html" class="text-center">Productos</a>
                                        </li>
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="clients.html" class="text-center">Clientes</a>
                                        </li>
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="managers.html" class="text-center">Gestores</a>
                                        </li>
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="admins.html" class="text-center">Admins</a>
                                        </li>
                                        <h2 class="mb-2">DOCUMENTACIÓN</h2>
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="/docs.html" class="text-center">Swagger UI</a>
                                        </li>
                                    </ul>
                                    <a href="/" onclick="logout()" class="offcanvas-logout text-center btn-style-1" type="button">
                                        <span>Cerrar sesión</span>
                                    </a>
                                </div>
                            </div>`;
}

function managerMenu(name, lastname){
    headerContainer.innerHTML = loggedNav;
    const offcanvas = document.querySelector('.profile-offcanvas');
    offcanvas.innerHTML = `<button class="btn-offcanvas-logged btn-style-1 d-flex align-items-center justify-content-center p-0 border-0 rounded-pill" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasProfileManager" aria-controls="offcanvasProfileManager">
                                <div>
                                    <i class="fa-solid fa-user"></i>
                                </div>
                            </button>
                            <!-- offcanvas -->
                            <div class="offcanvas offcanvas-start offcanvas-profile" tabindex="-1" id="offcanvasProfileManager" aria-labelledby="offcanvasProfileManagerLabel">
                                <div class="offcanvas-header justify-content-center logged">
                                    <h2 class="text-center m-0">${name} ${lastname}</h2>
                                    <button type="button" class="btn-offcanvas-close" style="right: .5em;" data-bs-dismiss="offcanvas" aria-label="Close">
                                        <i class="fa-solid fa-xmark"></i>
                                    </button>
                                </div>
                                <div class="offcanvas-body d-flex flex-column justify-content-between">
                                    <ul class="offcanvas-menu text-center">
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="profile.html" class="offcanvas-profile-item btn-style-1 d-flex justify-content-center align-items-center" type="button">
                                                <i class="fa-solid fa-user me-2"></i>
                                                <span class="me-2">Ver perfil</span>
                                            </a>
                                        </li>
                                        <h2 class="mb-2">GESTIONAR</h2>
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="products.html" class="text-center">Productos</a>
                                        </li>
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="clients.html" class="text-center">Clientes</a>
                                        </li>
                                    </ul>
                                    <a href="/" onclick="logout()" class="offcanvas-logout text-center btn-style-1" type="button">
                                        <span>Cerrar sesión</span>
                                    </a>
                                </div>
                            </div>`;
}

//Menu clientes
function clientMenu(name, lastname, id){
    headerContainer.innerHTML = loggedNav;
    const offcanvas = document.querySelector('.profile-offcanvas');
    //Carrito
    const cart = document.createElement('div');
    cart.classList.add('cart', 'position-absolute', 'end-0', 'me-4');
    cart.innerHTML = `<button class="btn-cart btn-style-1 d-flex align-items-center justify-content-center p-0 border-0 rounded-pill" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasCart" aria-controls="offcanvasCart">
                        <i class="fa-solid fa-cart-shopping ps-2 nav-cart"></i>
                        <span class="text-button">CARRITO</span>
                    </button>
                    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasCart" aria-labelledby="offcanvasCartLabel">
                        <div class="offcanvas-header justify-content-center logged">
                            <button type="button" class="btn-offcanvas-close" style="left: .5em;" data-bs-dismiss="offcanvas" aria-label="Close">
                                <i class="fa-solid fa-xmark"></i>
                            </button>
                            <h2 class="text-center m-0">CARRITO</h2>
                        </div>
                        <div class="offcanvas-cart-body">
                            <ul id="cart-list"></ul>
                        </div>
                        <div class="offcanvas-cart-footer d-flex justify-content-around align-items-center">
                            <span id="cart-total"></span>
                            <button class="btn-style-1" id="cart-buy">Comprar</button>
                        </div>
                    </div>`;
    document.querySelector('.logged-nav').appendChild(cart);
    
    //Menu
    offcanvas.innerHTML = `<button class="btn-offcanvas-logged btn-style-1 d-flex align-items-center justify-content-center p-0 border-0 rounded-pill" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasProfileClient" aria-controls="offcanvasProfileClient">
                                <div>
                                    <i class="fa-solid fa-user"></i>
                                </div>
                            </button>
                            <!-- offcanvas -->
                            <div class="offcanvas offcanvas-start offcanvas-profile" tabindex="-1" id="offcanvasProfileClient" aria-labelledby="offcanvasProfileClientLabel">
                                <div class="offcanvas-header justify-content-center logged">
                                    <h2 class="text-center m-0">${name} ${lastname}</h2>
                                    <button type="button" class="btn-offcanvas-close" style="right: .5em;" data-bs-dismiss="offcanvas" aria-label="Close">
                                        <i class="fa-solid fa-xmark"></i>
                                    </button>
                                </div>
                                <div class="offcanvas-body d-flex flex-column justify-content-between">
                                    <ul class="offcanvas-menu">
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="profile.html" class="offcanvas-profile-item btn-style-1 d-flex justify-content-center align-items-center" type="button">
                                                <i class="fa-solid fa-user me-2"></i>
                                                <span class="me-2">Ver perfil</span>
                                            </a>
                                        </li>
                                        <li class="offcanvas-item btn-style-1 d-flex align-items-center">
                                            <a href="payments.html" class="text-center">Metodos de pago</a>
                                        </li>
                                    </ul>
                                    <a href="/" onclick="logout()" class="offcanvas-logout text-center btn-style-1" type="button">
                                        <span>Cerrar sesión</span>
                                    </a>
                                </div>
                            </div>`;

    
}

async function loadCart(clientId){
    const response = await fetch(`/cart?client_id=${clientId}`);
    if (response.ok) {
        const cart = await response.json();
        
        if (cart.contents.length === 0) {
            document.getElementById('cart-list').innerHTML = `<li id="empty-cart" class="d-flex align-items-center justify-content-center">El carrito está vacío</li>`;
            return;
        }else{
            const itemsHTML = await Promise.all(cart.contents.map(async item => {
                const prodRes = await fetch(`/products/id/${item.productId}`);
                if (prodRes.ok) {
                    const product = await prodRes.json();
                    let priceARS = (product.price).toLocaleString('es-AR', {
                        style: 'currency',
                        currency: 'ARS',
                        maximumFractionDigits: 0
                    });
                    return `
                        <li class="cart-item d-flex">
                            <img src="${product.image}" alt="${product.name}">
                        <div class="d-flex flex-column align-items-center">
                                <span>${product.name}</span>
                                <div class="w-100 d-flex justify-content-between align-items-center mt-3 pe-2">
                                    <span>Cantidad: ${item.quantity} - ${priceARS}</span>
                                    <button class="btn btn-sm btn-danger remove-from-cart ms-2" data-product-id="${item.productId}" data-client-id="${clientId}">
                                        <i class="fa-solid fa-trash"></i>
                                    </button>
                                </div>
                            </div>
                        </li>
                    `;
                } else {
                    return `<li>Producto ID ${item.productId} (no encontrado) - Cantidad: ${item.quantity}</li>`;
                }
            }));
            
            document.getElementById('cart-list').innerHTML = itemsHTML.join('');
            deleteFromCart();
        }
    } else {
        throw new Error("Producto no encontrado");
    }
}

async function totalCart(clientId){
    const response = await fetch(`/cart/total?client_id=${clientId}`);
    if (response.ok) {
        const total = await response.json();
        let totalARS = (total).toLocaleString('es-AR', {
            style: 'currency',
            currency: 'ARS',
            maximumFractionDigits: 0
        });

        document.getElementById('cart-total').innerText = `Total: ${totalARS}`;
    }else{
        document.getElementById('cart-total').innerText = "Error al calcular total.";
    }
}

//Botones de eliminar producto
function deleteFromCart(){
    document.querySelectorAll('.remove-from-cart').forEach((b) =>{
        b.addEventListener('click', async (e) =>{
            e.preventDefault();
            const productId = e.currentTarget.getAttribute('data-product-id');
            const clientId = e.currentTarget.getAttribute('data-client-id');
            try {
                const entryResponse = await fetch(`/cart/entry?client_id=${clientId}&product_id=${productId}`);
                if (entryResponse.ok) {
                    const entry = await entryResponse.json();
                    
                    const response = await fetch(`/cart?id=${entry.id}`, {
                        method: 'DELETE'});
                    
                    if (response.ok) {
                        toastSuccess("Producto eliminado correctamente.");
                        loadCart(clientId);
                        totalCart(clientId);
                    }
                } else {
                    toastFail("Error al eliminar el producto del carrito.");
                }
            } catch (error) {
                console.log(error);
                toastFail("Error al eliminar el producto del carrito.");
            }
        });
    })
}

//Boton de agregar producto
function addProductButton(clientId){
    document.getElementById("modal-add-cart").addEventListener('click', async (e) =>{
        const name = document.getElementById('modal-product-name').innerText.trim();
        const productId = await getProductID(name);
        const clientId = getDecodedToken().id;

        fetch('/cart', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                clientId: clientId,
                productId: productId,
                quantity: 1
            })
        })
        .then(res => {
            if (res.ok) {
                loadCart(clientId);
                toastSuccess("Producto agregado correctamente.")
            } else {
                toastFail("Ha ocurrido un error", "No se ha podido agregar el producto.")
            }
        })
        .catch(err => {
            console.error(err);
            toastFail("Ha ocurrido un error", "No se ha podido agregar el producto.")
        });
    });
    async function getProductID(name){
        const response = await fetch(`/products?name=${encodeURIComponent(name)}`);
        if (response.ok) {
            const product = await response.json();
            return product[0].id;
        } else {
            throw new Error("Producto no encontrado");
        }
    }
}