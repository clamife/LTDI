let photoUrls = []; // Array para almacenar las URLs de las fotos

// Función para validar el formulario de producto
function validateProductForm(event) {
  // Reseteo de errores previos y clases
  document.getElementById("productNameError").style.display = "none";
  document.getElementById("descriptionError").style.display = "none";
  document.getElementById("photoUrlError").style.display = "none";
  document.getElementById("priceError").style.display = "none";

  let isValid = true;
  let productName = document.getElementById("productName");
  let description = document.getElementById("description");
  let price = document.getElementById("price");

  // Reseteo las clases de validación
  productName.classList.remove("is-invalid");
  description.classList.remove("is-invalid");
  price.classList.remove("is-invalid");

  // Validación del nombre del producto
  if (productName.value.trim() === "") {
    document.getElementById("productNameError").textContent = "El nombre del producto es obligatorio.";
    document.getElementById("productNameError").style.display = "block";
    productName.classList.add("is-invalid");
    isValid = false;
  }

  // Validación de la descripción
  if (description.value.trim() === "") {
    document.getElementById("descriptionError").textContent = "La descripción es obligatoria.";
    document.getElementById("descriptionError").style.display = "block";
    description.classList.add("is-invalid");
    isValid = false;
  } else if (description.value.length > 500) {
    document.getElementById("descriptionError").textContent = "La descripción no puede superar los 500 caracteres.";
    document.getElementById("descriptionError").style.display = "block";
    description.classList.add("is-invalid");
    isValid = false;
  }


  // Validación del precio
  if (price.value.trim() === "") {
    document.getElementById("priceError").textContent = "El precio es obligatorio.";
    document.getElementById("priceError").style.display = "block";
    price.classList.add("is-invalid");
    isValid = false;
  } else if (parseFloat(price.value) <= 0) {
    document.getElementById("priceError").textContent = "El precio debe ser mayor a 0.";
    document.getElementById("priceError").style.display = "block";
    price.classList.add("is-invalid");
    isValid = false;
  }


  if (photoUrls.length === 0) {
    alert('Debes proporcionar al menos una URL para las fotos.');
    event.preventDefault();
    return false;
}

// Asegurarse de que las URLs se envíen como parámetro en el formulario
  const photoUrlsInput = document.createElement('input');
  photoUrlsInput.type = 'hidden';
  photoUrlsInput.name = 'urls'; // Nombre del parámetro que recibirá el backend
  photoUrlsInput.value = JSON.stringify(photoUrls); // Convertir el array en una cadena JSON

// Añadir el input al formulario antes de enviarlo
  document.getElementById('productForm').appendChild(photoUrlsInput);


  return isValid;  // Si todo es válido, el formulario se enviará
}

// Función para agregar la URL de la foto a la lista
function addPhotoUrl() {
  const photoUrlInput = document.getElementById("photoUrl");
  const photoUrlsList = document.getElementById("photoUrls");
  const errorContainer = document.getElementById("photoUrlError");

  // Validar la URL antes de agregarla
  if (photoUrlInput.value.trim() === "") {
    errorContainer.textContent = "La URL de la foto es obligatoria.";
    errorContainer.style.display = "block";
    photoUrlInput.classList.add("is-invalid");
    return;
  } else if (photoUrlInput.value.length < 10 || photoUrlInput.value.length > 100) {
    errorContainer.textContent = "La URL debe tener entre 10 y 100 caracteres.";
    errorContainer.style.display = "block";
    photoUrlInput.classList.add("is-invalid");
    return;
  }

  // Esconder el error si la URL es válida
  errorContainer.style.display = "none";
  photoUrlInput.classList.remove("is-invalid");

  // Crear un nuevo elemento de lista para la URL
  const listItem = document.createElement("li");
  listItem.classList.add("d-flex", "justify-content-between", "align-items-center");
  listItem.innerHTML = `
      <span>${photoUrlInput.value}</span>
      <button type="button" class="btn btn-sm btn-danger" onclick="removePhotoUrl(this)">Eliminar</button>
  `;
  photoUrlsList.appendChild(listItem);

  // Añadir la URL al array de fotos
  photoUrls.push(photoUrlInput.value);

  // Limpiar el campo de entrada
  photoUrlInput.value = "";
}

function removePhotoUrl(button) {
  // Eliminar el item de la lista
  const listItem = button.closest("li");
  const urlToRemove = listItem.querySelector("span").textContent;
  photoUrls = photoUrls.filter(url => url !== urlToRemove);
  listItem.remove();
}
