// src/service/service.js
import axios from "axios"

const API_URL = "http://localhost:9090/api/cliente"

const apiClient = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json"
  }
})
export default {
  //añadir cliente
  addClient(cliente){
    return apiClient.post("/anadir", cliente)
  },

  //eliminar cliente
  deleteCliente(idCliente){
    return apiClient.delete(`/eliminar-client/${idCliente}`)
  },

  //actualizar cliente
  updateClient(idCliente, cliente){
    return apiClient.put(`/update/${idCliente}`, cliente)
  },

  //buscar cliente
  buscarCliente(nombre){
    return apiClient.get(`/buscar-cliente?nombre=${nombre}`)
  },

  //Lista de todos los clientes
  listarClientes() {
    return apiClient.get("/listar-cliente");
  },

  //Obtener cliente por id
  obtenerClientePorId(idCliente){
    return apiClient.get(`/obtener-cliente/${idCliente}`)
  }

}