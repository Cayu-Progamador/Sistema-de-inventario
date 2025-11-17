import axios from 'axios'

const API_URL = 'http://localhost:9090/api/productos'

const apiClient = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})


//expportamos las funciones que llamaremos de tu frontend
export default{
    //para el boton "Escanner codigo de barra"
    buscarPorCodigoBarra(codigo){
        return apiClient.get(`/barcode/${codigo}`)
    },

    //para el boton buscar 
    buscarPorTermino(termino){
        return apiClient.get(`/buscar?termino=${termino}`)
    },

    //para el desplegable a granel 
    getTipoDeProducto(){
        return apiClient.get(`/tipos`)
    },

    //para el despieble "pollo"
    getProductoTipo(tipo){
        return apiClient.get(`/por-tipo/${tipo}`)
    },
    //ver todos los productos que pertenescas proveedor
    listarTodosProductosProveedor(){
        return apiClient.get(`/ver`)    
    },



    //listar todos los productos
    listarTodosProductos(){
        return apiClient.get(`/todos`)
    },

    //agregar productos 
    agregarProductos(productoDTO){
        return apiClient.post(`/agregar`, productoDTO)
    },
    
    //editar productos 
    editarProducto(id, productoDTO){
        return apiClient.put(`/${id}`, productoDTO)
    },

    //eliminar productos 
    eliminarProducto(id){
        return apiClient.delete(`/${id}`)
    },
    // obtener producto por id
    obtenerProductoPorId(id){
        return apiClient.get(`/${id}`)
    },

    // buscar productos por nombre

    buscarPorNombre(nombre){
        return apiClient.get(`/nombre?nombre=${nombre}`)
    }

}