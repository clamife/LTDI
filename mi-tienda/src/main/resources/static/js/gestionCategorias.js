function deleteCategory(categoryId) {
    // Confirmación antes de borrar
    if (confirm('¿Estás segura de que quieres borrar esta categoría?')) {
        // Llamada al backend para borrar la categoría
        fetch('/categoria/' + categoryId, {
        method: 'DELETE', // Método HTTP para eliminar
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            alert('Categoría eliminada correctamente');
            location.reload();  // Recarga la página para mostrar la lista actualizada
        } else {
            alert('Error al eliminar la categoría');
            }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Hubo un error al intentar eliminar la categoría');
    });
    }
}   