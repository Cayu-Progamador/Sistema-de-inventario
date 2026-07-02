import axios from 'axios'   

const API_URL = 'http://localhost:9090/api/proveedor'

const apiProveedor = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})


export default{
    
    //listar proveedores
    listProveedor(){
        return apiProveedor.get()
    },

    //bucar provedores por nombre
    buscarPorNombre(nombre){
        return apiProveedor.get(`/buscar?nombre=${nombre}`)
    },
    
    //obtener proveedor por id
    obtenerPorId(id){
        return apiProveedor.get(`/${id}`)
    },
    
    //crear proveedor
    crearProveedor(proveedor){
        return apiProveedor.post('/crear',proveedor)
    },
    
    //actualizar proveedor
    actualizarProveedor(id,proveedor){
        return apiProveedor.put(`/${id}`,proveedor)
    },

    //eliminar proveedor
    eliminarProveedor(id){
        return apiProveedor.delete(`/${id}`)
    }
}