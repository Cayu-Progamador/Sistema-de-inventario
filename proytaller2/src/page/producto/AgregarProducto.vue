<template>
  <div class="q-pa-md container">
    <div class="text-h5 q-pa-md text-center">Agregar nuevo producto</div>

    <q-form @submit.prevent="onSubmit" @reset="onReset">
      <!-- Nombre Producto -->
      <q-input
        filled
        label-color="teal-10"
        bg-color="teal-3"
        clearable
        v-model="producto.nombreProducto"
        label="Nombre del Producto"
        :rules="[val => !!val || 'El nombre es obligatorio']"
      />

      <!-- Tipo Producto -->
      <q-input
        label-color="teal-10"
        bg-color="teal-3"
        filled
        clearable
        v-model="producto.tipoProducto"
        label="Tipo de Producto"
        :rules="[val => !!val || 'El tipo es obligatorio']"
      />

      <!-- Código de Barra -->
      <q-input
        label-color="teal-10"
        bg-color="teal-3"
        filled
        clearable
        v-model="producto.codigoBarra"
        label="Código de Barra"
        :rules="[val => !!val || 'El código de barra es obligatorio']"
      />

      <!-- Unidad de Medida -->
      <q-input
       label-color="teal-10"
        bg-color="teal-3"
        filled
        clearable
        v-model="producto.unidadMedida"
        label="Unidad de Medida"
        :rules="[val => !!val || 'La unidad es obligatoria']"
      />

      <!-- Precio -->
      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        filled
        clearable
        type="number"
        v-model.number="producto.precio"
        label="Precio"
        :rules="[val => val > 0 || 'El precio debe ser mayor a 0']"
      />

      <!-- Fecha de Vencimiento -->
      <q-input
        filled
        label-color="teal-10"
        bg-color="teal-3"
        clearable
        type="date"
        v-model="producto.fechaVencimiento"
        label="Fecha de Vencimiento"
        :rules="[val => !!val || 'La fecha de vencimiento es obligatoria']"
      />

      <!-- Inventario -->
      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        filled
        clearable
        type="number"
        v-model.number="producto.stockActual"
        label="Stock Actual"
        :rules="[val => val >= 0 || 'Stock actual no puede ser negativo']"
      />

      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        filled
        clearable
        type="number"
        v-model.number="producto.stockMinimo"
        label="Stock Mínimo"
        :rules="[val => val >= 0 || 'Stock mínimo no puede ser negativo']"
      />

      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        filled
        clearable
        type="number"
        v-model.number="producto.stockMaximo"
        label="Stock Máximo"
        :rules="[val => val >= producto.stockMinimo || 'Stock máximo debe ser mayor o igual al mínimo']"
      />

      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        filled
        clearable
        v-model="producto.ubicacion"
        label="Ubicación"
      />

      <div class="q-mt-md flex justify-center">
        <q-btn
          label="Guardar Producto"
          type="submit"
          color="primary"
          icon="save"
          class="q-ml-sm q-pl-ms bg-teal-10 text-white"
        />
        <q-btn
          label="Limpiar"
          type="reset"
          color="grey-7"
          icon="close"
          class="q-ml-sm q-pl-ms bg-teal-10 text-white"
        />
      </div>
    </q-form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ProductoService from '../../service/ProductoService'
import { useQuasar } from 'quasar'

const $q = useQuasar()

// Objeto reactivo para el formulario
const producto = ref({
  nombreProducto: '',
  tipoProducto: '',
  codigoBarra: '',
  unidadMedida: '',
  precio: null,
  fechaVencimiento: '',
  stockActual: 0,
  stockMinimo: 0,
  stockMaximo: 0,
  ubicacion: ''
})

// Función para enviar el formulario
async function onSubmit() {
  try {
    $q.dialog({
      title: '¿Confirmar registro?',
      message: '¿Deseas agregar este producto?',
      cancel: true,
      persistent: true
    }).onOk(async () => {
      const response = await ProductoService.agregarProductos(producto.value)
      console.log('Producto agregado:', response.data)

      $q.notify({
        type: 'positive',
        message: 'Producto guardado con éxito',
        position: 'bottom'
      })

      onReset()
    })
  } catch (error) {
    console.error('Error al guardar el producto', error)
    $q.notify({
      type: 'negative',
      message: 'Error al guardar el producto',
      position: 'bottom'
    })
  }
}

// Función para limpiar el formulario
function onReset() {
  producto.value = {
    nombreProducto: '',
    tipoProducto: '',
    codigoBarra: '',
    unidadMedida: '',
    precio: null,
    fechaVencimiento: '',
    stockActual: 0,
    stockMinimo: 0,
    stockMaximo: 0,
    ubicacion: ''
  }
}
</script>

<style>
.container {
  max-width: 800px;
  margin: auto;
}
</style>
