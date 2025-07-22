import axios from 'axios'

// 用户相关的API封装
export default {
  // 获取所有用户
  getAllUsers() {
    return axios.get('/api/users/list')
  },
  
  // 分页获取用户列表
  getPageUsers(params) {
    return axios.get('/api/users/page', { params })
  },
  
  // 根据ID获取用户
  getUserById(id) {
    return axios.get('/api/users/getUserById', { params: { id } })
  },
  
  // 根据ID获取用户名
  getUserNameById(id) {
    return axios.get('/api/users/getUserNameById', { params: { id } })
  },
  
  // 更新用户
  updateUser(userData) {
    return axios.put('/api/users/update', userData)
  },
  
  // 删除用户
  deleteUser(id) {
    return axios.delete('/api/users/delete', { data: { id } })
  }
} 