<template>
  <div class="q-pa-md">
    <div class="container">
       <q-input 
       bg-color="teal-3"
      label-color="teal-10"
          bottom-slots 
          v-model="terminoBusqueda" 
          label="Buscar por nombre"  
          :dense="dense"
        >
          <template v-slot:append>
            <q-icon 
              v-if="terminoBusqueda !== ''" 
              name="close"
              bg-color="teal-10" 
              @click="limparBusqueda" 
              class="cursor-pointer" 
            />
            <q-icon 
            bg-color="teal-10"
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
      class="my-sticky-header-table bg-teal-7 text-white"
      bordered
      :rows="proveedor"
      :columns="columns"
      row-key="idProveedor"
      loading-label="Cargando proveedores..."
      no-data-label="No hay proveedores registrados"
    >
    <!-- 🔹 Botón Añadir Cliente cuando no hay datos -->
      <template v-slot:no-data>
        <div class="q-pa-md text-center">
          <div class="text-center q-mb-sm text-subtitle2" style="color: red;">No hay proveedor registrados</div>
          <q-btn 
            color="primary" 
            icon="add" 
            label="Añadir Proveedor" 
            @click="anadirProveedor"
          />
        </div>
      </template>
      <!-- 🔹 Botón Editar proveedor-->
      <template v-slot:body-cell-acciones="props">
        <q-td align="center">
          <q-btn
            color="primary"
            icon="edit"
            size="sm"
            label="Editar"
            square
            class="bg-teal-10 text-white"
            @click.stop="editarProveedor(props.row)"
          />
          <q-btn
            color="negative"
            icon="delete"
            size="sm"
            label="Eliminar"
            square
            class="q-ml-md q-px-md"
            @click.stop="eliminarProveedor(props.row)"
          />


        </q-td>
      </template>
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

/*nit: '',
    nombre: '',
    apellido: '',
    direccion: '',
    telefono: '',
    correoElectronico: '',
    estado: ''*/
const columns = [
  { name: 'nombre', label: 'Nombre', field: 'nombre', align: 'left' },
  { name: 'apellido', label: 'Apellido', field: 'apellido', align: 'left' },
  { name: 'direccion', label: 'Dirección', field: 'direccion', align: 'left' },
  { name: 'telefono', label: 'Teléfono', field: 'telefono', align: 'left' },
  { name: 'nit', label: 'NIT', field: 'nit', align: 'left' },
  { name: 'correoElectronico', label: 'Correo Electrónico', field: 'correoElectronico', align: 'left' },
  { name: 'estado', label: 'Estado', field: 'estado', align: 'left' },
  { name: 'acciones', label: 'Acciones', align: 'center' }
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

function editarProveedor(row) {
    if(!row.idProveedor){
        console.error('No se puede editar el eproveedor sin id')
        $q.notify({ type: 'warning', message: 'No se pudo obtener el id del proveedor' })
        return
    }
  router.push(`/proveedor/editar/${row.idProveedor}`)
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

//Anadir proveedor CUANDO NO ESTE EN LA LISTA 
function anadirProveedor(){
  router.push('/proveedor/nuevo')
}

//Eliminar cliente
async function eliminarProveedor(row) {
  try {
    $q.dialog({
      title: 'Confirmar Eliminación',
      message: '¿Está seguro de eliminar este Proveedor?',
      cancel: true,
      persistent: true
    }).onOk(async()=>{
      if(!row || !row.idProveedor){
        console.error('No se puede eliminar el proveedor sin id')
        $q.notify({ type: 'warning', message: 'No se pudo obtener el id del proveedor' })
        return
      }
      await ProveedorService.eliminarProveedor(row.idProveedor)
      $q.notify({
        type: 'positive',
        message: 'Proveedor fue eliminado exitosamente',
        icon: 'check_circle'
      })
      cargarProveedor()
    }).onCancel(() => {
      console.log('Cancelar')
    })

  } catch (error) {
    $q.notify({ 
        type: 'negative', 
        message: 'Error al eliminar proveedor' 
    })
    
  }  
}

</script>


<style>
.container {
  max-width: 50%;
  padding: 7px;
}
</style>