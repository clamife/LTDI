
   
    document.addEventListener("DOMContentLoaded", function() {
  console.log("El script se está ejecutando correctamente");

  fetch('/usuario-sesion')
    .then(response => {
      if (!response.ok) {
        console.warn("La respuesta no es válida:", response.status);
        return null; 
      }
      return response.text();
    })
    .then(text => {
      if (text) {
        return JSON.parse(text);
      }
      return null; 
    })
    .then(usuario => {
      if (usuario && usuario.esAdmin) {
        document.getElementById("userName").textContent = usuario.nombre;
        var dropdown = document.getElementById("accountDropdown");
        dropdown.innerHTML = `
          <li><a class="dropdown-item" href="/logout" onclick="logout()"><i class="fas fa-sign-out-alt"></i> Salir</a></li>
        `;
      }else if(usuario && usuario.nombre){
        document.getElementById("userName").textContent = usuario.nombre;
        var dropdown = document.getElementById("accountDropdown");
        dropdown.innerHTML = `
          <li><a class="dropdown-item" href="/miZona"><i class="fas fa-cogs"></i> Mi Zona</a></li>
          <li><a class="dropdown-item" href="/logout" onclick="logout()"><i class="fas fa-sign-out-alt"></i> Salir</a></li>
          
        `;
      } 
      else {
        // Si no hay usuario en la sesión, mostramos las opciones de Iniciar sesión y Registrarse
        var dropdown = document.getElementById("accountDropdown");
        dropdown.innerHTML = `
          <li><a class="dropdown-item" href="/login"><i class="fas fa-sign-in-alt"></i> Iniciar Sesión</a></li>
          <li><a class="dropdown-item" href="/registro"><i class="fas fa-user-plus"></i> Registrarme</a></li>
        `;
      }
    })
    .catch(error => {
      console.error('Error al obtener el usuario:', error);
    });
});



    // Función para cerrar sesión
    function logout() {
      fetch("/logout", { method: 'POST' })
        .then(response => window.location.href = "/");  // Redirige a la página principal o login
    }