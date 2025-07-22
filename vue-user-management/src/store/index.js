import { createStore } from 'vuex'
import axios from 'axios'

export default createStore({
  state: {
    users: [],
    currentUser: null,
    totalUsers: 0,
    currentPage: 1,
    pageSize: 10
  },
  getters: {
    getUserById: (state) => (id) => {
      return state.users.find(user => user.id === id)
    }
  },
  mutations: {
    SET_USERS(state, users) {
      state.users = users
    },
    SET_CURRENT_USER(state, user) {
      state.currentUser = user
    },
    SET_TOTAL_USERS(state, total) {
      state.totalUsers = total
    },
    SET_CURRENT_PAGE(state, page) {
      state.currentPage = page
    },
    SET_PAGE_SIZE(state, size) {
      state.pageSize = size
    }
  },
  actions: {
    // 获取所有用户
    async getAllUsers({ commit }) {
      try {
        const response = await axios.get('/api/users/list')
        if (response.data.code === 200) {
          commit('SET_USERS', response.data.data)
        }
        return response.data
      } catch (error) {
        console.error('获取用户列表失败:', error)
        return { code: 500, message: '获取用户列表失败' }
      }
    },
    
    // 分页获取用户
    async getPageUsers({ commit, state }, params = {}) {
      try {
        const queryParams = {
          current: params.current || state.currentPage,
          size: params.size || state.pageSize,
          userName: params.userName || '',
          email: params.email || ''
        }
        
        const response = await axios.get('/api/users/page', { params: queryParams })
        if (response.data.code === 200) {
          commit('SET_USERS', response.data.records)
          commit('SET_TOTAL_USERS', response.data.total)
          commit('SET_CURRENT_PAGE', response.data.current)
          commit('SET_PAGE_SIZE', response.data.size)
        }
        return response.data
      } catch (error) {
        console.error('分页获取用户失败:', error)
        return { code: 500, message: '分页获取用户失败' }
      }
    },
    
    // 根据ID获取用户
    async getUserById({ commit }, id) {
      try {
        const response = await axios.get('/api/users/getUserById', { params: { id } })
        if (response.data.code === 200) {
          commit('SET_CURRENT_USER', response.data.data)
        }
        return response.data
      } catch (error) {
        console.error('获取用户详情失败:', error)
        return { code: 500, message: '获取用户详情失败' }
      }
    },
    
    // 更新用户
    async updateUser({ dispatch }, userData) {
      try {
        const response = await axios.put('/api/users/update', userData)
        if (response.data.code === 200) {
          // 更新成功后重新获取用户列表
          dispatch('getPageUsers')
        }
        return response.data
      } catch (error) {
        console.error('更新用户失败:', error)
        return { code: 500, message: '更新用户失败' }
      }
    },
    
    // 删除用户
    async deleteUser({ dispatch }, id) {
      try {
        const response = await axios.delete('/api/users/delete', { 
          data: { id }
        })
        if (response.data.code === 200) {
          // 删除成功后重新获取用户列表
          dispatch('getPageUsers')
        }
        return response.data
      } catch (error) {
        console.error('删除用户失败:', error)
        return { code: 500, message: '删除用户失败' }
      }
    }
  }
}) 