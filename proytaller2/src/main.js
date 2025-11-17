import { createApp } from 'vue'
import App from './App.vue'
import '../src/assets/styles/style.css'

//quasar 
import { Quasar } from 'quasar'
import quasarSettings  from '../quasar.settings'
import 'quasar/dist/quasar.css'
import '@quasar/extras/material-icons/material-icons.css'

//router
import router from './router'
const app = createApp(App)

//configuración de vue router
app.use(router)
//configuración de quasar
app.use(Quasar, quasarSettings)

app.mount('#app')
