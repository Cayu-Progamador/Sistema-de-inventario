<template>
  <div class="q-pa-md container">
    <div class="text-h5 q-pa-md text-center ">Agregar nuevo cliente</div>

    <q-form @submit.prevent="onSubmit" @reset="onReset">
      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        clear-icon="close"
        filled
        color="purple-12"
        clearable
        v-model="cliente.nombre"
        label="Nombre"
        :rules="[val => !!val || 'El nombre es obligatorio']"
      />

      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        clear-icon="close"
        filled
        color="purple-12"
        clearable
        v-model="cliente.ci"
        label="CI"
        :rules="[val => !!val || 'El CI es obligatorio']"
      />

      <q-input
        clear-icon="close"
        filled
        label-color="teal-10"
        bg-color="teal-3"
        color="purple-12"
        clearable
        v-model="cliente.correoElectronico"
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
        color="purple-12"
        clearable
        label-color="teal-10"
        bg-color="teal-3"
        v-model="cliente.direccion"
        label="Dirección"
        :rules="[val => !!val || 'La dirección es obligatoria']"
      />

      <q-input
        clear-icon="close"
        label-color="teal-10"
        bg-color="teal-3"
        filled
        color="purple-12"
        clearable
        v-model="cliente.telefono"
        label="Teléfono"
        :rules="[val => !!val || 'El teléfono es obligatorio']"
      />

      <div class="q-mt-md flex justify-center">
        <q-btn
          label="Guardar cliente"
          type="submit"
          icon="save"
          class="q-mx-sm bg-teal-10 text-white"
        />

        <q-btn
          text-color="white"
          label="Limpiar"
          type="reset"
          color="grey-7"
          icon="close"
          class="q-mx-sm"
        />
      </div>
    </q-form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ClienteService from '../../service/ClienteService'
import { useQuasar } from 'quasar'

const $q = useQuasar()

const cliente = ref({
  nombre: '',
  ci: '',
  correoElectronico: '',
  direccion: '',
  telefono: ''
})

async function onSubmit() {
  try {
    $q.dialog({
      dark: true,
      title: '¿Confirmar registro?',
      message: '¿Deseas agregar este cliente?',
      cancel: true,
      persistent: true
    }).onOk(async () => {
      const response = await ClienteService.addClient(cliente.value)
      console.log('Cliente agregado', response.data)

      $q.notify({
        type: 'positive',
        message: 'Cliente guardado con éxito',
        position: 'bottom'
      })

      onReset()
    })
  } catch (error) {
    console.error('Error al guardar el cliente', error)

    $q.notify({
      type: 'negative',
      message: 'Error al guardar el cliente',
      position: 'bottom'
    })
  }
}

function onReset() {
  cliente.value = {
    nombre: '',
    ci: '',
    correoElectronico: '',
    direccion: '',
    telefono: ''
  }
}
</script>

<style>
.container {
  max-width: 800px;
  margin: auto;
}


</style>
