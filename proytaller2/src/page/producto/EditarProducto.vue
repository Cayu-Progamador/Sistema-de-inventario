<template>
  <div class="q-pa-md container">
    <div class="text-h5 text-center q-mb-md">Editar Producto</div>

    <q-form @submit.prevent="actualizarProducto" class="q-gutter-md">
      <!-- Campos del producto -->
      <q-input
        filled
        color="purple-12"
        label-color="teal-10"
        bg-color="teal-3"
        v-model="producto.nombreProducto"
        label="Nombre del producto"
        :rules="[val => !!val || 'El nombre es obligatorio']"
      />

      <q-input
        filled
        label-color="teal-10"
        bg-color="teal-3"
        color="purple-12"
        v-model="producto.tipoProducto"
        label="Tipo de producto"
        :rules="[val => !!val || 'El tipo es obligatorio']"
      />

      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        filled
        color="purple-12"
        v-model="producto.unidadMedida"
        label="Unidad de medida"
        :rules="[val => !!val || 'La unidad es obligatoria']"
      />

      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        filled
        color="purple-12"
        type="number"
        v-model.number="producto.precio"
        label="Precio"
        :rules="[val => val >= 0 || 'Debe ser mayor o igual a 0']"
      />

      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        filled
        color="purple-12"
        v-model="producto.codigoBarra"
        label="Código de barra"
      />

      <q-input
      label-color="teal-10"
        bg-color="teal-3"
        filled
        color="purple-12"
        type="date"
        v-model="producto.fechaVencimiento"
        label="Fecha de vencimiento"
      />

      <!-- Campos de inventario -->
      <q-input
        filled
        label-color="teal-10"
        bg-color="teal-3"
        color="purple-12"
        type="number"
        v-model.number="producto.stockActual"
        label="Stock actual"
      />

      <q-input
        filled
        color="purple-12"
        label-color="teal-10"
        bg-color="teal-3"
        type="number"
        v-model.number="producto.stockMinimo"
        label="Stock mínimo"
      />

      <q-input
        filled
        color="purple-12"
        type="number"
        label-color="teal-10"
        bg-color="teal-3"
        v-model.number="producto.stockMaximo"
        label="Stock máximo"
      />

      <q-input
        filled
        color="purple-12"
        label-color="teal-10"
        bg-color="teal-3"
        v-model="producto.ubicacion"
        label="Ubicación"
      />

      <!-- Botones -->
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
          class="q-ml-sm bg-grey-6 text-white"
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
import ProductoService from '../../service/ProductoService'

const route = useRoute()
const router = useRouter()
const $q = useQuasar()

const producto = ref({
  nombreProducto: '',
  tipoProducto: '',
  unidadMedida: '',
  precio: 0,
  codigoBarra: '',
  fechaVencimiento: '',
  stockActual: 0,
  stockMinimo: 0,
  stockMaximo: 0,
  ubicacion: ''
})

// Cargar datos del producto al montar
onMounted(async () => {
  const id = route.params.idProducto
  try {
    const response = await ProductoService.obtenerProductoPorId(id)
    producto.value = response.data
  } catch (error) {
    console.error('Error al cargar producto', error)
    $q.notify({ type: 'negative', message: 'Error al cargar producto' })
  }
})

// Guardar cambios
async function actualizarProducto() {
  const id = route.params.idProducto
  try {
    $q.dialog({
      dark: true,
      title: '¿Confirmar cambios?',
      message: '¿Deseas guardar los cambios de este producto?',
      cancel: true,
      persistent: true
    }).onOk(async () => {
      await ProductoService.editarProducto(id, producto.value)
      $q.notify({
        type: 'positive',
        message: 'Producto actualizado con éxito'
      })
      router.push('/producto/lista')
    })
  } catch (error) {
    console.error('Error al actualizar producto', error)
    $q.notify({
      type: 'negative',
      message: 'Error al actualizar producto'
    })
  }
}

// Volver sin guardar
function volver() {
  router.push('/producto/lista')
}
</script>

<style>
.container {
  max-width: 700px;
  margin: auto;
}
</style>
