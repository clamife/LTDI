function validateProductForm(event) {
    let isValid = true;

    const nombreRecurso = document.getElementById('productName').value;
    const tipoRecurso = document.getElementById('tipoRecurso').value;
    const rutaRecurso = document.getElementById('rutaRecurso').value;

    clearErrors();

    if (nombreRecurso.trim() === "") {
        showError('productNameError', 'El nombre del recurso no puede estar vacío');
        isValid = false;
    } else if (nombreRecurso.length > 100) {
        showError('productNameError', 'El nombre del recurso no puede superar los 100 caracteres');
        isValid = false;
    }

    if (tipoRecurso === "") {
        showError('tipoRecursoError', 'Selecciona un tipo de recurso');
        isValid = false;
    }

    if (rutaRecurso.trim() === "") {
        showError('rutaRecursoError', 'La ruta del recurso no puede estar vacía');
        isValid = false;
    }

    if (!isValid) {
        event.preventDefault();
    }

    return isValid;
}

function showError(elementId, message) {
    const errorElement = document.getElementById(elementId);
    errorElement.textContent = message;
    errorElement.style.display = 'block';
}

function clearErrors() {
    const errorElements = document.querySelectorAll('.invalid-feedback');
    errorElements.forEach(error => {
        error.style.display = 'none';
        error.textContent = '';
    });
}
