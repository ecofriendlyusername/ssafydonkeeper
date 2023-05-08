import { createStore } from 'vuex'

export default createStore({
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
