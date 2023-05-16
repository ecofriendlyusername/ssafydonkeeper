import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  plugins: [createPersistedState({
    storage: window.sessionStorage,
  })],
  state: {
    loginCheck: false,
    userData: {
      id: null,
      email: null,
      nickname: null
    }
  },
  getters: {
    getLoginCheck(state) {
      return state.loginCheck;
    },
    getUserData(state) {
      return state.userData;
    },
  },
  mutations: {
    setLoginCheck(state, payload) {
      state.loginCheck = payload;
    },
    setUserData(state, payload) {
      state.userData = payload;
    }
  },
  actions: {
    setLoginCheck({ commit }, payload) {
      commit("setLoginCheck", payload);
    },
    setUserData({ commit }, payload) {
      commit("setUserData", payload);
    }
  },
  modules: {
  }
})
