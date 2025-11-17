import axios from 'axios';

// 1. Define the base URL for your VentaController
const API_URL = 'http://localhost:9090/api/ventas';

// 2. Create an Axios instance (optional but good practice)
const apiClient = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    }
});

// 3. Export the function to register the sale
export default {
    /**
     * Sends the sale data to the backend.
     * @param {object} ventaRequestDTO - The DTO containing sale details (employee, method, total, items).
     * @returns {Promise} - The Axios promise for the POST request.
     */
    registrar(ventaRequestDTO) {
        // Calls POST /api/ventas/registrar
        return apiClient.post('/registrar', ventaRequestDTO);
    },

    //Ver hostorial de compras del cliente

    getHistorialCliente(idCliente){
        return apiClient.get(`/historial/${idCliente}`)
    },
    //listar ventas
    listarVentas(){
        return apiClient.get('/lista-dto')
    }

};