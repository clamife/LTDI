function deleteCategory(categoryId) {
    if (confirm('¿Estás segura de que quieres borrar esta categoría?')) {
        fetch('/categoria/' + categoryId, {
        method: 'DELETE', 
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            alert('Categoría eliminada correctamente');
            location.reload();  
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