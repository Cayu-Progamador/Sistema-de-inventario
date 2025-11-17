<template>
  <div class="q-pa-md container">
    <div class="text-h5 text-center q-mb-md">Editar Cliente</div>

    <q-form @submit.prevent="actualizarCliente" class="q-gutter-md">
      <q-input
        filled
        v-model="cliente.nombre"
        label="Nombre"
        color="purple-12"
        label-color="teal-10"
        bg-color="teal-3"
        :rules="[val => !!val || 'El nombre es obligatorio']"
      />
      <q-input
        filled
        v-model="cliente.ci"
        label="CI"
        color="purple-12"
        label-color="teal-10"
        bg-color="teal-3"
        :rules="[val => !!val || 'El CI es obligatorio']"
      />
      <q-input
        filled
        label-color="teal-10"
        bg-color="teal-3"
        v-model="cliente.correoElectronico"
        label="Correo electrónico"
        type="email"
        color="purple-12"
        :rules="[val => /.+@.+\..+/.test(val) || 'Correo no válido']"
      />
      <q-input
        filled
        v-model="cliente.direccion"
        label="Dirección"
        color="purple-12"
        label-color="teal-10"
        bg-color="teal-3"
        :rules="[val => !!val || 'La dirección es obligatoria']"
      />
      <q-input
        filled
        label-color="teal-10"
        bg-color="teal-3"
        v-model="cliente.telefono"
        label="Teléfono"
        color="purple-12"
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
import ClienteService from '../../service/ClienteService'

const route = useRoute()
const router = useRouter()
const $q = useQuasar()

const cliente = ref({
  nombre: '',
  ci: '',
  correoElectronico: '',
  direccion: '',
  telefono: ''
})

// Cargar datos del cliente al montar
onMounted(async () => {
  const id = route.params.idCliente
  try {
    const response = await ClienteService.obtenerClientePorId(id)
    cliente.value = response.data
  } catch (error) {
    console.error('Error al cargar cliente', error)
    $q.notify({ type: 'negative', message: 'Error al cargar cliente' })
  }
})

// Guardar cambios
async function actualizarCliente() {
  const id = route.params.idCliente
  try {
    $q.dialog({
        dark: true,
        title: '¿Confirmar cambios?',
        message: '¿Deseas guardar los cambios?',
        cancel: true,
        persistent: true
    }).onOk(async () => {
        await ClienteService.updateClient(id, cliente.value)
        $q.notify({
            type: 'positive',
            message: 'Cliente actualizado con éxito'
        })
    }).onCancel(() => {
        console.log('Cancelar')
    })

    
    router.push('/cliente/lista')
  } catch (error) {
    console.error('Error al actualizar cliente', error)
    $q.notify({
      type: 'negative',
      message: 'Error al actualizar cliente'
    })
  }
}

// Volver sin guardar
function volver() {
  router.push('/cliente/lista')
}
</script>

<style>
.container {
  margin: auto;
}
</style>
