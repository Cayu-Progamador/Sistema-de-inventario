<template>
  <q-page padding>

    <div class="row q-col-gutter-md q-mb-md">

      <!-- Total Ventas -->
      <div class="col-12 col-md-3">
        <q-card class="bg-teal-10 text-white">
          <q-card-section>
            <div class="text-h6">Total Ventas</div>
            <div class="text-h4 q-mt-sm">{{ totalVentas }} Bs.</div>
          </q-card-section>
        </q-card>
      </div>

      <!-- Ventas Hoy -->
      <div class="col-12 col-md-3">
        <q-card class="bg-teal-8 text-white">
          <q-card-section>
            <div class="text-h6">Ventas Hoy</div>
            <div class="text-h4 q-mt-sm">{{ ventasHoy }} Bs.</div>
          </q-card-section>
        </q-card>
      </div>

      <!-- Filtrar por fecha -->
      <div class="col-12 col-md-6">
        <q-input
          v-model="fechaFiltro"
          label="Filtrar ventas por fecha"
          outlined
          dense
          readonly
          bg-color="teal-1"
        >
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer" @click="calendarioAbierto = true"/>
            <q-icon v-if="fechaFiltro" name="close" class="cursor-pointer" @click="limpiarFecha"/>
          </template>
        </q-input>

        <q-popup-proxy v-model="calendarioAbierto" transition-show="scale" transition-hide="scale">
          <q-date v-model="fechaFiltro" mask="YYYY-MM-DD" @update:model-value="filtrarPorFecha"/>
        </q-popup-proxy>
      </div>

    </div>

    <!-- Gráfico de ventas por fecha -->
    <q-card class="q-mb-md">
      <q-card-section>
        <div class="text-h6">Ventas por fecha</div>
        <canvas id="graficoVentas"></canvas>
      </q-card-section>
    </q-card>

    <!-- Últimas ventas -->
    <q-card>
      <q-card-section>
        <div class="text-h6">Últimas Ventas</div>
      </q-card-section>
      <q-table
        :rows="ventasFiltradas"
        :columns="columnas"
        row-key="idVenta"
        flat
        bordered
      />
    </q-card>

  </q-page>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import ListarVentas from '../../service/VentaService';
import Chart from 'chart.js/auto';

// Datos
const ventas = ref([]);
const fechaFiltro = ref('');
const calendarioAbierto = ref(false);

// Tabla
const columnas = [
  { name: 'numeroVenta', label: 'N° Venta', field: 'numeroVenta' },
  { name: 'fechaVenta', label: 'Fecha', field: row => new Date(row.fechaVenta).toLocaleString() },
  { name: 'totalventa', label: 'Total (Bs.)', field: 'totalventa' },
];

// Computed
const ventasFiltradas = computed(() => {
  if (!fechaFiltro.value) return ventas.value;
  return ventas.value.filter(v => v.fechaVenta.startsWith(fechaFiltro.value));
});

const totalVentas = computed(() => {
  return ventasFiltradas.value.reduce((sum, v) => sum + v.totalventa, 0);
});

const hoy = new Date().toISOString().slice(0,10);
const ventasHoy = computed(() => {
  return ventas.value
    .filter(v => v.fechaVenta.startsWith(hoy))
    .reduce((sum, v) => sum + v.totalventa, 0);
});

// Funciones
function limpiarFecha() {
  fechaFiltro.value = '';
}

// Filtrar gráfico
function filtrarPorFecha() {
  actualizarGrafico();
}

// Cargar ventas
const cargarVentas = async () => {
  try {
    const response = await ListarVentas.listarVentas();
    ventas.value = response.data;
    actualizarGrafico();
  } catch (err) {
    console.error('Error cargando ventas', err);
  }
};

// Gráfico
let grafico;
function actualizarGrafico() {
  const labels = ventasFiltradas.value.map(v => new Date(v.fechaVenta).toLocaleDateString());
  const data = ventasFiltradas.value.map(v => v.totalventa);

  if (grafico) grafico.destroy();

  const ctx = document.getElementById('graficoVentas').getContext('2d');
  grafico = new Chart(ctx, {
    type: 'bar',
    data: {
      labels,
      datasets: [{
        label: 'Ventas (Bs.)',
        data,
        backgroundColor: '#26a69a'
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: { display: false },
      }
    }
  });
}

onMounted(() => {
  cargarVentas();
});
</script>
