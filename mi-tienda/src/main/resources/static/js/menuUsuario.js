
    console.log("¡El script se ejecuta correctamente!");
    document.addEventListener("DOMContentLoaded", function() {
  console.log("El script se está ejecutando correctamente");

  // Petición al backend para obtener el usuario de la sesión
  fetch('/usuario-sesion')
    .then(response => {
      if (!response.ok) {
        console.warn("La respuesta no es válida:", response.status);
        return null; // Devolvemos null si la respuesta no es exitosa
      }
      return response.text(); // Leemos el cuerpo de la respuesta como texto
    })
    .then(text => {
      if (text) {
        return JSON.parse(text); // Intentamos convertirlo a JSON si no está vacío
      }
      return null; // Si el texto está vacío, devolvemos null
    })
    .then(usuario => {
      if (usuario && usuario.nombre) {
        // Si hay usuario en la sesión, mostramos su nombre y las opciones de Mi Zona y Salir
        document.getElementById("userName").textContent = usuario.nombre + " " + usuario.apellido;
        var dropdown = document.getElementById("accountDropdown");
        dropdown.innerHTML = `
          <li><a class="dropdown-item" href="/mi-zona"><i class="fas fa-cogs"></i> Mi Zona</a></li>
          <li><a class="dropdown-item" href="/logout" onclick="logout()"><i class="fas fa-sign-out-alt"></i> Salir</a></li>
        `;
      } else {
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