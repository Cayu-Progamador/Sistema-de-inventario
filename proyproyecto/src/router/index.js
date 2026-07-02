import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../layouts/index.vue' 
import Dashborard from '../layouts/modules/MainContent.vue'
import ClienteAgregar from '../page/cliente/ClienteAgregar.vue'
import ClienteEdit from '../page/cliente/EditarCliente.vue'
import ListaClient from '../page/cliente/ListaCliente.vue'
import DetalleCLiente from '../page/cliente/DetalleCliente.vue'
import HistorialCliente from '../page/cliente/HistorialCliente.vue'
import AñadirEmpleado from '../page/empleado/EmpleadoAgregar.vue'
import ListaEmpleado from '../page/empleado/ListaEmpleado.vue'
import EditarEmpleado from '../page/empleado/EditarEmpleado.vue'
import AnadirProveedor from '../page/proveedor/AñadirProveedor.vue'
import ActualizarProveedor from '../page/proveedor/ActualizarProveedor.vue'
import ListProveedor from '../page/proveedor/ListProveedor.vue'
import ConsultarListar from '../page/proveedor/ConsultarListar.vue'
import PedidoProveedor from '../page/pedido/RalizarPedido.vue'
import ProductoAgregar from '../page/producto/AgregarProducto.vue'
import ListaProducto from '../page/producto/ListaProducto.vue'
import EditarProducto from '../page/producto/EditarProducto.vue'
import DetalleProducto from '../page/producto/DetalleProducto.vue'
import AnadirVenta from '../page/venta/AñadirVenta.vue'
import ListarVenta from '../page/venta/VentaLista.vue'

const routes = [
  {
    path: '/',
    component: MainLayout,

    children:[
      {
        path: '',
        name:'dashboard',
        component:Dashborard
      },
     

      //cliente nuevo

      {
        path: '/cliente/nuevo',
        name:'nuevoCliente',
        component:ClienteAgregar
      },
      
      //Editar cliente
      {
        path: '/cliente/editar/:idCliente',
        name:'editarCliente',
        component:ClienteEdit
      },

      //Lista de clientes
      {
        path: '/cliente/lista',
        name:'listaCliente',
        component:ListaClient
      },

      //Detalle de cliente
      {
        path: '/cliente/detalle',
        name:'detalleCliente',
        component:DetalleCLiente
      },

      // Historial de compras

      {
        path: '/cliente/historial/:idCliente',
        name:'historialCliente',
        component:HistorialCliente
      },

      //añadir empleado 
      {
        path: '/empleado/nuevo',
        name:'nuevoEmpleado',
        component:AñadirEmpleado
      },
      //Lista de empleados
      {
        path: '/empleado/lista',
        name:'listaEmpleado',
        component:ListaEmpleado
      },
      //Editar empleado
      {
        path: '/empleado/editar/:idEmpleado',
        name:'editarEmpleado',
        component:EditarEmpleado
      },
      // añadir proveedor
      {
        path: '/proveedor/nuevo',
        name:'nuevoProveedor',
        component:AnadirProveedor
      },
      //Editar proveedor
      {
        path: '/proveedor/editar/:idProveedor',
        name:'editarProveedor',
        component:ActualizarProveedor
      },
      
      //Lista de proveedores
      {
        path: '/proveedor/lista',
        name:'listaProveedor',
        component:ListProveedor
      },
      //consultar la lista de prveedores
      {
        path: '/proveedor/consultar',
        name:'consultarProveedor',
        component:ConsultarListar
      },
      // Ralizar pedido
      {
        path: '/pedido/realizar',
        name:'pedidoRealizar',
        component:PedidoProveedor
      },
      //productos anadir 

      {
        path: '/producto/nuevo',
        name:'nuevoProducto',
        component:ProductoAgregar
      },
      //ver todos los productos
      {
        path: '/producto/lista',
        name:'listaProducto',
        component:ListaProducto
      },
      //editar producto
      {
        path: '/producto/editar/:idProducto',
        name:'editarProducto',
        component:EditarProducto
      },
      //detalle producto    
      {
        path: '/producto/detalle',
        name:'detalleProducto',
        component:DetalleProducto
      },
      //añadir venta
      {
        path: '/venta/nuevo',
        name:'nuevoVenta',
        component:AnadirVenta
      },
      {
        path: '/venta/lista',
        name:'listaVenta',
        component:ListarVenta
      }
      
    ]
    
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router