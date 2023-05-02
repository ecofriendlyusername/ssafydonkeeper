import { createStore } from 'vuex'

export default createStore({
  state: {
    title: '메인페이지',
  },
  getters: {
    getTitle(state) {
      return state.title;
    }
  },
  mutations: {
    setTitle(state, payload) {
      state.title = payload;
    }
  },
  actions: {
    setTitle({ commit }, payload) {
      commit("setTitle", payload);
    }
  },
  modules: {
  }
})
