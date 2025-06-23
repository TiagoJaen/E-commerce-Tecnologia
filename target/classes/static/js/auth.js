//Manda el token JWT en el header automaticamente.
window.authFetch = function(url, options = {}) {
    const token = localStorage.getItem('jwtToken');

    if (!token || isTokenExpired(token)) {
        logout();
        alert("Sesion expirada.");
        window.location.href = "/";
        return Promise.reject("JWT missing or expired");
    }

    const headers = new Headers(options.headers || {});
    headers.set('Authorization', `Bearer ${token}`);
    headers.set('Content-Type', 'application/json');

    return fetch(url, {
        ...options,
        headers
    });
};
//Devuelve los datos del token JWT.
window.getDecodedToken = function () {
    const token = localStorage.getItem('jwtToken');
    if(!token) return null;

    try{
        const payload = token.split('.')[1];
        return JSON.parse(atob(payload));
    }catch (e){
        console.error('Invalid JWT token:', e);
        return null;
    }
};
//Checkear si el token esta expirado
window.isTokenExpired = function(token){
    try{
        const payload = JSON.parse(atob(token.split(".")[1]));
        const now = Math.floor(Date.now() / 1000);
        return payload.exp < now;
    }catch(e){
        return true;
    }
};
//Eliminar el token
window.logout = function(){
    localStorage.removeItem('jwtToken');
};

(function checkAuthOnPageLoad(){
    const token = localStorage.getItem('jwtToken');
    const decoded = token ? getDecodedToken() : null;
    const currentPath = window.location.pathname;

    const protectedPaths = [
        {path: '/admins.html', roles: ['ROLE_ADMIN']},
        {path: '/clients.html', roles: ['ROLE_ADMIN', 'ROLE_MANAGER']},
        {path: '/docs.html', roles: ['ROLE_ADMIN']},
        {path: '/managers.html', roles: ['ROLE_ADMIN']},
        {path: '/products.html', roles: ['ROLE_ADMIN', 'ROLE_MANAGER']},
        {path: '/profile.html', roles: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CLIENT']}
    ];

    const page = protectedPaths.find(p => currentPath.endsWith(p.path));

    if(page){
        if(!token || isTokenExpired(token) || !decoded || !page.roles.includes(decoded.role)){
            if(isTokenExpired(token)){
                logout();
                alert("Sesion expirada.");
            }
            window.location.href = "/";
        }
    }else{
        if(token && isTokenExpired(token)){
            logout();
            console.log('Expired token detected and removed on public page.')
            return;
        }
    }
})();
