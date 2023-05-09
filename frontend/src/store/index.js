import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  plugins: [createPersistedState({
    storage: window.sessionStorage,
  })],
  state: {
    loginCheck: false,
  },
  getters: {
    getLoginCheck(state) {
      return state.loginCheck;
    }
  },
  mutations: {
    setLoginCheck(state, payload) {
      state.loginCheck = payload;
    }
  },
  actions: {
    setLoginCheck({ commit }, payload) {
      commit("setLoginCheck", payload);
    }
  },
  modules: {
  }
})
