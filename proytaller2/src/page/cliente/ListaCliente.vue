<template>
  <div class="q-pa-md">
    <div class="container">
       <q-input 
          bottom-slots 
          v-model="terminoBusqueda" 
          label="Buscar por nombre"  
          :dense="dense"
          label-color="teal-10"
          bg-color="teal-3"
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
              @click="buscarCliente" 
              class="cursor-pointer "
            />
          </template>
        </q-input>
    </div>

    <q-table
      flat
      title="Lista de los clientes"
      class="my-sticky-header-table  bg-teal-7 text-white"
      bordered
      :rows="clientes"
      :columns="columns"
      row-key="idCliente"
      loading-label="Cargando clientes..."
      no-data-label="No hay clientes registrados"
    >
    <!-- 🔹 Botón Añadir Cliente cuando no hay datos -->
      <template v-slot:no-data>
        <div class="q-pa-md text-center">
          <div class="text-center q-mb-sm text-subtitle2" style="color: red;">No hay clientes registrados</div>
          <q-btn 
            color="primary" 
            icon="add" 
            label="Añadir Cliente" 
            @click="anadirCliente"
          />
        </div>
      </template>
      <!-- 🔹 Botón Editar Cliente -->
      <template v-slot:body-cell-acciones="props">
        <q-td align="center">
          <q-btn
            color="primary"
            icon="edit"
            size="sm"
            label="Editar"
            square
            class="bg-teal-10 text-white"
            @click.stop="editarCliente(props.row)"
          />
          <q-btn
            color="negative"
            icon="delete"
            size="sm"
            label="Eliminar"
            square
            class="q-ml-md q-px-md"
            @click.stop="eliminarCliente(props.row)"
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
import ClienteService from '../../service/ClienteService' // Asegúrate de tener este servicio configurado


const $q = useQuasar()
const router = useRouter()
const clientes = ref([])
const terminoBusqueda = ref('')
const dense = ref(true)

const columns = [
  { name: 'nombre', label: 'Nombre', field: 'nombre', align: 'left' },
  { name: 'ci', label: 'CI', field: 'ci', align: 'left' },
  { name: 'correoElectronico', label: 'Correo Electrónico', field: 'correoElectronico', align: 'left' },
  { name: 'telefono', label: 'Teléfono', field: 'telefono', align: 'left' },
  { name: 'direccion', label: 'Dirección', field: 'direccion', align: 'left' },
  { name: 'acciones', label: 'Acciones', align: 'center' }
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

function editarCliente(row) {
    if(!row.idCliente){
        console.error('No se puede editar el cliente sin id')
        $q.notify({ type: 'warning', message: 'No se pudo obtener el id del cliente' })
        return
    }
  router.push(`/cliente/editar/${row.idCliente}`)
}


watch(terminoBusqueda, async(nuevoValor) => {
    if(!nuevoValor.trim()){
      await cargarClientes()
    }else{
      await buscarCliente()
    }
})
//buscar cliente por nomnre
async function buscarCliente() {
  try {
    if(!terminoBusqueda.value.trim()){
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

//para limpiar la busqueda
function limparBusqueda(){
  terminoBusqueda.value = ''
  buscarCliente()
}

//Anadir cliente CUANDO NO ESTE EN LA LISTA 
function anadirCliente(){
  router.push('/cliente/nuevo')
}

//Eliminar cliente
async function eliminarCliente(row) {
  try {
    $q.dialog({
      title: 'Confirmar Eliminación',
      message: '¿Está seguro de eliminar este cliente?',
      cancel: true,
      persistent: true
    }).onOk(async()=>{
      if(!row || !row.idCliente){
        console.error('No se puede eliminar el cliente sin id')
        $q.notify({ type: 'warning', message: 'No se pudo obtener el id del cliente' })
        return
      }
      await ClienteService.deleteCliente(row.idCliente)
      $q.notify({
        type: 'positive',
        message: 'Cliente eliminado exitosamente',
        icon: 'check_circle'
      })
      cargarClientes()
    }).onCancel(() => {
      console.log('Cancelar')
    })

  } catch (error) {
    $q.notify({ 
        type: 'negative', 
        message: 'Error al eliminar cliente' 
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