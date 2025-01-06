function deleteRecurso(idRecurso) {
    if (confirm('¿Estás segura de que quieres borrar este recurso?')) {
        fetch('/recurso/' + idRecurso, {
        method: 'DELETE', 
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            alert('Recurso eliminada correctamente');
            location.reload();  
        } else {
            alert('Error al eliminar el Recurso');
            }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Hubo un error al intentar eliminar el Recurso');
    });
    }
}   