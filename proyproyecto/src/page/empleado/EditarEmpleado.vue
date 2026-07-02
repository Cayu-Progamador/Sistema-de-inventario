<template>
  <div class="q-pa-md container">
    <div class="text-h5 text-center q-mb-md">Editar Empleado</div>

    <q-form @submit.prevent="actualizarEmpleado" class="q-gutter-md">
        <q-input
            clear-icon="close"
            filled
            color="purple-12"
            bg-color="teal-3"
            label-color="teal-10"
            clearable
            v-model="empleado.nombre"
            label="Nombre"
            :rules="[val => !!val || 'El nombre es obligatorio']"
        />

        <q-input
            bg-color="teal-3"
            label-color="teal-10"
            clear-icon="close"
            filled
            color="purple-12"
            clearable
            v-model="empleado.apellido"
            label="Apellidos"
            :rules="[val => !!val || 'El apellido es obligatorio']"
        />

        <q-input
            clear-icon="close"
            filled
            color="purple-12"
            clearable
            bg-color="teal-3"
            label-color="teal-10"
            v-model="empleado.ci"
            label="Ci"
            :rules="[val => !!val || 'El ci es obligatorio']"
        />
        <q-input
            clear-icon="close"
            filled
            bg-color="teal-3"
            label-color="teal-10"
            color="purple-12"
            clearable
            v-model="empleado.direccion"
            label="Dirección"
            :rules="[val => !!val || 'La dirección es obligatoria']"
        />

        <q-input
            clear-icon="close"
            filled
            bg-color="teal-3"
            label-color="teal-10"
            color="purple-12"
            clearable
            v-model="empleado.correo"
            type="email"
            label="Correo electrónico"
            :rules="[
            val => !!val || 'El correo electrónico es obligatorio',
            val => /.+@.+\..+/.test(val) || 'Correo no válido'
            ]"
        />

        <q-input
            clear-icon="close"
            filled
            bg-color="teal-3"
            label-color="teal-10"
            color="purple-12"
            clearable
            v-model="empleado.estado"
            label="Estado"
            :rules="[val => !!val || 'El estado es obligatorio']"
        />

        <q-input
            bg-color="teal-3"
            label-color="teal-10"
            clear-icon="close"
            filled
            color="purple-12"
            clearable
            v-model="empleado.telefono"
            label="Teléfono"
            :rules="[val => !!val || 'El teléfono es obligatorio']"
        />
        
        <div class="q-mt-md">
            <q-btn
            label="Guardar Cambios"
            type="submit"          
            icon="save"
            class="bg-teal-10 text-white"

            />
            <q-btn
            label="Cancelar"
            flat
            icon="arrow_back"
            class="q-ml-sm q-pl-ms bg-teal-10 text-white"
            @click="volver"
            />
        </div>
    </q-form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useQuasar } from 'quasar'
import EmpleadoService from '../../service/EmpleadoService'

const route = useRoute()
const router = useRouter()
const $q = useQuasar()

const empleado = ref({
  nombre: '',
  ci: '',
  apellido: '',
  estado: '',
  correo: '',
  direccion: '',
  telefono: ''
})

// Cargar datos del cliente al montar
onMounted(async () => {
  const id = route.params.idEmpleado
  try {
    const response = await EmpleadoService.obtenerEmpleadoPorId(id)
    empleado.value = response.data
  } catch (error) {
    console.error('Error al cargar empleado', error)
    $q.notify({ type: 'negative', message: 'Error al cargar empleado' })
  }
})

// Guardar cambios
async function actualizarEmpleado() {
  const id = route.params.idEmpleado
  try {
    $q.dialog({
        dark: true,
        title: '¿Confirmar cambios?',
        message: '¿Deseas guardar los cambios?',
        cancel: true,
        persistent: true
    }).onOk(async () => {
        await EmpleadoService.updateEmpleado(id, empleado.value)
        $q.notify({
            type: 'positive',
            message: 'Empleado actualizado con éxito'
        })
    }).onCancel(() => {
        console.log('Cancelar')
    })

    
    router.push('/empleado/lista')
  } catch (error) {
    console.error('Error al actualizar empleado', error)
    $q.notify({
      type: 'negative',
      message: 'Error al actualizar empleado'
    })
  }
}

// Volver sin guardar
function volver() {
  router.push('/empleado/lista')
}
</script>

<style>
.container {
  margin: auto;
}
</style>
