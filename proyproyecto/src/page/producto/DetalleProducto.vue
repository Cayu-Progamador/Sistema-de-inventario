<template>
  <div class="q-pa-md">
    <div class="container">
      <!-- Buscador de productos -->
      <q-input
        bottom-slots
        v-model="terminoBusqueda"
        label="Buscar producto"
        :dense="dense"
        label-color="teal-10"
        bg-color="teal-3"
      >
        <template v-slot:append>
          <q-icon
            v-if="terminoBusqueda !== ''"
            name="close"
            @click="limpiarBusqueda"
            class="cursor-pointer"
          />
          <q-icon
            name="search"
            @click="buscarProducto"
            class="cursor-pointer"
          />
        </template>
      </q-input>
    </div>

    <!-- Tabla de productos -->
    <q-table
      flat
      class="bg-teal-10 text-white"
      title="Lista de productos"
      bordered
      :rows="productos"
      :columns="columns"
      row-key="idProducto"
      loading-label="Cargando productos..."
      no-data-label="No hay productos registrados"
    >
      <!-- CELDA DE FECHA PERSONALIZADA -->
      <template v-slot:body-cell-fechaVencimiento="props">
        <q-td :props="props" :class="getClassFecha(props.row.fechaVencimiento)">
          {{ formatFecha(props.row.fechaVencimiento) }}
        </q-td>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useQuasar } from 'quasar'
import ProductoService from '../../service/ProductoService'

// Estado
const productos = ref([])
const terminoBusqueda = ref('')
const dense = ref(true)
const $q = useQuasar()

// Columnas
const columns = [
  { name: 'nombreProducto', label: 'Nombre', field: 'nombreProducto', align: 'left' },
  { name: 'tipoProducto', label: 'Tipo', field: 'tipoProducto', align: 'left' },
  { name: 'unidadMedida', label: 'Unidad', field: 'unidadMedida', align: 'left' },
  { name: 'precio', label: 'Precio', field: 'precio', align: 'left' },
  { name: 'codigoBarra', label: 'Código de barra', field: 'codigoBarra', align: 'left' },
  { name: 'fechaVencimiento', label: 'Vencimiento', field: 'fechaVencimiento', align: 'left' },
  { name: 'stockActual', label: 'Stock Actual', field: 'stockActual', align: 'left' },
  { name: 'stockMinimo', label: 'Stock Mínimo', field: 'stockMinimo', align: 'left' },
  { name: 'stockMaximo', label: 'Stock Máximo', field: 'stockMaximo', align: 'left' },
  { name: 'ubicacion', label: 'Ubicación', field: 'ubicacion', align: 'left' }
]

// Cargar productos al montar
onMounted(async () => {
  await cargarProductos()
})

// ⬇⬇⬇ FUNCIONES IMPORTANTES ⬇⬇⬇

// ⭐ Formato de fecha
function formatFecha(fecha) {
  if (!fecha) return '—'
  return new Date(fecha).toLocaleDateString('es-BO')
}

// ⭐ Pintar celda según vencimiento
function getClassFecha(fecha) {
  if (!fecha) return ''

  const hoy = new Date()
  const fechaVenc = new Date(fecha)
  const diff = (fechaVenc - hoy) / (1000 * 60 * 60 * 24)

  if (diff < 0) return 'bg-red-8 text-white'          // 🔴 VENCIDO
  if (diff <= 5) return 'bg-orange-8 text-black'      // 🟠 Pronto a vencer

  return ''                                            // Normal
}

async function cargarProductos() {
  try {
    const response = await ProductoService.listarTodosProductos()
    productos.value = response.data
  } catch (error) {
    console.error('Error al obtener productos', error)
    $q.notify({ type: 'negative', message: 'Error al cargar productos' })
  }
}

async function buscarProducto() {
  try {
    if (!terminoBusqueda.value.trim()) {
      await cargarProductos()
      return
    }

    const response = await ProductoService.buscarPorNombre(terminoBusqueda.value)
    productos.value = response.data
  } catch (error) {
    console.error('Error al buscar producto', error)
    $q.notify({ type: 'negative', message: 'Error al buscar producto' })
  }
}

function limpiarBusqueda() {
  terminoBusqueda.value = ''
  buscarProducto()
}

// Actualiza cuando se escribe
watch(terminoBusqueda, async (nuevoValor) => {
  if (!nuevoValor.trim()) {
    await cargarProductos()
  } else {
    await buscarProducto()
  }
})
</script>

<style>
.container {
  max-width: 70%;
  margin: auto;
  padding: 7px;
}
</style>
