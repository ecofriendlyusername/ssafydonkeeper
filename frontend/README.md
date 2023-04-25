# frontend

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).


{
    path: '/book',
    children: [
      {
        path: '/calendar',
        name: 'calendar',
        component: () => import('../views/book/calendarView.vue')
      },
      {
        path: '/add',
        name: 'add',
        component: () => import('../views/book/addView.vue')
      },
      {
        path: '/:id',
        name: 'detail',
        component: () => import('../views/book/detailView.vue')
      },
    ]
  },
  {
    path: '/research',
    children: [
      {
        path: '/',
        name: 'research',
        component: () => import('../views/research/researchView.vue')
      },
      {
        path: '/recom',
        name: 'recom',
        component: () => import('../views/research/researchView.vue')
      },
    ]
  },
  {
    path: '/budget',
    children: [
      {
        path: '/',
        name: 'budget',
        component: () => import('../views/budget/budgetView.vue')
      },
      {
        path: '/set',
        name: 'set',
        component: () => import('../views/budget/setView.vue')
      },
      {
        path: '/update',
        name: 'update',
        component: () => import('../views/budget/updateView.vue')
      },
    ]
  },
  {
    path: '/comparison',
    children: [
      {
        path: '/',
        name: 'comparison',
        component: () => import('../views/comparison/comparisonView.vue')
      },
      {
        path: '/group',
        name: 'group',
        component: () => import('../views/comparison/groupView.vue')
      },
      {
        path: '/challenge',
        name: 'challenge',
        component: () => import('../views/comparison/challengeView.vue')
      },
      {
        path: '/challenge/add',
        name: 'challengeAdd',
        component: () => import('../views/comparison/challengeAddView.vue')
      },
      {
        path: '/challenge/:id',
        name: 'challengeDetail',
        component: () => import('../views/comparison/detailView.vue')
      },