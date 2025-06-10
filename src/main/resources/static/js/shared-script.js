

fetch('/user', {
    method: 'GET',
    headers: {
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
      'Content-Type': 'application/json'
    }
  })
  .then(response => response.json())
  .then(data => {
    console.log(data);
    const role = data.role;
    renderMenu(role);
  });

// MENUS DISTINTOS POR ROL
function renderMenu(role) {
    if (role === 'ROLE_CLIENT') {
    } else if (role === 'ROLE_ADMIN') {
        document.getElementById('cart').classList.add('d-none');
    } else if (role === 'ROLE_MANAGER') {
        document.getElementById('cart').classList.add('d-none');
    }
  }