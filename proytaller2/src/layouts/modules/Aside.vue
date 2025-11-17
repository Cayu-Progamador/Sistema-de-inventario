<template>
  <q-scroll-area class="fit" :horizontal-thumb-style="{ opacity: 0 }">

    <q-list padding bordered separator>

      <q-item clickable v-ripple to="/">
        <q-item-section avatar>
          <q-icon name="dashboard" />
        </q-item-section>
        <q-item-section>Dashboard</q-item-section>
      </q-item>

      <q-expansion-item
        exexpand-separator
        group="somegroup" 
        icon="shopping_cart" 
        label="Ventas" 
        header-class=" text-white"
        expand-icon-class="text-white"
      >
        <q-item clickable v-ripple to="/venta/nuevo" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="add_shopping_cart" />
          </q-item-section>
          <q-item-section>Añadir venta</q-item-section>
        </q-item>

        <q-item clickable v-ripple to="/venta/lista" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="info" />
          </q-item-section>
          <q-item-section>detalle venta</q-item-section>
        </q-item>
        
      </q-expansion-item>

      <!-- productos -->
       <q-expansion-item 
        expand-separator
        group="somegroup" 
        icon="inventory_2" 
        label="Productos" 
        header-class=" text-white"
        expand-icon-class="text-white"
      >



        <q-item clickable v-ripple to="/producto/nuevo" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="add_box" />
          </q-item-section>
          <q-item-section>Agregar Producto</q-item-section>
        </q-item>


        <!-- lista cliente -->

        <q-item clickable v-ripple to="/producto/lista" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="edit_note" />
          </q-item-section>
          <q-item-section>Editar Producto</q-item-section>
        </q-item>


        <q-item clickable v-ripple to="/producto/detalle"class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="visibility" />
          </q-item-section>
          <q-item-section>Detalle producto</q-item-section>
        </q-item>

      </q-expansion-item>
      
      <q-expansion-item 
        expand-separator
        group="somegroup" 
        icon="group" 
        label="Clientes" 
        header-class=" text-white"
        expand-icon-class="text-white"
      >



        <q-item clickable v-ripple to="/cliente/nuevo" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="person" />
          </q-item-section>
          <q-item-section>Agregar Clientes</q-item-section>
        </q-item>


        <!-- lista cliente -->

        <q-item clickable v-ripple to="/cliente/lista" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="edit" />
          </q-item-section>
          <q-item-section>Editar Clientes</q-item-section>
        </q-item>


        <q-item clickable v-ripple to="/cliente/detalle"class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="receipt_long" />
          </q-item-section>
          <q-item-section>Historial de Clientes</q-item-section>
        </q-item>

      </q-expansion-item>

      <!-- vista de empleados-->
      <q-expansion-item 
        expand-separator
        group="somegroup" 
        icon="engineering" 
        label="Empleados" 
        header-class=" text-white"
        expand-icon-class="text-white"
      >



        <q-item clickable v-ripple to="/empleado/nuevo" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="person_add" />
          </q-item-section>
          <q-item-section>Agregar empleado</q-item-section>
        </q-item>


        <!-- lista cliente -->

        <q-item clickable v-ripple to="/empleado/lista" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="edit" />
          </q-item-section>
          <q-item-section>Editar Empleado</q-item-section>
        </q-item>


        <q-item clickable v-ripple to="/cliente/detalle"class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="receipt_long" />
          </q-item-section>
          <q-item-section>Historial de Clientes</q-item-section>
        </q-item>

      </q-expansion-item>


      <!-- Proveedores  -->
      <q-expansion-item 
        expand-separator
        group="somegroup" 
        icon="business" 
        label="Proveedores" 
        header-class=" text-white"
        expand-icon-class="text-white"
      >



        <q-item clickable v-ripple to="/proveedor/nuevo" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="add_business" />
          </q-item-section>
          <q-item-section>Agregar proveedor</q-item-section>
        </q-item>


        <!-- lista cliente -->

        <q-item clickable v-ripple to="/proveedor/lista" class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="edit_note" />
          </q-item-section>
          <q-item-section>Editar Proveedor</q-item-section>
        </q-item>


        <q-item clickable v-ripple to="/proveedor/consultar"class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="receipt_long" />
          </q-item-section>
          <q-item-section>Lista de Proveedores</q-item-section>
        </q-item>
        
        <q-item clickable v-ripple to="/pedido/realizar"class="q-pl-xl">
          <q-item-section avatar>
            <q-icon name="local_shipping" />
          </q-item-section>
          <q-item-section>Realizar Pedido</q-item-section>
        </q-item>

      </q-expansion-item>

      <!-- Agrega más items según tus rutas -->
    </q-list>
  </q-scroll-area>

</template>

<script setup>

</script>
