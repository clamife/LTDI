function eliminarDetalle(idProducto) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "Este producto se eliminará del carrito.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminarlo',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/eliminarDetalle/${idProducto}`, { method: 'DELETE' })
                .then(response => {
                    if (response.ok) {
                        Swal.fire(
                            'Eliminado!',
                            'El producto ha sido eliminado del carrito.',
                            'success'
                        ).then(() => {
                            location.reload();
                        });
                    } else {
                        Swal.fire(
                            'Error',
                            'No se pudo eliminar el producto.',
                            'error'
                        );
                    }
                });
        }
    });
}
