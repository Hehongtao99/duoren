import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'

// 配置axios
axios.defaults.baseURL = 'http://localhost:8080'

const app = createApp(App)

// 全局配置
app.config.globalProperties.$axios = axios

// 使用插件
app.use(store)
app.use(router)
app.use(ElementPlus)

app.mount('#app')
