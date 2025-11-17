<template>
  <div class="q-pa-md">
    <div class="container">
      <q-input 
        label-color="teal-10"
        bg-color="teal-3"
        bottom-slots v-model="terminoBusqueda" label="Buscar por nombre" :dense="dense" style="width: 40%;">
        <template v-slot:append>
          <q-icon bg-color="teal-10" v-if="terminoBusqueda !== ''" name="close" @click="limpiarBusqueda" class="cursor-pointer" />
          <q-icon bg-color="teal-10" name="search" @click="buscarCliente" class="cursor-pointer" />
        </template>
      </q-input>
    </div>
  </div>
  
  <div class="q-pa-md text-h5 text-center">Ver historial de compras del clientes</div>

  <div class="q-pa-md">
    <q-table
      flat
      virtual-scroll
      title="Lista de los clientes"
      class="my-sticky-header-table bg-teal-10 text-white"
      bordered
      :rows="clientes"
      :columns="columns"
      row-key="idCliente"
      loading-label="Cargando clientes..."
      no-data-label="No hay clientes registrados"
    >
      <template v-slot:body-cell-acciones="props">
        <q-td :props="props" class="q-gutter-sm">
          
          <q-btn
            color="primary"
            icon="receipt_long"
            dense
            class="bg-teal-7 text-white"
            round
            @click="verHistorial(props.row)"
          >
            <q-tooltip class="bg-teal-7 text-white">Ver Historial</q-tooltip>
          </q-btn>

        </q-td>
      </template>
      </q-table>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useQuasar } from 'quasar'
import ClienteService from '../../service/ClienteService' // Esto está correcto

const $q = useQuasar()
const router = useRouter()
const clientes = ref([])
const terminoBusqueda = ref('')
const dense = ref(true)

const columns = [
    { name: 'nombre', label: 'Nombre', field: 'nombre', align: 'left', sortable: true },
    { name: 'ci', label: 'CI', field: 'ci', align: 'left' },
    { name: 'correoElectronico', label: 'Correo Electrónico', field: 'correoElectronico', align: 'left' },
    { name: 'telefono', label: 'Teléfono', field: 'telefono', align: 'left' },
    { name: 'direccion', label: 'Dirección', field: 'direccion', align: 'left' },
    { name: 'acciones', label: 'Acciones', align: 'center' } // Esta columna la renderiza el slot
]

onMounted(async () => {
    await cargarClientes()
})

async function cargarClientes() {
    try {
        const response = await ClienteService.listarClientes()
        clientes.value = response.data
    } catch (error) {
        console.error('Error al obtener clientes', error)
        $q.notify({ type: 'negative', message: 'Error al cargar clientes' })
    }
}



// --- FUNCIÓN NUEVA PARA VER EL HISTORIAL ---
function verHistorial(row) {
  if (!row.idCliente) {
    console.error('No se puede ver el historial sin id');
    $q.notify({ type: 'warning', message: 'No se pudo obtener el id del cliente' });
    return;
  }
  // Usamos el router para ir a la página de historial que ya creamos,
  // pasándole el ID del cliente en la URL.
  router.push(`/cliente/historial/${row.idCliente}`);
}
// --- FIN DE LA FUNCIÓN NUEVA ---

watch(terminoBusqueda, async (nuevoValor) => {
    if (!nuevoValor.trim()) {
        await cargarClientes()
    } else {
        await buscarCliente()
    }
})

async function buscarCliente() {
    try {
        if (!terminoBusqueda.value.trim()) {
            await cargarClientes()
            return
        }
        const resp = await ClienteService.buscarCliente(terminoBusqueda.value)
        clientes.value = resp.data
    } catch (error) {
        console.error('Error al buscar clientes', error)
        $q.notify({ type: 'negative', message: 'Error al buscar clientes' })
    }
}

function limpiarBusqueda() {
    terminoBusqueda.value = ''
    buscarCliente()
}


</script>

<style>
.container {
    max-width: 50%;
    padding: 7px;
}
</style>