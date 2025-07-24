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
  },
  {
    path: '/notes',
    name: 'NoteList',
    component: () => import('../views/NoteList.vue')
  },
  {
    path: '/note/:id',
    name: 'NoteDetail',
    component: () => import('../views/NoteDetail.vue')
  },
  {
    path: '/categories',
    name: 'CategoryManage',
    component: () => import('../views/CategoryManage.vue')
  },
  {
    path: '/tags',
    name: 'TagManage',
    component: () => import('../views/TagManage.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
