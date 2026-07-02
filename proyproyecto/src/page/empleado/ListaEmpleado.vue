<template>
  <div class="q-pa-md">
    <div class="container">
       <q-input 
          bottom-slots 
          v-model="terminoBusqueda" 
          label="Buscar por nombre"  
          :dense="dense"
          bg-color="teal-3"
      label-color="teal-10"
        >
          <template v-slot:append>
            <q-icon 
             bg-color="teal-10"
              v-if="terminoBusqueda !== ''" 
              name="close" 
              @click="limparBusqueda" 
              class="cursor-pointer" 
            />
            <q-icon 
             bg-color="teal-10"
              name="search" 
              @click="buscarEmpleado" 
              class="cursor-pointer"
            />
          </template>
        </q-input>
    </div>

    <q-table
      flat
      title="Lista de los empleados"
      class="my-sticky-header-table bg-teal-7 text-white"
      bordered
      :rows="empleados"
      :columns="columns"
      row-key="idEmpleado"
      loading-label="Cargando empleados..."
      no-data-label="No hay Empleados registrados"
    >
    <!-- 🔹 Botón Añadir Cliente cuando no hay datos -->
      <template v-slot:no-data>
        <div class="q-pa-md text-center">
          <div class="text-center q-mb-sm text-subtitle2" style="color: red;">No hay empleados registrados</div>
          <q-btn 
            color="primary" 
            icon="add" 
            label="Añadir Empleado" 
            @click="anadirEmpleado"
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
            @click.stop="editarEmpleado(props.row)"
          />
          <q-btn
            color="negative"
            icon="delete"
            size="sm"
            label="Eliminar"
            square
            class="q-ml-md q-px-md"
            @click.stop="eliminarEmpleado(props.row)"
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
import EmpleadoService from '../../service/EmpleadoService' // Asegúrate de tener este servicio configurado


const $q = useQuasar()
const router = useRouter()
const empleados = ref([])
const terminoBusqueda = ref('')
const dense = ref(true)

//  nombre: '',
//   ci: '',
//   apellido: '',
//   estado: 'activo',
//   correo: '',
//   direccion: '',
//   telefono: ''
const columns = [
  { name: 'nombre', label: 'Nombre', field: 'nombre', align: 'left' },
  { name: 'ci', label: 'CI', field: 'ci', align: 'left' },
  { name: 'apellido', label: 'Apellido', field: 'apellido', align: 'left' },
  { name: 'estado', label: 'Estado', field: 'estado', align: 'left' },
  { name: 'correo', label: 'Correo', field: 'correo', align: 'left' },
  { name: 'direccion', label: 'Dirección', field: 'direccion', align: 'left' },
  { name: 'telefono', label: 'Teléfono', field: 'telefono', align: 'left' },
  { name: 'acciones', label: 'Acciones', align: 'center' }
]



onMounted(async () => {
  await cargarEmpleados()
})

async function cargarEmpleados() {
  try {
    const response = await EmpleadoService.listarEmpleados()
    empleados.value = response.data
  } catch (error) {
    console.error('Error al obtener empleados', error)
    $q.notify({ type: 'negative', message: 'Error al cargar empleados' })
  }
}

function editarEmpleado(row) {
    if(!row.idEmpleado){
        console.error('No se puede editar el empleado sin id')
        $q.notify({ type: 'warning', message: 'No se pudo obtener el id del empleado' })
        return
    }
  router.push(`/empleado/editar/${row.idEmpleado}`)
}


watch(terminoBusqueda, async(nuevoValor) => {
    if(!nuevoValor.trim()){
      await cargarEmpleados()
    }else{
      await buscarEmpleado()
    }
})
//buscar cliente por nomnre
async function buscarEmpleado() {
  try {
    if(!terminoBusqueda.value.trim()){
      await cargarEmpleados()
      return
    }

    const resp = await EmpleadoService.buscarEmpleado(terminoBusqueda.value)
    empleados.value = resp.data

  } catch (error) {
    console.error('Error al buscar empleado', error)
    $q.notify({ type: 'negative', message: 'Error al buscar empleado' })
  }
    
}

//para limpiar la busqueda
function limparBusqueda(){
  terminoBusqueda.value = ''
  buscarEmpleado()
}

//Anadir cliente CUANDO NO ESTE EN LA LISTA 
function anadirEmpleado(){
  router.push('/empleado/nuevo')
}

//Eliminar cliente
async function eliminarEmpleado(row) {
  try {
    $q.dialog({
      title: 'Confirmar Eliminación',
      message: '¿Está seguro de eliminar este empleado?',
      cancel: true,
      persistent: true
    }).onOk(async()=>{
      if(!row || !row.idEmpleado){
        console.error('No se puede eliminar el empleao sin id')
        $q.notify({ type: 'warning', message: 'No se pudo obtener el id del empleado' })
        return
      }
      await EmpleadoService.deleteEmpleado(row.idEmpleado)
      $q.notify({
        type: 'positive',
        message: 'Empleado eliminado exitosamente',
        icon: 'check_circle'
      })
      cargarEmpleados()
    }).onCancel(() => {
      console.log('Cancelar')
    })

  } catch (error) {
    $q.notify({ 
        type: 'negative', 
        message: 'Error al eliminar empleado' 
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