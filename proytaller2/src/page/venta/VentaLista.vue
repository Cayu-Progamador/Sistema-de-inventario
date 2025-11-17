<template>
    <q-card style="height: 100vh; overflow-y: none;">
        <q-card-section>
            <div class="text-h6 text-center">Lista de Ventas</div>
        </q-card-section>

        <div class="container">
            <q-input bg-color="teal-1"  bottom-slots v-model="terminoBusqueda" label-color="teal-10" label="Buscar producto por nombre" :dense="dense" color="teal-10">
                <template v-slot:append>
                    <q-icon v-if="terminoBusqueda !== ''" name="close" @click="limparBusqueda" class="cursor-pointer" color="teal-10" />
                    <q-icon name="search" @click="buscarVentasPorName" class="cursor-pointer" color="teal-10"/>
                </template>
            </q-input>
        </div>
        <q-card-section>
            <div class="q-pa-md">
                <q-table flat title="Lista de los ventas" class="my-sticky-header-table bg-teal-10 text-white" bordered :rows="ventas"
                    :columns="columnas" row-key="idDetalleVenta" loading-label="Cargando lista de ventas..."
                    no-data-label="No hay Ventas registrados"
                      :pagination.sync="paginacion"
                        rows-per-page-label="Filas por página:" 
                    >
                </q-table>
            </div>
        </q-card-section>
    </q-card>
</template>

<script setup>
import { ref, onMounted ,watch} from "vue"
import ListarVentas from "../../service/VentaService"
import BuscarProducto from '../../service/ProductoService'

const paginacion = ref({
  page: 1,          // página inicial (si cada página tiene 5 filas, page 2 empieza en fila 6-10)
  rowsPerPage: 10,  // filas por página
  descending: false,
});

const terminoBusqueda = ref('')
const ventas = ref([]);
const dense = ref(true)

// Columnas de la tabla
const columnas = [
    { name: "nombreEmpleado", label: "Empleado", field: "nombreEmpleado", align: "left" },
    { name: "nombreProducto", label: "Producto", field: "nombreProducto", align: "left" },
    { name: "fechaVenta", label: "Fecha", align: "left", field: row => new Date(row.fechaVenta).toLocaleDateString() },
    { name: "cantidad", label: "Cantidad", field: "cantidad", align: "center" },
    { name: "precioUnitario", label: "Precio Unitario", field: "precioUnitario", align: "right" },
    { name: "subtotal", label: "Subtotal", field: "subtotal", align: "right" },
];

function limparBusqueda(){
  terminoBusqueda.value = ''
  buscarVentasPorName()
}

watch(terminoBusqueda, async(nuevoValor) => {
    if(!nuevoValor.trim()){
      await cargarVentas()
    }else{
      await buscarVentasPorName()
    }
})

async function buscarVentasPorName() {
  try {
    if(!terminoBusqueda.value.trim()){
      await cargarVentas()
      return
    }

    const resp = await BuscarProducto.buscarPorNombre(terminoBusqueda.value)
    ventas.value = resp.data

  } catch (error) {
    console.error('Error al buscar proveedor', error)
    $q.notify({ type: 'negative', message: 'Error al buscar Productos' })
  }
    
}

const cargarVentas = async () => {
    try {
        const response = await ListarVentas.listarVentas();
        ventas.value = response.data; // <- aquí accedemos al array real
    } catch (error) {
        console.error("Error al cargar ventas:", error);
    }
};


// Cargar ventas al montar el componente
onMounted(() => {
    cargarVentas();
});
</script>
