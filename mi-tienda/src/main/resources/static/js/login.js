document.addEventListener("DOMContentLoaded", () => {
    // Obtener el formulario y asociar el evento submit
    const loginForm = document.getElementById("loginForm");
    loginForm.addEventListener("submit", validateLogin);
  });

  function validateLogin(event) {
    event.preventDefault(); 
    
    const usernameInput = document.getElementById("username");
    const passwordInput = document.getElementById("password");
    const usernameError = document.getElementById("usernameError");
    const passwordError = document.getElementById("passwordError");
    
    // Limpiar los errores anteriores
    usernameError.textContent = "";
    passwordError.textContent = "";
    
    // Resetear clases de error
    usernameInput.classList.remove("is-invalid");
    passwordInput.classList.remove("is-invalid");
    
    let isValid = true;

    // Validar nombre de usuario
    if (usernameInput.value.trim() === "" || usernameInput.value.length < 3) {
      usernameError.textContent = "El nombre de usuario es obligatorio y debe tener al menos 3 caracteres.";
      usernameInput.classList.add("is-invalid");
      isValid = false;
    }

    // Validar contraseña
    if (passwordInput.value.trim() === "" || passwordInput.value.length < 6) {
      passwordError.textContent = "La contraseña es obligatoria y debe tener al menos 6 caracteres.";
      passwordInput.classList.add("is-invalid");
      isValid = false;
    }

    // Si el formulario es válido, enviarlo
    if (isValid) {
      loginForm.submit(); // Esto hará que el formulario se envíe
    }
  }

