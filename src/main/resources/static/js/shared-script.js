const headerContainer = document.getElementById('header-container');
//Opciones de roles
getCurrentUser();

async function getCurrentUser() {
    const response = await fetch('/api/me', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            });

    if(!response.ok){
        renderMenu(null);
    }else{
        console.log(await response.json);
    }
}

// MENUS DISTINTOS POR ROL
function renderMenu(user) {
    if(user == null){
        headerContainer.innerHTML = `<!-- header sin logear -->
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
    }else if (user.role === 'ROLE_CLIENT') {
    } else if (user.role === 'ROLE_ADMIN') {
        document.getElementById('cart').classList.add('d-none');
    } else if (user.role === 'ROLE_MANAGER') {
        document.getElementById('cart').classList.add('d-none');
    }
};