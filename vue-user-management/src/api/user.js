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
  getUserById(Id) {
    return axios.get(`/api/users/detail/${Id}`)
  },

  // 根据ID获取用户名
  getUserNameById(Id) {
    return axios.get('/api/users/getUserNameById', { params: { Id } })
  },

  // 更新用户
  updateUser(userData) {
    return axios.put('/api/users/update', userData)
  },

  // 删除用户 - 使用路径参数
  deleteUser(Id) {
    console.log('删除用户API调用，原始Id:', Id, '类型:', typeof Id)
    
    if (!Id || Id === undefined || Id === null) {
      console.error('Id为空或未定义！')
      return Promise.reject(new Error('用户ID不能为空'))
    }
    
    const url = `/api/users/delete/${Id}`
    console.log('最终请求URL:', url)
    return axios.delete(url)
  },

  // 添加用户
  addUser(userData) {
    return axios.post('/api/users/insert', userData)
  }
}
