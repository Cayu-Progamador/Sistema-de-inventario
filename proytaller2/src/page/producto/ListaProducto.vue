<template>
  <div class="q-pa-md">
    <div class="container">
      <!-- Buscador de productos -->
      <q-input bottom-slots v-model="terminoBusqueda" label="Buscar producto" :dense="dense" label-color="teal-10"
        bg-color="teal-3">
        <template v-slot:append>
          <q-icon v-if="terminoBusqueda !== ''" name="close" @click="limpiarBusqueda" class="cursor-pointer"
            bg-color="teal-10" />
          <q-icon name="search" @click="buscarProducto" class="cursor-pointer" bg-color="teal-10" />
        </template>
      </q-input>
    </div>

    <!-- Tabla de productos -->
    <q-table flat title="Lista de productos" bordered class="bg-teal-8 text-white" :rows="productos" :columns="columns"
      row-key="idProducto" loading-label="Cargando productos..." no-data-label="No hay productos registrados">
      <!-- Botón Añadir producto cuando no hay datos -->
      <template v-slot:no-data>
        <div class="q-pa-md text-center">
          <div class="text-center q-mb-sm text-subtitle2" style="color: red;">
            No hay productos registrados
          </div>
          <q-btn color="primary" icon="add_box" label="Añadir Producto" @click="anadirProducto" />
        </div>
      </template>

      <!-- Botones Editar y Eliminar -->
      <template v-slot:body-cell-acciones="props">
        <q-td align="center">
          <q-btn color="primary" icon="edit" size="sm" label="Editar" square class="bg-teal-10 text-white"
            @click.stop="editarProducto(props.row)" />
          <q-btn color="negative" icon="delete" size="sm" label="Eliminar" square class="q-ml-md q-px-md"
            @click.stop="eliminarProducto(props.row)" />

          <!-- Alerta de stock bajo -->
          <template v-slot:body-cell-stockActual="props">
            <q-td :props="props">
              <div :class="{
                'bg-red-10 text-white q-pa-xs rounded': props.row.stockActual <= 5,
                'text-white': true
              }">
                {{ props.row.stockActual }}
              </div>
            </q-td>
          </template>

          <!-- Alerta por fecha vencida o próxima -->
          <template v-slot:body-cell-fechaVencimiento="props">
            <q-td :props="props">
              <div :class="getClassFecha(props.row.fechaVencimiento)" class="q-pa-xs rounded">
                {{ props.row.fechaVencimiento }}
              </div>
            </q-td>
          </template>


        </q-td>
      </template>


    </q-table>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useQuasar } from 'quasar'
import ProductoService from '../../service/ProductoService'

const $q = useQuasar()
const router = useRouter()
const productos = ref([])
const terminoBusqueda = ref('')
const dense = ref(true)

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
  { name: 'ubicacion', label: 'Ubicación', field: 'ubicacion', align: 'left' },
  { name: 'acciones', label: 'Acciones', align: 'center' }
]


//
function getClassFecha(fecha) {
  if (!fecha) return ''

  const hoy = new Date()
  const vencimiento = new Date(fecha)

  const diffDias = (vencimiento - hoy) / (1000 * 60 * 60 * 24)

  // Si está vencido
  if (diffDias < 0) {
    return 'bg-red-10 text-white'
  }

  // Si faltan 10 días o menos
  if (diffDias <= 10) {
    return 'bg-orange-9 text-white'
  }

  return 'text-white'
}



// Cargar productos al montar
onMounted(async () => {
  await cargarProductos()
})

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

function anadirProducto() {
  router.push('/producto/nuevo')
}

function editarProducto(row) {
  if (!row.idProducto) {
    $q.notify({ type: 'warning', message: 'No se pudo obtener el id del producto' })
    return
  }
  router.push(`/producto/editar/${row.idProducto}`)
}

async function eliminarProducto(row) {
  try {
    $q.dialog({
      title: 'Confirmar Eliminación',
      message: '¿Está seguro de eliminar este producto?',
      cancel: true,
      persistent: true
    }).onOk(async () => {
      if (!row.idProducto) {
        $q.notify({ type: 'warning', message: 'No se pudo obtener el id del producto' })
        return
      }
      await ProductoService.eliminarProducto(row.idProducto)
      $q.notify({ type: 'positive', message: 'Producto eliminado exitosamente', icon: 'check_circle' })
      cargarProductos()
    })
  } catch (error) {
    $q.notify({ type: 'negative', message: 'Error al eliminar producto' })
  }
}

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
