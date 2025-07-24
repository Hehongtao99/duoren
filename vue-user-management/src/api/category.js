import axios from 'axios'

// 分类相关的API封装
export default {
  // 获取所有分类
  getAllCategories() {
    return axios.get('/api/categories/list')
  },

  // 添加分类
  addCategory(categoryName) {
    return axios.post('/api/categories/add', null, { params: { categoryName } })
  },

  // 更新分类
  updateCategory(categoryId, categoryName) {
    return axios.put('/api/categories/update', null, { params: { categoryId, categoryName } })
  },

  // 删除分类
  deleteCategory(categoryId) {
    return axios.delete('/api/categories/delete', { params: { categoryId } })
  }
}
