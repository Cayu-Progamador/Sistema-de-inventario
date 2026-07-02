import axios from 'axios'       

const BASE_URL = 'http://localhost:9090/api/pedido'

const apiPedido = axios.create({
    baseURL: BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})

export default {
    crearPedido(pedidoDto){
        return apiPedido.post('/crear', pedidoDto)
    },

    listarPedidos(){
        return apiPedido.get()
    }


}