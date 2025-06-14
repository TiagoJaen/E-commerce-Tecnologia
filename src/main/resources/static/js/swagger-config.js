const token = localStorage.getItem('jwtToken');

const ui = SwaggerUIBundle({
  url: '/v3/api-docs',
  dom_id: '#swagger-ui',
  presets: [SwaggerUIBundle.presets.apis],
  layout: "BaseLayout",
  requestInterceptor: (req) => {
    if (token) {
      req.headers['Authorization'] = 'Bearer ' + token;
    }
    return req;
  }
});