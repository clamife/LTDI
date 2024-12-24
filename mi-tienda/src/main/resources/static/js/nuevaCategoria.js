function validateProductForm(event) {
    const productName = document.getElementById('productName');
    const description = document.getElementById('description');
    const productNameError = document.getElementById('productNameError');
    const descriptionError = document.getElementById('descriptionError');
    let isValid = true;

    // Validar el nombre del producto
    if (!productName.value.trim()) {
        productNameError.textContent = 'El nombre es obligatorio.';
        productNameError.style.display = 'block';
        isValid = false;
    } else if (productName.value.trim().length < 4 || productName.value.trim().length > 40) {
        productNameError.textContent = 'El nombre debe tener entre 4 y 40 caracteres.';
        productNameError.style.display = 'block';
        isValid = false;
    } else {
        productNameError.style.display = 'none';
    }

    // Validar la descripción
    if (!description.value.trim()) {
        descriptionError.textContent = 'La descripción es obligatoria.';
        descriptionError.style.display = 'block';
        isValid = false;
    } else if (description.value.trim().length < 10 || description.value.trim().length > 300) {
        descriptionError.textContent = 'La descripción debe tener entre 10 y 300 caracteres.';
        descriptionError.style.display = 'block';
        isValid = false;
    } else {
        descriptionError.style.display = 'none';
    }

    // Evitar envío si hay errores
    if (!isValid) {
        event.preventDefault();
    }

    return isValid;
    }
