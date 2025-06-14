const headerContainer = document.querySelector('.header-container');
const unloggedNav = `<!-- header sin logear -->
            <div class="fixed-top container-fluid nav-container unlogged-nav p-0 d-flex justify-content-center align-items-center">
                <div id="login-nav" class="position-absolute start-0 ms-4">
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
                <!-- para admins y gestores display:none; -->
            </div>`;
//Agregarle menu segun el rol

//Opciones de roles
getCurrentUser();

async function getCurrentUser() {
    const response = await fetch('/user', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        });
        if (!response.ok) {
            renderMenu(null);
            return;
        }

        const user = await response.json();
        renderMenu(user.role);
}

// MENUS DISTINTOS POR ROL
function renderMenu(role) {
    if(role == null){
        headerContainer.innerHTML = unloggedNav;

    }else if (role === 'CLIENT') {
        clientMenu();
    } else if (role === 'ADMIN') {
        adminMenu();
    } else if (role === 'MANAGER') {
        managerMenu();
    }
};

function clientMenu(){
    headerContainer.innerHTML = loggedNav;
    const offcanvas = document.querySelector('.profile-offcanvas');
    const cart = document.createElement('div');
    cart.classList.add('cart', 'position-absolute', 'end-0', 'me-4');
    cart.innerHTML = `<button class="btn-cart btn-style-1 d-flex align-items-center justify-content-center p-0 border-0 rounded-pill" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasCart" aria-controls="offcanvasCart">
                        <i class="fa-solid fa-cart-shopping ps-2 nav-cart"></i>
                        <span class="text-button">CARRITO</span>
                    </button>
                    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasCart" aria-labelledby="offcanvasCartLabel">
                        <div class="offcanvas-header">
                            <h5 class="offcanvas-title" id="offcanvasRightLabel">Offcanvas right</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                        </div>
                        <div class="offcanvas-body">
                            CARRITO
                        </div>
                    </div>`;
    document.querySelector('.logged-nav').appendChild(cart);
    offcanvas.innerHTML = `<button class="btn-offcanvas-logged btn-style-1 d-flex align-items-center justify-content-center p-0 border-0 rounded-pill" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasProfileClient" aria-controls="offcanvasProfileClient">
                        <div>
                            <i class="fa-solid fa-user"></i>
                        </div>
                    </button>
                    <!-- offcanvas -->
                    <div class="offcanvas offcanvas-start offcanvas-profile" tabindex="-1" id="offcanvasProfileClient" aria-labelledby="offcanvasProfileClientLabel">
                        <div class="offcanvas-header logged">
                            <a href="login.html" id="btn-profile" class="btn-style-1 d-flex align-items-center ps-1 border-0 rounded-pill" type="button">
                                <i class="fa-solid fa-user ms-2"></i>
                                <span class="text-button ms-1">Perfil cliente</span>
                            </a>
                            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                        </div>
                        <div class="offcanvas-body">
                            ...
                        </div>
                    </div>`;
}

function adminMenu(){
    headerContainer.innerHTML = loggedNav;
    const offcanvas = document.querySelector('.profile-offcanvas');
    offcanvas.innerHTML = `<button class="btn-offcanvas-logged btn-style-1 d-flex align-items-center justify-content-center p-0 border-0 rounded-pill" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasProfileAdmin" aria-controls="offcanvasProfileAdmin">
                        <div>
                            <i class="fa-solid fa-user"></i>
                        </div>
                    </button>
                    <!-- offcanvas -->
                    <div class="offcanvas offcanvas-start offcanvas-profile" tabindex="-1" id="offcanvasProfileAdmin" aria-labelledby="offcanvasProfileAdminLabel">
                        <div class="offcanvas-header logged">
                            <a href="login.html" id="btn-profile" class="btn-style-1 d-flex align-items-center ps-1 border-0 rounded-pill" type="button">
                                <i class="fa-solid fa-user ms-2"></i>
                                <span class="text-button ms-1">Perfil Admin</span>
                            </a>
                            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                        </div>
                        <div class="offcanvas-body">
                            ...
                        </div>
                    </div>`;
}

function managerMenu(){
    headerContainer.innerHTML = loggedNav;
    const offcanvas = document.querySelector('.profile-offcanvas');
    offcanvas.innerHTML = `<button class="btn-offcanvas-logged btn-style-1 d-flex align-items-center justify-content-center p-0 border-0 rounded-pill" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasProfileManager" aria-controls="offcanvasProfileManager">
                        <div>
                            <i class="fa-solid fa-user"></i>
                        </div>
                    </button>
                    <!-- offcanvas -->
                    <div class="offcanvas offcanvas-start offcanvas-profile" tabindex="-1" id="offcanvasProfileManager" aria-labelledby="offcanvasProfileManagerLabel">
                        <div class="offcanvas-header logged">
                            <a href="login.html" id="btn-profile" class="btn-style-1 d-flex align-items-center ps-1 border-0 rounded-pill" type="button">
                                <i class="fa-solid fa-user ms-2"></i>
                                <span class="text-button ms-1">Perfil Manager</span>
                            </a>
                            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                        </div>
                        <div class="offcanvas-body">
                            ...
                        </div>
                    </div>`;
}