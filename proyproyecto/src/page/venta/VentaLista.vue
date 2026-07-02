<template>
  <q-card style="height: 100vh;">
    <q-card-section>
      <div class="text-h6 text-center">Lista de Ventas</div>
    </q-card-section>

    <div class="q-pa-md row q-col-gutter-md">


      <!-- Búsqueda por fecha -->
      <div class="col-12 col-md-6 flex justify-center">
        <q-input 
          v-model="fechaBusqueda" 
          label="Buscar por fecha" 
          outlined dense
          readonly
          bg-color="teal-1"
          label-color="teal-10"
        >
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer" @click="calendarioAbierto = true" />
            <q-icon v-if="fechaBusqueda" name="close" class="cursor-pointer" @click="limpiarFecha" />
          </template>
        </q-input>

        <q-popup-proxy v-model="calendarioAbierto" transition-show="scale" transition-hide="scale">
          <q-date 
            v-model="fechaBusqueda"
            mask="YYYY-MM-DD"
            @update:model-value="filtrarPorFecha"
          />
        </q-popup-proxy>
      </div>

    </div>

    <q-card-section>
      <div class="q-pa-md">
        <q-table 
          flat 
          title="Lista de ventas" 
          class="my-sticky-header-table bg-teal-9 text-white" 
          bordered
          :rows="ventasFiltradas" 
          :columns="columnas" 
          row-key="idVenta" 
          no-data-label="No hay ventas registradas"
          :pagination.sync="paginacion" 
          rows-per-page-label="Filas por página:"
        >
          <template v-slot:body-cell-acciones="props">
            <q-td align="center">
              <q-btn color="info" icon="visibility" size="sm" square class="q-ml-md q-px-md"
                @click.stop="verDetalleVenta(props.row)" />
              <q-tooltip class="bg-indigo" :offset="[10, 10]">
                Detalle venta
              </q-tooltip>
            </q-td>
          </template>
        </q-table>
      </div>
    </q-card-section>

    <!-- MODAL DETALLE VENTA -->
    <q-dialog v-model="verDetalleDialog" persistent>
      <q-card class="q-pa-md" style="min-width: 420px; max-width: 620px;">
        <q-card-section class="bg-teal-8 text-white">
          <div class="text-h6">Detalle de Venta</div>
        </q-card-section>

        <q-card-section v-if="ventaSeleccionada && detalleVenta.length">
          <div><b>Número de Venta:</b> {{ ventaSeleccionada.numeroVenta }}</div>
          <div><b>Empleado:</b> {{ ventaSeleccionada.nombreEmpleado }}</div>
          <div><b>Fecha:</b> {{ new Date(ventaSeleccionada.fechaVenta).toLocaleString() }}</div>
          <div><b>Total:</b> Bs. {{ ventaSeleccionada.totalventa }}</div>

          <q-separator class="q-my-md" />

          <div class="text-subtitle2 q-mb-sm">Productos Vendidos:</div>

          <q-list bordered separator>
            <q-item v-for="item in detalleVenta" :key="item.producto">
              <q-item-section>
                <div><b>{{ item.producto }}</b></div>
                <div>Cantidad: {{ item.cantidad }}</div>
                <div>Precio: {{ item.precioUnitario }} Bs.</div>
                <div>Subtotal: {{ item.subtotal }} Bs.</div>
              </q-item-section>
            </q-item>
          </q-list>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="Cerrar" color="primary" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>

  </q-card>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import ListarVentas from "../../service/VentaService";

// Paginación
const paginacion = ref({
  page: 1,
  rowsPerPage: 10,
  descending: false,
});

// Datos
const ventas = ref([]);
const terminoBusqueda = ref("");
const fechaBusqueda = ref("");
const calendarioAbierto = ref(false);
const dense = ref(true);

// Modal detalle
const verDetalleDialog = ref(false);
const ventaSeleccionada = ref(null);
const detalleVenta = ref([]);

// Columnas tabla
const columnas = [
  { name: "nombreEmpleado", label: "Empleado", field: "nombreEmpleado", align: "left" },
  { name: "numeroVenta", label: "Número Venta", field: "numeroVenta", align: "left" },
  { name: "fechaVenta", label: "Fecha Venta", align: "left", field: row => new Date(row.fechaVenta).toLocaleDateString() },
  { name: "estado", label: "Estado", field: "estado", align: "left" },
  { name: "totalventa", label: "Total Venta", field: "totalventa", align: "left" },
  { name: "acciones", label: "Acciones", align: "center" },
];

// 🚀 Funciones de búsqueda
function limparBusqueda() {
  terminoBusqueda.value = "";
  cargarVentas();
}

function limpiarFecha() {
  fechaBusqueda.value = "";
  cargarVentas();
}

// Buscar por número
async function buscarVentasPorId() {
  try {
    if (!terminoBusqueda.value.trim()) {
      await cargarVentas();
      return;
    }
    const resp = await ListarVentas.obtererDetalleventa(terminoBusqueda.value);
    ventas.value = resp.data ? [resp.data] : [];
  } catch (error) {
    console.error("Error al buscar venta por ID", error);
  }
}

// Filtrar por fecha
function filtrarPorFecha() {
  // La tabla se actualiza automáticamente usando el computed 'ventasFiltradas'
}

// Cargar todas las ventas
const cargarVentas = async () => {
  try {
    const response = await ListarVentas.listarVentas();
    ventas.value = response.data;
  } catch (error) {
    console.error("Error al cargar ventas:", error);
  }
};

// Modal detalle
async function verDetalleVenta(venta) {
  try {
    ventaSeleccionada.value = venta;
    const resp = await ListarVentas.obtererDetalleventa(venta.idVenta);
    detalleVenta.value = resp.data.detalles || [];
    verDetalleDialog.value = true;
  } catch (err) {
    console.error("Error al obtener detalle de venta", err);
  }
}

// Computed para combinar filtros
const ventasFiltradas = computed(() => {
  return ventas.value.filter(v => {
    const coincideNumero = !terminoBusqueda.value || v.numeroVenta.includes(terminoBusqueda.value);
    const coincideFecha = !fechaBusqueda.value || v.fechaVenta.startsWith(fechaBusqueda.value);
    return coincideNumero && coincideFecha;
  });
});

onMounted(() => {
  cargarVentas();
});
</script>
