import axios from 'axios'

// 文章相关的API封装
export default {
  // 获取所有文章
  getAllNotes() {
    return axios.get('/api/notes/list')
  },

  // 分页获取文章列表
  getPageNotes(params) {
    return axios.get('/api/notes/page', { params })
  },

  // 根据ID获取文章详情
  getNoteById(noteId) {
    return axios.get('/api/notes/detail', { params: { noteId } })
  },

  // 添加文章
  addNote(noteData) {
    return axios.post('/api/notes/add', noteData)
  },

  // 更新文章
  updateNote(noteData) {
    return axios.put('/api/notes/update', noteData)
  },

  // 删除文章
  deleteNote(noteId) {
    return axios.delete('/api/notes/delete', { params: { noteId } })
  }
}
