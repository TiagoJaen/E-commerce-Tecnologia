const jwtToken = localStorage.getItem('jwtToken');

const ui = SwaggerUIBundle({
    url: '/v3/api-docs',
    dom_id: '#swagger-ui',
    presets: [SwaggerUIBundle.presets.apis],
    layout: "BaseLayout",
    requestInterceptor: (req) => {
        if (jwtToken) {
            req.headers['Authorization'] = 'Bearer ' + jwtToken;
            console.log('[Swagger] Token added to request:', jwtToken);
        }else{
            console.warn('[Swagger] No token found!');
        }
    return req;
    }
});