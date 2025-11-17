<template>
    <div class="q-pa-md">
        <q-btn label="Volver" color="primary" icon="arrow_back"
            @click="router.push('/cliente/detalle')"  class="q-mb-md bg-teal-7 text-white"/>

          <div class="text-h4 " style="text-align: center;">Historial de Compras</div>
          <q-table  
                flat  
                bordered  
                title="Ventas Realizadas" 
                :rows="historial" 
                :columns="columns"  
                row-key="idVenta"  
                loading-label="Cargando historial..."
                no-data-label="Este cliente no tiene compras registradas"  
                :loading="cargando" 
                class="q-mt-md bg-teal-10 text-white" 
            />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useQuasar } from 'quasar';
// ¡¡IMPORTANTE: Usamos VentaService, no ClienteService!!
import VentaService from '../../service/VentaService';

const route = useRoute();  // Para leer la URL
const router = useRouter();  // Para el botón de "Volver"
const $q = useQuasar();

const historial = ref([]);
const cargando = ref(false);
const idCliente = ref(null);

// --- AQUÍ ESTÁ LA CORRECCIÓN ---
// Esta definición de 'columns' SÍ coincide con tu HistorialProductoDTO
const columns = [
    {
        name: 'fechaVenta',
        label: 'Fecha',
        field: 'fechaVenta', // <-- Coincide con tu DTO
        align: 'left',
        sortable: true,
        format: (val) => (val ? new Date(val).toLocaleString() : 'N/A')
    },
    {
        name: 'numeroVenta',
        label: 'Nro. Venta',
        field: 'numeroVenta', // <-- Coincide con tu DTO
        align: 'left'
    },
    {
        name: 'nombreProducto',
        label: 'Producto',
        field: 'nombreProducto', // <-- Coincide con tu DTO
        align: 'left'
    },
    {
        name: 'cantidad',
        label: 'Cantidad',
        field: 'cantidad', // <-- Coincide con tu DTO
        align: 'center'
    },
    {
        name: 'precioUnitario',
        label: 'Precio Unit.',
        field: 'precioUnitario', // <-- Coincide con tu DTO
        align: 'right',
        sortable: true,
        format: (val) => (val ? `${val.toFixed(2)} Bs.` : '0.00 Bs.')
    },
    {
        name: 'subtotalProducto',
        label: 'Subtotal',
        field: 'subtotalProducto', // <-- Arregla el error 'toFixed'
        align: 'right',
        sortable: true,
        format: (val) => (val ? `${val.toFixed(2)} Bs.` : '0.00 Bs.')
    },
    {
        name: 'nombrePago',
        label: 'Método de Pago',
        field: 'nombrePago', // <-- Coincide con tu DTO
        align: 'left'
    }
];
// --- FIN DE LA CORRECCIÓN ---

onMounted(async () => {
    // 1. Obtenemos el ID del cliente desde la URL
    idCliente.value = route.params.idCliente;

    if (!idCliente.value) {
        $q.notify({ type: 'negative', message: 'No se especificó un ID de cliente' });
        router.push('/cliente/detalle'); // Devuelve al listado
        return;
    }

    await cargarHistorial();

    // Opcional: Cargar los datos del cliente (nombre) para mostrarlos en el título
    // await cargarDatosCliente(); 
});

async function cargarHistorial() {
    cargando.value = true;
    try {
        // 2. Llamamos al servicio de VENTA que creamos en el paso anterior
        const response = await VentaService.getHistorialCliente(idCliente.value)

        // 3. Asignamos los datos a la tabla
        historial.value = Array.isArray(response.data)? response.data: []

    } catch (error) {
        console.error('Error al cargar el historial', error);
        $q.notify({ type: 'negative', message: 'No se pudo cargar el historial del cliente' });
    } finally {
        cargando.value = false;
    }
}

/*
// Opcional: Función para cargar el nombre del cliente
async function cargarDatosCliente() {
  try {
    // Reutilizamos el ClienteService que ya tienes
    const response = await ClienteService.obtenerClientePorId(idCliente.value);
    nombreCliente.value = response.data.nombre;
  } catch (error) {
    console.warn('No se pudo cargar el nombre del cliente', error);
  }
}
*/
</script>