<template>
  <div class="q-pa-md container">
    <div class="text-h5 text-center q-mb-md">Editar proveedor</div>

    <q-form @submit.prevent="actualizarProveedor" class="q-gutter-md">
            
        <q-input
        bg-color="teal-3"
      label-color="teal-10"
            clear-icon="close"
            filled
            color="purple-12"
            clearable
            v-model="proveedor.nombre"
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
            v-model="proveedor.apellido"
            label="Apellidos"
            :rules="[val => !!val || 'El apellido es obligatorio']"
        />

        <q-input
        bg-color="teal-3"
      label-color="teal-10"
            clear-icon="close"
            filled
            color="purple-12"
            clearable
            v-model="proveedor.direccion"
            label="Dirección"
            :rules="[val => !!val || 'La dirección es obligatoria']"
        />

        <q-input
        bg-color="teal-3"
      label-color="teal-10"
            clear-icon="close"
            filled
            color="purple-12"
            clearable
            v-model="proveedor.correoElectronico"
            type="email"
            label="Correo electrónico"
            :rules="[
            val => !!val || 'El correo electrónico es obligatorio',
            val => /.+@.+\..+/.test(val) || 'Correo no válido'
            ]"
        />

        <q-input
        bg-color="teal-3"
      label-color="teal-10"
            clear-icon="close"
            filled
            color="purple-12"
            clearable
            v-model="proveedor.nit"
            label="NIT"
            :rules="[val => !!val || 'El NIT es obligatorio']"
        />

        <q-input
        bg-color="teal-3"
      label-color="teal-10"
            clear-icon="close"
            filled
            color="purple-12"
            clearable
            v-model="proveedor.telefono"
            label="Telefono"
            :rules="[val => !!val || 'El telefono es obligatorio']"
        />

        <q-input
            clear-icon="close"
            filled
            color="purple-12"
            clearable
            bg-color="teal-3"
      label-color="teal-10"
            v-model="proveedor.estado"
            label="Estado"
            :rules="[val => !!val || 'El estado es obligatorio']"
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
import ProveedorService from '../../service/ProveedorService'

const route = useRoute()
const router = useRouter()
const $q = useQuasar()

const proveedor = ref({
    nit: '',
    nombre: '',
    apellido: '',
    direccion: '',
    telefono: '',
    correoElectronico: '',
    estado: ''
})

// Cargar datos del cliente al montar
onMounted(async () => {
  const id = route.params.idProveedor
  try {
    const response = await ProveedorService.obtenerPorId(id)
    proveedor.value = response.data
  } catch (error) {
    console.error('Error al cargar proveedor', error)
    $q.notify({ type: 'negative', message: 'Error al cargar proveedor' })
  }
})

// Guardar cambios
async function actualizarProveedor() {
  const id = route.params.idProveedor
  try {
    $q.dialog({
        dark: true,
        title: '¿Confirmar cambios?',
        message: '¿Deseas guardar los cambios?',
        cancel: true,
        persistent: true
    }).onOk(async () => {
        await ProveedorService.actualizarProveedor(id, proveedor.value)
        $q.notify({
            type: 'positive',
            message: 'Proveedor actualizado con éxito'
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
