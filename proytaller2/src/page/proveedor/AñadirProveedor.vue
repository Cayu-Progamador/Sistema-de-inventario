<template>
  <div class="q-pa-md container">
    <div class="text-h5 q-pa-md text-center ">Agregar nuevo proveedor</div>

    <q-form @submit.prevent="onSubmit" @reset="onReset">
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
        clear-icon="close"
        bg-color="teal-3"
      label-color="teal-10"
        filled
        color="purple-12"
        clearable
        v-model="proveedor.apellido"
        label="Apellidos"
        :rules="[val => !!val || 'El apellido es obligatorio']"
      />

      <q-input
        clear-icon="close"
        filled
        color="purple-12"
        bg-color="teal-3"
      label-color="teal-10"
        clearable
        v-model="proveedor.direccion"
        label="Dirección"
        :rules="[val => !!val || 'La dirección es obligatoria']"
      />

      <q-input
        clear-icon="close"
        filled
        color="purple-12"
        bg-color="teal-3"
      label-color="teal-10"
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
        clear-icon="close"
        filled
        bg-color="teal-3"
      label-color="teal-10"
        color="purple-12"
        clearable
        v-model="proveedor.nit"
        label="NIT"
        :rules="[val => !!val || 'El NIT es obligatorio']"
      />

      <q-input
        clear-icon="close"
        filled
        color="purple-12"
        bg-color="teal-3"
      label-color="teal-10"
        clearable
        v-model="proveedor.telefono"
        label="Telefono"
        :rules="[val => !!val || 'El telefono es obligatorio']"
      />

      <q-input
        clear-icon="close"
        filled
        bg-color="teal-3"
      label-color="teal-10"
        color="purple-12"
        clearable
        v-model="proveedor.estado"
        label="Estado"
        :rules="[val => !!val || 'El estado es obligatorio']"
      />

      <div class="q-mt-md flex justify-center">
        <q-btn
          label="Guardar Proveedor"
          type="submit"

          icon="save"
          class="q-mx-sm bg-teal-10 text-white"
        />

        <q-btn
          label="Limpiar"
          type="reset"
          icon="close"
          class="q-mx-sm bg-teal-10 text-white"
        />
      </div>
    </q-form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ProvedorService from '../../service/ProveedorService'
import { useQuasar } from 'quasar'

const $q = useQuasar()

const proveedor = ref({
    nombre: '',
    apellido: '',
    direccion: '',
    telefono: '',
    nit: '',
    correoElectronico: '',
    estado: 'Activo'
})

async function onSubmit() {
  try {
    $q.dialog({
      dark: true,
      title: '¿Confirmar registro?',
      message: '¿Deseas agregar este proveedor?',
      cancel: true,
      persistent: true
    }).onOk(async () => {
      const response = await ProvedorService.crearProveedor(proveedor.value)
      console.log('proveedor agregado', response.data)

      $q.notify({
        type: 'positive',
        message: 'Proveedor guardado con éxito',
        position: 'bottom'
      })

      onReset()
    })
  } catch (error) {
    console.error('Error al guardar al proveedor', error)

    $q.notify({
      type: 'negative',
      message: 'Error al guardar al Proveedor',
      position: 'bottom'
    })
  }
}

function onReset() {
  proveedor.value = {
    nit: '',
    nombre: '',
    apellido: '',
    direccion: '',
    telefono: '',
    correoElectronico: '',
    estado: ''
  }
}
</script>

<style>
.container {
  max-width: 800px;
  margin: auto;
}


</style>
