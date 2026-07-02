<template>
  <q-toolbar class="bg-teal-10 text-white q-pa-md">

    <!-- Botón menú -->
    <q-btn 
      flat 
      @click="$emit('toggle-drawer')" 
      round 
      dense 
      icon="menu" 
    />



    <q-space />

    <!-- Avatar / Login -->
    <div class="row items-center">
      <q-avatar 
        size="40px" 
        class="cursor-pointer" 
        @click="abrirLogin = true"
      >
        <img src="https://cdn-icons-png.flaticon.com/512/149/149071.png" />
      </q-avatar>
    </div>

    <!-- Dialog Login con solo avatar -->
    <q-dialog v-model="abrirLogin" persistent>
      <q-card class="q-pa-md flex flex-center column" style="min-width: 200px;">
        <q-avatar size="80px" class="q-mb-md">
          <img src="https://cdn-icons-png.flaticon.com/512/149/149071.png" />
        </q-avatar>
        <div class="text-h6 q-mb-md">Bienvenido</div>
        <q-btn flat label="Entrar" color="teal-10" @click="login" />
        <q-btn flat label="Cancelar" color="grey" class="q-mt-sm" v-close-popup />
      </q-card>
    </q-dialog>

  </q-toolbar>
</template>

<script setup>
import { ref } from 'vue';
import { useQuasar } from 'quasar';

const $q = useQuasar();
const abrirLogin = ref(false);
const usuarioLogueado = ref(null);

function login() {
  // Simulación de login al hacer click en el avatar
  usuarioLogueado.value = { nombre: 'Administrador' };
  abrirLogin.value = false;
  $q.notify({ color: 'positive', message: 'Bienvenido, Administrador' });
}
</script>

<style scoped>
/* Cursor pointer para avatar */
.cursor-pointer {
  cursor: pointer;
}
</style>
