import { createRouter, createWebHistory } from 'vue-router'


const routes = [
  // {
  //   path: '/',
  //   name: 'main',
  //   component: () => import(/* webpackChunkName: "about" */ '../views/mainPageView.vue')
  // },
  {
    path: '/calendar',
    name: 'calendar',
    component: () => import('../views/calendarView.vue')
  },
  {
    path: '/kakaoCallback',
    name: 'kakaoCallback',
    component: () => import('../views/kakaoCallback.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
