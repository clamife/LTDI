function validateRegister(event) {
    document.getElementById("usernameError").style.display = "none";
    document.getElementById("emailError").style.display = "none";
    document.getElementById("passwordError").style.display = "none";
    document.getElementById("confirmPasswordError").style.display = "none";
    document.getElementById("apellidoError").style.display = "none";
    document.getElementById("direccionError").style.display = "none";
    document.getElementById("telefonoError").style.display = "none";

    let isValid = true;
    let username = document.getElementById("username");
    let email = document.getElementById("email");
    let password = document.getElementById("password");
    let confirmPassword = document.getElementById("confirmPassword");
    let apellido = document.getElementById("apellido");
    let direccion = document.getElementById("direccion");
    let telefono = document.getElementById("telefono");

    username.classList.remove("is-invalid");
    email.classList.remove("is-invalid");
    password.classList.remove("is-invalid");
    confirmPassword.classList.remove("is-invalid");
    apellido.classList.remove("is-invalid");
    direccion.classList.remove("is-invalid");
    telefono.classList.remove("is-invalid");

    if (apellido.value.trim().length < 3) {
        document.getElementById("apellidoError").textContent = "El apellido debe tener al menos 3 caracteres.";
        document.getElementById("apellidoError").style.display = "block";
        apellido.classList.add("is-invalid");
        isValid = false;
    }

    if (direccion.value.trim() === "") {
        document.getElementById("direccionError").textContent = "La dirección es obligatoria.";
        document.getElementById("direccionError").style.display = "block";
        direccion.classList.add("is-invalid");
        isValid = false;
    }

    const telefonoPattern = /^[0-9]{9}$/;
    if (telefono.value.trim() === "") {
        document.getElementById("telefonoError").textContent = "El teléfono es obligatorio.";
        document.getElementById("telefonoError").style.display = "block";
        telefono.classList.add("is-invalid");
        isValid = false;
    } else if (!telefonoPattern.test(telefono.value)) {
        document.getElementById("telefonoError").textContent = "Por favor, introduce un teléfono válido (9 dígitos).";
        document.getElementById("telefonoError").style.display = "block";
        telefono.classList.add("is-invalid");
        isValid = false;
    }

    if (username.value.trim().length < 3 || username.value.trim().length > 20) {
        document.getElementById("usernameError").textContent = "El nombre de usuario debe tener entre 3 y 20 caracteres.";
        document.getElementById("usernameError").style.display = "block";
        username.classList.add("is-invalid");
        isValid = false;
    }

    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (email.value.trim() === "") {
        document.getElementById("emailError").textContent = "El correo electrónico es obligatorio.";
        document.getElementById("emailError").style.display = "block";
        email.classList.add("is-invalid");
        isValid = false;
    } else if (!emailPattern.test(email.value)) {
        document.getElementById("emailError").textContent = "Por favor, introduce un correo electrónico válido.";
        document.getElementById("emailError").style.display = "block";
        email.classList.add("is-invalid");
        isValid = false;
    }

    let errors = [];

    if (password.value.trim() === "") {
        errors.push("La contraseña es obligatoria.");
    } 
    if (password.value.trim().length < 6 || password.value.trim().length > 20) {
        errors.push("La contraseña debe tener entre 6 y 20 caracteres.");
    }
    if (!/[A-Z]/.test(password.value)) { 
        errors.push("La contraseña debe incluir al menos una letra mayúscula.");
    }
    if (!/\d/.test(password.value)) { 
        errors.push("La contraseña debe incluir al menos un número.");
    }
    if (errors.length > 0) {
        document.getElementById("passwordError").innerHTML = errors.join("<br>");
        document.getElementById("passwordError").style.display = "block";
        password.classList.add("is-invalid");
        isValid = false;
    } else {
        document.getElementById("passwordError").style.display = "none";
        password.classList.remove("is-invalid");
    }

    if (confirmPassword.value.trim() === "") {
        document.getElementById("confirmPasswordError").textContent = "Debes confirmar la contraseña.";
        document.getElementById("confirmPasswordError").style.display = "block";
        confirmPassword.classList.add("is-invalid");
        isValid = false;
    } else if (confirmPassword.value !== password.value) {
        document.getElementById("confirmPasswordError").textContent = "Las contraseñas no coinciden.";
        document.getElementById("confirmPasswordError").style.display = "block";
        confirmPassword.classList.add("is-invalid");
        isValid = false;
    }

    return isValid;  
}