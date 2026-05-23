import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', redirect: '/create' },
  { path: '/create', name: 'Create', component: () => import('../views/CreateView.vue') },
  { path: '/history', name: 'History', component: () => import('../views/HistoryView.vue') },
  { path: '/settings', name: 'Settings', component: () => import('../views/SettingsView.vue') },
]

export default createRouter({
  history: createWebHistory(),
  routes,
})
