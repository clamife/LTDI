document.getElementById("productForm").addEventListener("submit", validateProductForm);

function validateProductForm(event) {
  // Prevenir el envío del formulario si hay errores
  event.preventDefault();

  // Obtener los campos
  const nombre = document.getElementById("nombre");
  const descripcion = document.getElementById("descripcion");
  const precio = document.getElementById("precio");

  // Obtener los elementos de error
  const nameError = document.getElementById("nameError");
  const descriptionError = document.getElementById("descriptionError");
  const priceError = document.getElementById("priceError");
  const urlModError = document.getElementById("photoModError");

  // Resetear los mensajes de error
  nameError.textContent = "";
  descriptionError.textContent = "";
  priceError.textContent = "";
  urlModError.textContent = "";

  let isValid = true;

  // Validar el nombre
  if (nombre.value.trim() === "") {
    nameError.textContent = "El nombre no puede estar vacío.";
    isValid = false;
  } else if (nombre.value.trim().length < 5) {
    nameError.textContent = "El nombre debe tener al menos 5 caracteres.";
    isValid = false;
  }

  // Validar la descripción
  if (descripcion.value.trim() === "") {
    descriptionError.textContent = "La descripción no puede estar vacía.";
    isValid = false;
  } else if (descripcion.value.trim().length > 300) {
    descriptionError.textContent = "La descripción no puede superar los 300 caracteres.";
    isValid = false;
  }

  // Validar el precio
  if (precio.value.trim() === "") {
    priceError.textContent = "El precio no puede estar vacío.";
    isValid = false;
  } else if (parseFloat(precio.value) <= 0) {
    priceError.textContent = "El precio debe ser mayor que 0.";
    isValid = false;
  }
  if (photoUrls.length === 0) {
    urlModError.textContent= "Debes proporcionar al menos una URL para las fotos.";
    isValid = false;
    }

    // Crear un input oculto con las URLs para enviarlas al backend
    const photoUrlsInput = document.createElement("input");
    photoUrlsInput.type = "hidden";
    photoUrlsInput.name = "urls"; // Nombre del parámetro que recibirá el backend
    photoUrlsInput.value = JSON.stringify(photoUrls); // Convertir el array en JSON

    // Añadir el input al formulario antes de enviarlo
    productForm.appendChild(photoUrlsInput);

  // Si todo es válido, se puede enviar el formulario
  if (isValid) {
    alert("Formulario válido. Procesando envío...");
     event.target.submit();
  }
}
// Inicializar las URLs cargadas al abrir la página
document.addEventListener("DOMContentLoaded", () => {
  const loadedUrls = Array.from(document.querySelectorAll("#photoUrlsMod span"));
  loadedUrls.forEach(urlElement => {
    photoUrls.push(urlElement.textContent.trim());
  });
});


let photoUrls = []; // Array para almacenar las URLs de fotos

  function addPhotoUrl() {
    const photoUrlInput = document.getElementById("photoUrl");
    const photoUrlsList = document.getElementById("photoUrlsMod");
    const errorContainer = document.getElementById("photoUrlError");

    // Validar la URL antes de agregarla
    if (photoUrlInput.value.trim() === "") {
      errorContainer.textContent = "La URL de la foto es obligatoria.";
      errorContainer.style.display = "block";
      photoUrlInput.classList.add("is-invalid");
      return;
    } else if (photoUrlInput.value.length < 10 || photoUrlInput.value.length > 50) {
      errorContainer.textContent = "La URL debe tener entre 10 y 50 caracteres.";
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