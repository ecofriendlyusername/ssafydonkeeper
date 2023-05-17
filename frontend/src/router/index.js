import { createRouter, createWebHistory } from 'vue-router'


const routes = [
  {
    path: '/challange',
    children: [
      {
        path: '',
        name: 'challangeList',
        component: () => import('../views/challange/challangeListView.vue')
      },
      {
        path: 'add',
        name: 'challangeAdd',
        component: () => import('../views/challange/challangeAddView.vue')
      },
      {
        path: ':id',
        name: 'challangeDetail',
        component: () => import('../views/challange/challangeDetailView.vue')
      },

    ]

  },
  {
    path: '/group',
    name: 'group',
    component: () => import('../views/group/groupView.vue'),
  },
  {
    path: '/group/add',
    name: 'groupadd',
    component: () => import('../views/group/groupAddView.vue')
  },
  {
    path: '/group/update/:id',
    name: 'groupupdate',
    component: () => import('../views/group/groupUpdateView.vue')
  },
  {
    path: '/group/detail/:id',
    name: 'groupDetail',
    component: () => import('../views/group/groupDetail.vue')
  },
  {
    path: '/book',
    children: [
      {
        path: 'calendar',
        name: 'calendar',
        component: () => import('../views/book/calendarView.vue')
      },
      {
        path: 'add',
        name: 'add',
        component: () => import('../views/book/addView.vue')
      },
      {
        path: 'list',
        name: 'list',
        component: () => import('../views/book/listView.vue')
      },
      {
        path: 'detail',
        name: 'bookDetail',
        component: () => import('../views/book/detailView.vue')
      },
    ]
  },
  {
    path: '/research',
    children: [
      {
        path: '',
        name: 'research',
        component: () => import('../views/research/researchView.vue')
      },
      {
        path: 'cardList',
        name: 'cardList',
        component: () => import('../views/research/cardListView.vue')
      },
      {
        path: 'card/:id',
        name: 'cardDetail',
        component: () => import('../views/research/cardDetail.vue')
      },
    ]
  },
  {
    path: '/budget',
    children: [
      {
        path: '',
        name: 'budget',
        component: () => import('../views/budget/budgetView.vue')
      },
      {
        path: 'set',
        name: 'set',
        component: () => import('../views/budget/setView.vue')
      },
      {
        path: 'update',
        name: 'update',
        component: () => import('../views/budget/updateView.vue')
      },
    ]
  },
  {
    path: '/comparison',
    children: [
      {
        path: '',
        name: 'comparison',
        component: () => import('../views/comparison/comparisonView.vue')
      },
      
      {
        path: 'challenge',
        name: 'challenge',
        component: () => import('../views/comparison/challengeView.vue')
      },
      {
        path: 'challenge/add',
        name: 'challengeAdd',
        component: () => import('../views/comparison/challengeAddView.vue')
      },
      {
        path: 'challenge/:id',
        name: 'challengeDetail',
        component: () => import('../views/comparison/detailView.vue')
      },

    ]
  },
  //   path: '/calendar',
  //   name: 'calendar',
  //   component: () => import('../views/calendarView.vue')
  // },
  {
    path: '/kakaoCallback',
    name: 'kakaoCallback',
    component: () => import('../views/kakaoCallback.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
