import axios from "axios"

const API_URL = "http://localhost:9090/api/empleado"

const apiEmpleado = axios.create({
    baseURL: API_URL,
    headers: {
        "Content-Type": "application/json"
    }
})

export default {
    
    //añadir empleado
    addEmpleado(empleado){
        return apiEmpleado.post("/agregar", empleado)
    },

    //eliminar empleado
    deleteEmpleado(idEmpleado){
        return apiEmpleado.delete(`/eliminar/${idEmpleado}`)
    },

    //actualizar empleado
    updateEmpleado(idEmpleado, empleado){
        return apiEmpleado.put(`/update/${idEmpleado}`, empleado)
    },

    //buscar empleado por nombre
    buscarEmpleado(nombre){
        return apiEmpleado.get(`/buscar?nombre=${nombre}`)
    },

    //Lista de todos los empleados
    listarEmpleados() {
        return apiEmpleado.get("/ver-empleados");
    },

    //obtener empleado por id
    obtenerEmpleadoPorId(idEmpleado){
        return apiEmpleado.get(`/obtener/${idEmpleado}`)
    }
}