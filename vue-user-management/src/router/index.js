import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'UserList',
    component: () => import('../views/UserList.vue')
  },
  {
    path: '/all-users',
    name: 'AllUsers',
    component: () => import('../views/AllUsers.vue')
  },
  {
    path: '/user/:id',
    name: 'UserDetail',
    component: () => import('../views/UserDetail.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 