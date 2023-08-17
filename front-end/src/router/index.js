import Vue from 'vue'
import VueRouter from 'vue-router'
import AcceilVue from '../views/AcceilVue.vue'
import PagePc from '../views/PagePC.vue'
import ServicePage from '../views/ServicePage'
import LoginForm from '../views/LoginForm'
import Demande from '../views/Demande'
import PremiereEtage from '../views/PremiereEtage'
import ListProf from '../views/ListProf'
import PageProfil from '../views/PageProfil'
import CompteProf from '../views/CompteProf'
import StockVue from '../views/StockVue'
import SignUpEtudiant from '../views/SignUpEtudiant'
import Home from '../views/HomePage'
import ForgotPassword from '../views/ForgotPassword'

import store from '../store'

Vue.use(VueRouter)

const routes = [
    {
    path: '/',component: Home,meta: { auth:true }
    }, {
      path: '/ListProf',component: ListProf,meta: { auth:true }
      },
    {
    path: '/AcceilVue',component: AcceilVue,meta: { auth:true }
    },
    {
    path: '/PagePc',component: PagePc,meta: { auth:true }
    },  {
    path: '/PageProfil',component: PageProfil,meta: { auth:true }
    },
    {  
    path: '/ServicePage',component: ServicePage,meta: { auth:true }
    },
    {  
    path: '/LoginForm',component: LoginForm,meta: { auth:false }
    },
    {
    path: '/Demande',component: Demande,meta: { auth:true }
    },
    {
    path: '/StockVue',component: StockVue,meta: { auth:true }
    },{
    path: '/PremiereEtage',component: PremiereEtage,meta: { auth:true }
    }
    ,
    {
    path: '/Demande',component: Demande,meta: { auth:true }
    },
    {  
    path: '/CompteProf',component: CompteProf,meta: { auth:true }
    },
    {  
    path: '/SignUpEtudiant',component: SignUpEtudiant,meta: { auth:false }
    },
    {  
    path: '/ForgotPassword',component: ForgotPassword,meta: { auth:false }
    }
];

const router = new VueRouter({
  routes
});

router.beforeEach((to, from, next) => {
  if('auth' in to.meta &&
   to.meta.auth &&
    !store.state.auth.status.loggedIn) {
    next('/LoginForm');
  }

  else if ('auth' in to.meta &&
  !to.meta.auth &&
   store.state.auth.status.loggedIn) {
    next('/');
   }
   else {
    next();
   }
});


export default router
