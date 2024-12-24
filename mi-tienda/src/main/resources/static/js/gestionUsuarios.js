function deleteUser(userId) {
    // Confirmación antes de borrar
        if (confirm('¿Estás seguro de que quieres borrar este usuario?')) {
      // Llamada al backend para borrar el usuario
          fetch('/usuarios/' + userId, {
          method: 'DELETE', // Método HTTP para eliminar
          headers: {
            'Content-Type': 'application/json'
          }
        })
        .then(response => {
          if (response.ok) {
            alert('Usuario eliminado correctamente');
            location.reload();  // Recarga la página para mostrar la lista actualizada
          } else {
            alert('Error al eliminar el usuario');
          }
        })
        .catch(error => {
          console.error('Error:', error);
          alert('Hubo un error al intentar eliminar el usuario');
        });
      }
    }