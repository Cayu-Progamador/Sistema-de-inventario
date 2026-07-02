<template>
  <div class="q-pa-md">
    <div class="container">
       <q-input 
          bottom-slots 
          v-model="terminoBusqueda" 
          label="Buscar por nombre"  
          :dense="dense"
        >
          <template v-slot:append>
            <q-icon 
              v-if="terminoBusqueda !== ''" 
              name="close" 
              @click="limparBusqueda" 
              class="cursor-pointer" 
            />
            <q-icon 
              name="search" 
              @click="buscarProveedor" 
              class="cursor-pointer"
            />
          </template>
        </q-input>
    </div>

    <q-table
      flat
      title="Lista de los proveedores"
      class="my-sticky-header-table"
      bordered
      :rows="proveedor"
      :columns="columns"
      row-key="idProveedor"
      loading-label="Cargando proveedores..."
      no-data-label="No hay proveedores registrados"
    >
    
    </q-table>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useQuasar } from 'quasar'
import ProveedorService from '../../service/ProveedorService' // Asegúrate de tener este servicio configurado


const $q = useQuasar()
const router = useRouter()
const proveedor = ref([])
const terminoBusqueda = ref('')
const dense = ref(true)


const columns = [
  { name: 'nombre', label: 'Nombre', field: 'nombre', align: 'left' },
  { name: 'apellido', label: 'Apellido', field: 'apellido', align: 'left' },
  { name: 'direccion', label: 'Dirección', field: 'direccion', align: 'left' },
  { name: 'telefono', label: 'Teléfono', field: 'telefono', align: 'left' },
  { name: 'nit', label: 'NIT', field: 'nit', align: 'left' },
  { name: 'correoElectronico', label: 'Correo electrónico', field: 'correoElectronico', align: 'left' },
  { name: 'estado', label: 'Estado', field: 'estado', align: 'left' }
]



onMounted(async () => {
  await cargarProveedor()
})

async function cargarProveedor() {
  try {
    const response = await ProveedorService.listProveedor()
    console.log("respuesta del backend",response)
    
    proveedor.value = response.data
  } catch (error) {
    console.error('Error al obtener proveedor', error)
    $q.notify({ type: 'negative', message: 'Error al cargar proveedores' })
  }
}




watch(terminoBusqueda, async(nuevoValor) => {
    if(!nuevoValor.trim()){
      await cargarProveedor()
    }else{
      await buscarProveedor()
    }
})

//buscar cliente por nomnre
async function buscarProveedor() {
  try {
    if(!terminoBusqueda.value.trim()){
      await cargarProveedor()
      return
    }

    const resp = await ProveedorService.buscarPorNombre(terminoBusqueda.value)
    proveedor.value = resp.data

  } catch (error) {
    console.error('Error al buscar proveedor', error)
    $q.notify({ type: 'negative', message: 'Error al buscar proveedor' })
  }
    
}

//para limpiar la busqueda
function limparBusqueda(){
  terminoBusqueda.value = ''
  buscarProveedor()
}

</script>


<style>
.container {
  max-width: 50%;
  padding: 7px;
}
</style>