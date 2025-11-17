<template>
  <q-page padding>

    <!-- Lista de proveedores -->
    <div class="text-h6 q-mb-md text-center">Lista de Proveedores</div>
    <q-table
      :rows="proveedores"
      :columns="columnasProveedores"
      row-key="idProveedor"
      flat
      class="bg-teal-7 text-white"
      bordered
    >
      <template v-slot:body-cell-acciones="props">
        <q-td align="center">
          <q-btn color="primary" label="Comprar" @click="seleccionarProveedor(props.row)"             class="q-ml-sm q-pl-ms bg-teal-10 text-white"/>
        </q-td>
      </template>
    </q-table>

    <!-- Productos del proveedor seleccionado -->
    <div v-if="proveedorSeleccionado" class="q-mt-lg">
      <div class="text-h6 q-mb-md">Productos de: {{ proveedorSeleccionado.nombreProveedor }}</div>
      <q-table
        :rows="productosFiltrados"
        :columns="columnasProductos"
        row-key="idProductoProveedor"
        flat
        bordered
        class="bg-teal-6 text-white"
      >
        <template v-slot:body-cell-cantidad="props">
          <q-td>
            <q-input
              v-model.number="props.row.cantidad"
              type="number"
              min="1"
              dense
              bg-color="teal-10 text-white"
              style="width: 80px"
            />
          </q-td>
        </template>
        <template v-slot:body-cell-agregar="props">
          <q-td align="center">
            <q-btn
              color="primary"
              icon="add"
              class="q-ml-sm q-pl-ms bg-teal-10 text-white"
              @click="agregarALista(props.row)"
            />
          </q-td>
        </template>
      </q-table>
    </div>

    <!-- Lista de productos a comprar -->
    <div v-if="pedido.detalles.length" class="q-mt-lg">
      <div class="text-h6 q-mb-md">Productos a comprar</div>
      <q-table
        :rows="pedido.detalles"
        :columns="columnasDetalle"
        row-key="idProductoProveedor"
        flat
        bordered
      >
        <template v-slot:body-cell-subtotal="props">
          <q-td>{{ formatoMoneda(props.row.subtotal) }}</q-td>
        </template>
        <template v-slot:body-cell-eliminar="props">
          <q-td align="center">
            <q-btn color="negative" icon="delete" @click="eliminarDetalle(props.row)" />
          </q-td>
        </template>
      </q-table>

      <!-- Total -->
      <div class="q-mt-md text-h6">
        Total Pedido: {{ formatoMoneda(totalPedido) }}
      </div>

      <!-- Fecha y enviar -->
      <q-form @submit.prevent="enviarPedido" class="q-mt-md">
        <q-input
          v-model="pedido.fechaEntrega"
          type="date"
          label="Fecha de entrega"
          filled
          :min="fechaHoy"
        />
        <q-btn label="Realizar Pedido" type="submit" class="q-mt-md bg-teal-10 text-white" />
      </q-form>
    </div>

  </q-page>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useQuasar } from "quasar";
import ListProveedor from "../../service/ProveedorService";
import ProductoService from "../../service/ProductoService";
import apiPedido from "../../service/PedidoService";

const $q = useQuasar();

// Datos
const proveedores = ref([]);
const productos = ref([]);
const productosFiltrados = ref([]);
const proveedorSeleccionado = ref(null);

const pedido = ref({
  idProveedor: null,
  fechaEntrega: new Date().toISOString().split("T")[0],
  detalles: []
});
const fechaHoy = new Date().toISOString().split("T")[0];

// Columnas tablas
const columnasProveedores = [
  { name: "nombre", label: "Nombre", field: row => row.nombre, align: "left" },
  { name: "apellido", label: "Apellido", field: row => row.apellido, align: "left" },
  { name: "acciones", label: "Acciones", field: "acciones", align: "center" }
];

const columnasProductos = [
  { name: "nombreProducto", label: "Producto", field: row => row.nombreProducto ,align: "left"},
  { name: "precioCompra", label: "Precio", field: row => `$ ${row.precioCompra.toFixed(2)}`, align: "left"},
  { name: "cantidad", label: "Cantidad", field: "cantidad" ,align: "left"},
  { name: "agregar", label: "Agregar", field: "agregar", align: "center" }
];

const columnasDetalle = [
  { name: "nombreProducto", label: "Producto", field: row => row.nombreProducto,align: "left" },
  { name: "precioCompra", label: "Precio", field: row => `$ ${row.precioCompra.toFixed(2)}`,align: "left" },
  { name: "cantidad", label: "Cantidad", field: "cantidad" ,align: "left"},
  { name: "subtotal", label: "Subtotal", field: "subtotal" ,align: "left"},
  { name: "eliminar", label: "Eliminar", field: "eliminar", align: "center" ,align: "left"}
];

// Computed
const totalPedido = computed(() => {
  return pedido.value.detalles.reduce((acc, d) => acc + d.subtotal, 0);
});

// Funciones
const cargarDatos = async () => {
  try {
    const proveedoresResp = await ListProveedor.listProveedor();
    // Creamos un campo "nombreProveedor" que coincida con el que usan los productos
    proveedores.value = proveedoresResp.data.map(p => ({
      ...p,
      nombreProveedor: p.nombre // solo nombre para coincidir
    }));

    const productosResp = await ProductoService.listarTodosProductosProveedor();
    productos.value = productosResp.data.map(p => ({ ...p, cantidad: 1 }));
  } catch (error) {
    console.error(error);
    $q.notify({ color: "red", message: "Error cargando proveedores o productos" });
  }
};

const seleccionarProveedor = (prov) => {
  proveedorSeleccionado.value = prov;
  pedido.value.idProveedor = prov.idProveedor;
  productosFiltrados.value = productos.value.filter(
    p => p.nombreProveedor.trim() === prov.nombreProveedor.trim()
  );
};

const agregarALista = (producto) => {
  const existe = pedido.value.detalles.find(d => d.idProductoProveedor === producto.idProductoProveedor);
  if (existe) {
    existe.cantidad = producto.cantidad;
    existe.subtotal = producto.precioCompra * producto.cantidad;
  } else {
    pedido.value.detalles.push({
      ...producto,
      subtotal: producto.precioCompra * producto.cantidad
    });
  }
};

const eliminarDetalle = (producto) => {
  pedido.value.detalles = pedido.value.detalles.filter(d => d.idProductoProveedor !== producto.idProductoProveedor);
};

const formatoMoneda = (valor) => `$ ${valor.toFixed(2)}`;

const enviarPedido = async () => {
  if (!pedido.value.idProveedor || !pedido.value.fechaEntrega || !pedido.value.detalles.length) {
    $q.notify({ color: "red", message: "Completa todos los campos antes de enviar" });
    return;
  }
  try {
    const payload = {
      idProveedor: pedido.value.idProveedor,
      fechaEntrega: pedido.value.fechaEntrega,
      detalles: pedido.value.detalles.map(d => ({
        idProducto: d.idProductoProveedor,
        cantidad: d.cantidad
      }))
    };
    await apiPedido.crearPedido(payload);
    $q.notify({ color: "green", message: "Pedido realizado con éxito" });

    // Limpiar
    pedido.value = { idProveedor: null, fechaEntrega: fechaHoy, detalles: [] };
    proveedorSeleccionado.value = null;
    productosFiltrados.value = [];
  } catch (error) {
    console.error(error);
    $q.notify({ color: "red", message: "Error al realizar pedido" });
  }
};

onMounted(() => cargarDatos());
</script>
