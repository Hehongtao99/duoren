<template>
  <div class="all-users-container">
    <h2>所有用户列表</h2>
    
    <div class="actions">
      <el-button type="primary" @click="refreshUsers">刷新列表</el-button>
      <el-button @click="goBack">返回</el-button>
    </div>
    
    <el-table :data="userList" border style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="userName" label="用户名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="viewUserDetail(scope.row)">查看</el-button>
          <el-button size="small" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
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
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import userApi from '../api/user'

export default {
  name: 'AllUsers',
  setup() {
    const router = useRouter()
    const loading = ref(false)
    const userList = ref([])
    const dialogVisible = ref(false)
    
    // 编辑表单
    const editForm = reactive({
      id: null,
      userName: '',
      email: ''
    })
    
    // 获取所有用户
    const getAllUsers = async () => {
      loading.value = true
      try {
        const response = await userApi.getAllUsers()
        if (response.data.code === 200) {
          userList.value = response.data.data
          ElMessage.success('获取用户列表成功')
        } else {
          ElMessage.error(response.data.message || '获取用户列表失败')
        }
      } catch (error) {
        console.error('获取用户列表失败:', error)
        ElMessage.error('获取用户列表失败')
      } finally {
        loading.value = false
      }
    }
    
    // 刷新用户列表
    const refreshUsers = () => {
      getAllUsers()
    }
    
    // 返回上一页
    const goBack = () => {
      router.push('/')
    }
    
    // 查看用户详情
    const viewUserDetail = (row) => {
      router.push(`/user/${row.id}`)
    }
    
    // 编辑用户
    const handleEdit = (row) => {
      editForm.id = row.id
      editForm.userName = row.userName
      editForm.email = row.email
      dialogVisible.value = true
    }
    
    // 确认编辑
    const confirmEdit = async () => {
      loading.value = true
      try {
        const response = await userApi.updateUser(editForm)
        if (response.data.code === 200) {
          ElMessage.success('更新成功')
          dialogVisible.value = false
          getAllUsers() // 重新获取用户列表
        } else {
          ElMessage.error(response.data.message || '更新失败')
        }
      } catch (error) {
        console.error('更新用户失败:', error)
        ElMessage.error('更新失败')
      } finally {
        loading.value = false
      }
    }
    
    // 删除用户
    const handleDelete = (row) => {
      ElMessageBox.confirm('确定要删除该用户吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        loading.value = true
        try {
          const response = await userApi.deleteUser(row.id)
          if (response.data.code === 200) {
            ElMessage.success('删除成功')
            getAllUsers() // 重新获取用户列表
          } else {
            ElMessage.error(response.data.message || '删除失败')
          }
        } catch (error) {
          console.error('删除用户失败:', error)
          ElMessage.error('删除失败')
        } finally {
          loading.value = false
        }
      }).catch(() => {
        // 取消删除
      })
    }
    
    // 初始化
    onMounted(() => {
      getAllUsers()
    })
    
    return {
      loading,
      userList,
      dialogVisible,
      editForm,
      refreshUsers,
      goBack,
      viewUserDetail,
      handleEdit,
      confirmEdit,
      handleDelete
    }
  }
}
</script>

<style scoped>
.all-users-container {
  padding: 20px;
}

h2 {
  margin-bottom: 20px;
}

.actions {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}
</style> 