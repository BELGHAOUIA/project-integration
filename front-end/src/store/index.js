import Vue from 'vue'
import Vuex from 'vuex'
import { auth } from './auth.module';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    numeroSalle: ""
  },
  getters: {
    getNumeroSalle (state) {
      state.numeroSalle = JSON.parse(localStorage.getItem('numeroSalle'));
      return state.numeroSalle
    }
  },
  mutations: {
    setNumeroSalle (state, n) {
      state.numeroSalle = n;
      localStorage.setItem('numeroSalle', JSON.stringify(n));
    }
  },
  actions: {
  },
  modules: {
    auth
  }
})
