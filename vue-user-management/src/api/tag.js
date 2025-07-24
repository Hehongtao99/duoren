import axios from 'axios'

// 标签相关的API封装
export default {
  // 获取所有标签
  getAllTags() {
    return axios.get('/api/tags/list')
  },

  // 添加标签
  addTag(tagName) {
    return axios.post('/api/tags/add', null, { params: { tagName } })
  },

  // 更新标签
  updateTag(tagId, tagName) {
    return axios.put('/api/tags/update', null, { params: { tagId, tagName } })
  },

  // 删除标签
  deleteTag(tagId) {
    return axios.delete('/api/tags/delete', { params: { tagId } })
  }
}
