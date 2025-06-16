//Manda el token JWT en el header automaticamente.
window.authFetch = function(url, options = {}) {
    const token = localStorage.getItem('jwtToken');

    if (!token || isTokenExpired(token)) {
        logout();
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