import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import SvgIcon from '@jamescoyle/vue-icon'
import AOS from 'aos'
import 'aos/dist/aos.css'
import { mdiAccount } from '@mdi/js'
Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  SvgIcon,mdiAccount,
  render: h => h(App)
}).$mount('#app',  AOS.init())
