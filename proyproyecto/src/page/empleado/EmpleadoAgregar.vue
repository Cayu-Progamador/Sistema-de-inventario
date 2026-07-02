<template>
  <div class="q-pa-md container">
    <div class="text-h5 q-pa-md text-center ">Agregar nuevo empleado</div>

    <q-form @submit.prevent="onSubmit" @reset="onReset">
      <q-input
        bg-color="teal-3"
        label-color="teal-10"
        clear-icon="close"
        filled
        color="purple-12"
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
        bg-color="teal-3"
        label-color="teal-10"
        clear-icon="close"
        filled
        color="purple-12"
        clearable
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
        clear-icon="close"
        filled
        color="purple-12"
        clearable
        bg-color="teal-3"
        label-color="teal-10"
        v-model="empleado.telefono"
        label="Teléfono"
        :rules="[val => !!val || 'El teléfono es obligatorio']"
      />

      <div class="q-mt-md flex justify-center">
        <q-btn
          label="Guardar Empleado"
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
import EmpleadoService from '../../service/EmpleadoService'
import { useQuasar } from 'quasar'

const $q = useQuasar()

const empleado = ref({
  nombre: '',
  ci: '',
  apellido: '',
  estado: 'activo',
  correo: '',
  direccion: '',
  telefono: ''
})

async function onSubmit() {
  try {
    $q.dialog({
      dark: true,
      title: '¿Confirmar registro?',
      message: '¿Deseas agregar este empleado?',
      cancel: true,
      persistent: true
    }).onOk(async () => {
      const response = await EmpleadoService.addEmpleado(empleado.value)
      console.log('empleado agregado', response.data)

      $q.notify({
        type: 'positive',
        message: 'Empleado guardado con éxito',
        position: 'bottom'
      })

      onReset()
    })
  } catch (error) {
    console.error('Error al guardar al empleado', error)

    $q.notify({
      type: 'negative',
      message: 'Error al guardar al empleado',
      position: 'bottom'
    })
  }
}

function onReset() {
  empleado.value = {
    nombre: '',
    ci: '',
    apellido: '',
    estado: '',
    correo: '',
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
