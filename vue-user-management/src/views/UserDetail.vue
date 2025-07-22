<template>
  <div class="user-detail-container">
    <el-card class="user-card" v-loading="loading">
      <template #header>
        <div class="card-header">
          <span>用户详情</span>
          <el-button @click="goBack">返回</el-button>
        </div>
      </template>
      <div v-if="user" class="user-info">
        <div class="info-item">
          <span class="label">ID:</span>
          <span class="value">{{ user.id }}</span>
        </div>
        <div class="info-item">
          <span class="label">用户名:</span>
          <span class="value">{{ user.userName }}</span>
        </div>
        <div class="info-item">
          <span class="label">邮箱:</span>
          <span class="value">{{ user.email }}</span>
        </div>
      </div>
      <div v-else class="no-data">
        <el-empty description="暂无用户数据" />
      </div>
      
      <div class="action-buttons">
        <el-button type="primary" @click="handleEdit">编辑</el-button>
        <el-button type="danger" @click="handleDelete">删除</el-button>
      </div>
    </el-card>
    
    <!-- 编辑用户对话框 -->
    <el-dialog v-model="dialogVisible" title="编辑用户" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.userName" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmEdit">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'UserDetail',
  setup() {
    const store = useStore()
    const route = useRoute()
    const router = useRouter()
    const loading = ref(false)
    const dialogVisible = ref(false)
    
    const userId = computed(() => Number(route.params.id))
    const user = computed(() => store.state.currentUser)
    
    // 编辑表单
    const editForm = reactive({
      id: null,
      userName: '',
      email: ''
    })
    
    // 获取用户详情
    const fetchUserDetail = async () => {
      if (!userId.value) return
      
      loading.value = true
      try {
        await store.dispatch('getUserById', userId.value)
      } catch (error) {
        ElMessage.error('获取用户详情失败')
        console.error(error)
      } finally {
        loading.value = false
      }
    }
    
    // 返回上一页
    const goBack = () => {
      router.push('/')
    }
    
    // 编辑用户
    const handleEdit = () => {
      if (!user.value) return
      
      editForm.id = user.value.id
      editForm.userName = user.value.userName
      editForm.email = user.value.email
      dialogVisible.value = true
    }
    
    // 确认编辑
    const confirmEdit = async () => {
      loading.value = true
      try {
        const result = await store.dispatch('updateUser', editForm)
        if (result.code === 200) {
          ElMessage.success('更新成功')
          dialogVisible.value = false
          fetchUserDetail() // 重新获取用户信息
        } else {
          ElMessage.error(result.message || '更新失败')
        }
      } catch (error) {
        ElMessage.error('更新失败')
        console.error(error)
      } finally {
        loading.value = false
      }
    }
    
    // 删除用户
    const handleDelete = () => {
      if (!user.value) return
      
      ElMessageBox.confirm('确定要删除该用户吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        loading.value = true
        try {
          const result = await store.dispatch('deleteUser', user.value.id)
          if (result.code === 200) {
            ElMessage.success('删除成功')
            router.push('/') // 删除成功后返回列表页
          } else {
            ElMessage.error(result.message || '删除失败')
          }
        } catch (error) {
          ElMessage.error('删除失败')
          console.error(error)
        } finally {
          loading.value = false
        }
      }).catch(() => {
        // 取消删除
      })
    }
    
    // 初始化
    onMounted(() => {
      fetchUserDetail()
    })
    
    return {
      loading,
      user,
      editForm,
      dialogVisible,
      goBack,
      handleEdit,
      confirmEdit,
      handleDelete
    }
  }
}
</script>

<style scoped>
.user-detail-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.user-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-info {
  padding: 20px 0;
}

.info-item {
  margin-bottom: 15px;
  display: flex;
}

.label {
  font-weight: bold;
  width: 100px;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.no-data {
  padding: 40px 0;
  text-align: center;
}
</style> 