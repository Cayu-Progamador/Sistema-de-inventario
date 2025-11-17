<template>
  <q-page padding>
    <q-form @submit.prevent="confirmarVenta" class="q-gutter-md">

      <div class="row q-col-gutter-md">

        <div class="col-12 col-md-5">
          <q-card flat bordered>
            <q-card-section>
              <div class="text-h6 text-center">Datos de la Venta</div>
            </q-card-section>
            
            <q-card-section class="q-pt-none q-gutter-md">
              
              <q-select
               color="teal-10"
                outlined
                v-model="venta.idMetodoPago"
                :options="opcionesMetodoPago"
                label="Método de Pago"
                emit-value
                label-color="teal-10"
                bg-color="teal-1"
                map-options
                :rules="[val => !!val || 'Seleccione un método']"
              />
              <q-select
                label="Seleccionar empleado"
                outlined
                dense
                v-model="venta.idEmpleado"
                :options="empleados"
                emit-value
                map-options
                label-color="teal-10"
                bg-color="teal-1"
                :rules="[val => !!val || 'Seleccione un empleado']"
              />

              
              
              <q-select
                label-color="teal-10"
                bg-color="teal-1"
                outlined
                v-model="venta.tipoDocumento"
                :options="opcionesDocumento"
                label="Tipo de Documento"
                :rules="[val => !!val || 'Seleccione un documento']"
              />
              
              <q-input
                label-color="teal-10"
                bg-color="teal-1"
                outlined
                v-model.number="venta.totalDescuento"
                type="number"
                label="Descuento Total (Bs.)"
                prefix="Bs."
                step="0.10"
                :rules="[val => val >= 0 || 'El descuento no puede ser negativo']"
              />
            </q-card-section>
          </q-card>
        </div>

        <div class="col-12 col-md-7">
          <q-card flat bordered>
            <q-card-section>
              <div class="text-h6">Detalle de Productos</div>
            </q-card-section>
            
            <q-card-section class="q-gutter-md">
              
              <div class="row items-center q-gutter-sm">
                <q-input
                  class="col"
                  label-color="teal-10"
                  bg-color="teal-1"
                  outlined
                  dense
                  v-model="codigoBusqueda"
                  label="Escanear Código de Barras..."
                  @keyup.enter="buscarPorCodigo"
                  :loading="buscandoPorCodigo"
                >
                  <template v-slot:prepend>
                    <q-icon name="qr_code_scanner" />
                  </template>
                </q-input>
                
                <q-input
                  class="col-2"
                  label-color="teal-10"
                  bg-color="teal-1"
                  outlined
                  dense
                  v-model.number="cantidadCodigo"
                  type="number"
                  label="Cant."
                  :min="1"
                  @keyup.enter="buscarPorCodigo"
                />
                
                <q-btn
                  class="col-auto bg-teal-10 text-white"
                  padding="sm"
                  color="primary"
                  icon="add_shopping_cart"
                  @click="buscarPorCodigo"
                  unelevated
                  title="Añadir por código"
                />
              </div>

              <div class="row items-center q-gutter-sm">
                <q-select
                 label-color="teal-10"
                 bg-color="teal-1"
                  class="col"
                  outlined
                  dense
                  v-model="productoNombreSeleccionado"
                  label="Buscar por Nombre..."
                  use-input
                  fill-input
                  hide-selected
                  input-debounce="300"
                  :options="opcionesBusquedaNombre"
                  @filter="filtrarProductosPorNombre"
                  :loading="buscandoPorNombre"
                >
                  <template v-slot:no-option>
                    <q-item>
                      <q-item-section class="text-grey">
                        Escribe 2 o más letras para buscar...
                      </q-item-section>
                    </q-item>
                  </template>
                </q-select>
                
                <q-input
                  class="col-2"
                  outlined
                  label-color="teal-10"
                  bg-color="teal-1"
                  dense
                  v-model.number="cantidadNombre"
                  type="number"
                  label="Cant."
                  :min="1"
                />
                
                <q-btn
                  class_name="col-auto "
                  padding="sm"
                  class="bg-teal-10 text-white"
                  color="primary"
                  icon="add"
                  @click="anadirProductoPorNombre"
                  unelevated
                  title="Añadir por nombre"
                />
              </div>
            </q-card-section>

            <q-separator />

            <q-table
              flat
              :rows="venta.detalles"
              :columns="columnasDetalle"
              row-key="idProducto"
              hide-bottom
              class="bg-teal-10 text-white"
              no-data-label="Aún no hay productos en la venta."
            >
              <template v-slot:body-cell-accion="props">
                <q-td :props="props">
                  <q-btn
                    flat
                    round
                    color="red-5"
                    icon="delete"
                    @click="quitarProducto(props.row.idProducto)"
                  />
                </q-td>
              </template>
              
              <template v-slot:body-cell-subtotal="props">
                <q-td :props="props" class="text-right">
                  {{ props.row.subtotal.toFixed(2) }} Bs.
                </q-td>
              </template>
              
              <template v-slot:body-cell-precioUnitario="props">
                <q-td :props="props" class="text-right">
                  {{ props.row.precioUnitario.toFixed(2) }} Bs.
                </q-td>
              </template>

            </q-table>
            
            <q-separator />
            <q-card-section class="column items-end">
              <div class="text-subtitle1">
                Subtotal: <span class="text-weight-medium">{{ totalBruto.toFixed(2) }} Bs.</span>
              </div>
              <div class="text-subtitle1 text-red">
                Descuento: <span class="text-weight-medium">- {{ Number(venta.totalDescuento || 0).toFixed(2) }} Bs.</span>
              </div>
              <div class="text-h5 text-weight-bold text-primary q-mt-sm">
                TOTAL A PAGAR: {{ totalNeto.toFixed(2) }} Bs.
              </div>
            </q-card-section>
            
          </q-card>
        </div>

      </div>

      <div class="row justify-end q-mt-lg">
        <q-btn
          label="Registrar Venta"
          type="submit"
          color="teal-10"
          icon-right="shopping_cart"
          size="lg"
          :loading="enviandoVenta"
          :disable="venta.detalles.length === 0"
        />
      </div>

    </q-form>
  </q-page>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useQuasar } from 'quasar';
import apiVenta from '../../service/VentaService'; 
import productoService from '../../service/ProductoService'; 
import EmpleadoService from '../../service/EmpleadoService'

const $q = useQuasar();
const empleados = ref([])

// === ESTADO PRINCIPAL DE LA VENTA ===
const venta = ref({
  idEmpleado: null, 
  idMetodoPago: null,
  tipoDocumento: 'FACTURA',
  totalDescuento: 0.0,
  detalles: [] 
});
const enviandoVenta = ref(false);

// === DATOS PARA SELECTORES ===
const opcionesMetodoPago = ref([
  { label: 'Efectivo', value: 1 },
  { label: 'Tarjeta de Crédito', value: 2 },
  { label: 'Transferencia QR', value: 3 }
]);
const opcionesDocumento = ref(['FACTURA', 'RECIBO']);

// === LÓGICA DE BÚSQUEDA POR CÓDIGO ===
const codigoBusqueda = ref('');
const cantidadCodigo = ref(1);
const buscandoPorCodigo = ref(false);
// Cargar al montar el componente
onMounted(() => {
  cargarEmpleados();
  // También tus productos o ventas
});

async function cargarEmpleados (){
  try{
    const response = await EmpleadoService.listarEmpleados()
    empleados.value = response.data.map(emp => ({
      label: `${emp.nombre} ${emp.apellido}`,
      value: emp.idEmpleado
    }))
  }catch(error){
    $q.notify({ color: 'negative', message: 'No se pudieron cargar los empleados' });
  }
}
async function buscarPorCodigo() {
  if (!codigoBusqueda.value || codigoBusqueda.value.trim() === '') return;
  buscandoPorCodigo.value = true;
  try {
    const response = await productoService.buscarPorTermino(codigoBusqueda.value);
    const productoEncontrado = response.data; 
    anadirProductoAlCarrito(productoEncontrado, cantidadCodigo.value);
    codigoBusqueda.value = '';
    cantidadCodigo.value = 1;
  } catch (error) {
    if (error.response && error.response.status === 404) {
      $q.notify({
        color: 'negative',
        icon: 'warning',
        message: `Producto no encontrado: "${codigoBusqueda.value}"`
      });
    } else {
      console.error('Error al buscar producto:', error);
      $q.notify({ color: 'negative', message: 'Error de red al buscar el producto' });
    }
  } finally {
    buscandoPorCodigo.value = false;
  }
}

// === LÓGICA DE BÚSQUEDA POR NOMBRE ===
const productoNombreSeleccionado = ref(null);
const cantidadNombre = ref(1);
const buscandoPorNombre = ref(false);
const opcionesBusquedaNombre = ref([]);

async function filtrarProductosPorNombre(val, update) {
  if (val.length < 2) {
    update(() => { opcionesBusquedaNombre.value = []; });
    return;
  }
  buscandoPorNombre.value = true;
  try {
    const response = await productoService.buscarPorNombre(val);
    update(() => {
      opcionesBusquedaNombre.value = response.data.map(producto => ({
        // ❗ CAMBIO: Usamos 'nombreProducto' y 'precio' (de tu API)
        label: `${producto.nombreProducto} (Stock: ${producto.stockActual || 0}) - Bs. ${(producto.precio || 0).toFixed(2)}`,
        value: producto 
      }));
    });
  } catch (error) {
    console.error('Error al filtrar productos:', error);
    $q.notify({ color: 'negative', message: 'Error al buscar productos por nombre' });
  } finally {
    buscandoPorNombre.value = false;
  }
}

function anadirProductoPorNombre() {
  if (!productoNombreSeleccionado.value) {
    $q.notify({ color: 'warning', message: 'Debes seleccionar un producto de la lista' });
    return;
  }
  const producto = productoNombreSeleccionado.value.value; 
  anadirProductoAlCarrito(producto, cantidadNombre.value);
  productoNombreSeleccionado.value = null;
  cantidadNombre.value = 1;
  opcionesBusquedaNombre.value = [];
}

// === LÓGICA DEL CARRITO (TABLA) ===
const columnasDetalle = [
  { name: 'nombreProducto', label: 'Producto', field: 'nombreProducto', align: 'left', sortable: true },
  { name: 'cantidad', label: 'Cantidad', field: 'cantidad', align: 'center' },
  { name: 'precioUnitario', label: 'P. Unit.', field: 'precioUnitario', align: 'right' },
  { name: 'subtotal', label: 'Subtotal', field: 'subtotal', align: 'right' },
  { name: 'accion', label: 'Quitar', field: 'accion', align: 'center' }
];

// ❗❗ === ESTA ES LA FUNCIÓN CORREGIDA === ❗❗
function anadirProductoAlCarrito(producto, cantidad) {
  if (cantidad <= 0) return;
  
  // ❗ CAMBIO: Verificamos 'producto.precio' (de tu API)
  if (!producto || typeof producto.precio === 'undefined') {
    console.error("Intento de añadir producto inválido:", producto);
    $q.notify({ color: 'negative', message: 'Error al procesar el producto.' });
    return;
  }

  // ❗ CAMBIO: Verificamos 'producto.precio'
  if (!producto.precio) {
    $q.notify({
      // ❗ CAMBIO: Usamos 'producto.nombreProducto'
      color: 'negative',
      message: `El producto '${producto.nombreProducto}' no tiene un precio de venta.`
    });
    return;
  }

  // ❗ CAMBIO: Buscamos por 'producto.idProducto'
  const productoExistente = venta.value.detalles.find(
    p => p.idProducto === producto.idProducto
  );

  if (productoExistente) {
    productoExistente.cantidad += cantidad;
    productoExistente.subtotal = productoExistente.cantidad * productoExistente.precioUnitario;
    // ❗ CAMBIO: Usamos 'producto.nombreProducto'
    $q.notify({ color: 'info', message: `Cantidad actualizada: ${producto.nombreProducto}` });
  } else {
    // Aquí creamos el objeto para el carrito (q-table)
    // Los nombres que usamos aquí (precioUnitario) deben coincidir con 'columnasDetalle'
    venta.value.detalles.push({
      // ❗ CAMBIO: Usamos los nombres de tu API
      idProducto: producto.idProducto,
      nombreProducto: producto.nombreProducto,
      cantidad: cantidad,
      precioUnitario: producto.precio, // El precio de la API se vuelve 'precioUnitario' en el carrito
      subtotal: cantidad * producto.precio
    });
    // ❗ CAMBIO: Usamos 'producto.nombreProducto'
    $q.notify({ color: 'positive', icon: 'add', message: `Añadido: ${producto.nombreProducto}` });
  }
}
// ❗❗ === FIN DE LA FUNCIÓN CORREGIDA === ❗❗

function quitarProducto(idProducto) {
  venta.value.detalles = venta.value.detalles.filter(
    p => p.idProducto !== idProducto
  );
  $q.notify({ color: 'warning', message: 'Producto quitado del carrito' });
}

// === CÁLCULO DE TOTALES (COMPUTED) ===
const totalBruto = computed(() => {
  return venta.value.detalles.reduce((total, item) => total + item.subtotal, 0);
});

const totalNeto = computed(() => {
  const descuento = Number(venta.value.totalDescuento) || 0;
  return totalBruto.value - descuento;
});

// === LÓGICA DE ENVÍO (SUBMIT) ===
function confirmarVenta() {
  if (venta.value.detalles.length === 0) {
    $q.notify({ color: 'negative', message: 'No hay productos en la venta' });
    return;
  }
  if (totalNeto.value < 0) {
    $q.notify({ color: 'negative', message: 'El descuento no puede ser mayor al total' });
    return;
  }
  
  $q.dialog({
    title: 'Confirmar Venta',
    message: `El total a pagar es: ${totalNeto.value.toFixed(2)} Bs. ¿Deseas continuar?`,
    cancel: true,
    persistent: true,
    ok: { label: 'Confirmar', color: 'positive' },
    cancel: { label: 'Cancelar', color: 'grey' }
  }).onOk(() => {
    registrarVenta();
  });
}

async function registrarVenta() {
  enviandoVenta.value = true;
  
  // Esta parte ya estaba correcta, porque tu Postman usa 'idProducto'
  const payload = {
    idEmpleado: venta.value.idEmpleado,
    idMetodoPago: venta.value.idMetodoPago,
    tipoDocumento: venta.value.tipoDocumento,
    totalDescuento: Number(venta.value.totalDescuento) || 0,
    detalles: venta.value.detalles.map(item => ({
      idProducto: item.idProducto,
      cantidad: item.cantidad
    }))
  };

  try {
    const response = await apiVenta.registrar(payload)
    console.log(response)
    $q.notify({
      color: 'positive',
      icon: 'check_circle',
      message: `Venta registrada con éxito (ID: ${response.data.id})`,
    });
    resetearFormulario();
  } catch (error) {
    console.error('Error al registrar la venta:', error);
    $q.notify({
      color: 'negative',
      icon: 'error',
      message: `Error: ${error.response?.data?.message || 'No se pudo registrar la venta'}`
    });
  } finally {
    enviandoVenta.value = false;
  }
}

function resetearFormulario() {
  venta.value = {
    idEmpleado: 1,
    idMetodoPago: null,
    tipoDocumento: 'FACTURA',
    totalDescuento: 0.0,
    detalles: []
  };
  codigoBusqueda.value = '';
  cantidadCodigo.value = 1;
  productoNombreSeleccionado.value = null;
  cantidadNombre.value = 1;
  opcionesBusquedaNombre.value = [];
}
</script>