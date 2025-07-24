import axios from 'axios'

console.log('user.js API文件已加载')

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
  getUserById(userId) {
    return axios.get(`/api/users/detail/${userId}`)
  },

  // 根据ID获取用户名
  getUserNameById(userId) {
    return axios.get('/api/users/getUserNameById', { params: { userId } })
  },

  // 更新用户
  updateUser(userData) {
    return axios.put('/api/users/update', userData)
  },

  // 删除用户 - 使用路径参数
  deleteUser(userId) {
    console.log('删除用户API调用，原始userId:', userId, '类型:', typeof userId)
    
    if (!userId || userId === undefined || userId === null) {
      console.error('userId为空或未定义！')
      return Promise.reject(new Error('用户ID不能为空'))
    }
    
    const url = `/api/users/delete/${userId}`
    console.log('最终请求URL:', url)
    return axios.delete(url)
  },

  // 添加用户
  addUser(userData) {
    return axios.post('/api/users/insert', userData)
  }
}
