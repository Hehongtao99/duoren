<template>
  <div class="all-users">
    <div class="header">
      <h2>所有用户</h2>
      <div class="actions">
        <el-button type="primary" @click="showAddDialog">添加用户</el-button>
        <el-button @click="goBack">返回</el-button>
      </div>
    </div>
    
    <el-table :data="userList" border style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="userName" label="用户名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="address" label="地址" />
      <el-table-column prop="age" label="年龄" width="80" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="viewUserDetail(scope.row)">查看</el-button>
          <el-button size="small" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加用户对话框 -->
    <el-dialog v-model="dialogVisible" title="添加用户" width="500px">
      <el-form :model="addForm" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="addForm.userName" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="addForm.email" />
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="addForm.address" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model="addForm.age" :min="1" :max="150" />
        </el-form-item>
        <el-form-item label="身高(cm)">
          <el-input-number v-model="addForm.heightCm" :precision="2" :step="0.1" />
        </el-form-item>
        <el-form-item label="体重(kg)">
          <el-input-number v-model="addForm.weightKg" :precision="2" :step="0.1" />
        </el-form-item>
        <el-form-item label="GPA">
          <el-input-number v-model="addForm.gpa" :precision="2" :step="0.01" :min="0" :max="4" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleAdd">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑用户对话框 -->
    <el-dialog v-model="editDialogVisible" title="编辑用户" width="500px">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.userName" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" />
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="editForm.address" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model="editForm.age" :min="1" :max="150" />
        </el-form-item>
        <el-form-item label="身高(cm)">
          <el-input-number v-model="editForm.heightCm" :precision="2" :step="0.1" />
        </el-form-item>
        <el-form-item label="体重(kg)">
          <el-input-number v-model="editForm.weightKg" :precision="2" :step="0.1" />
        </el-form-item>
        <el-form-item label="GPA">
          <el-input-number v-model="editForm.gpa" :precision="2" :step="0.01" :min="0" :max="4" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleUpdate">确定</el-button>
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
    const editDialogVisible = ref(false)

    // 添加表单
    const addForm = reactive({
      userName: '',
      email: '',
      address: '',
      age: null,
      heightCm: null,
      weightKg: null,
      gpa: null
    })

    // 编辑表单
    const editForm = reactive({
      Id: null,
      userName: '',
      email: '',
      address: '',
      age: null,
      heightCm: null,
      weightKg: null,
      gpa: null
    })

    // 获取所有用户
    const getAllUsers = async () => {
      loading.value = true
      try {
        const response = await userApi.getAllUsers()
        console.log('API响应数据:', response.data)
        if (response.data.code === 200) {
          userList.value = response.data.data
          console.log('设置后的userList:', userList.value)
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

    // 查看用户详情
    const viewUserDetail = (row) => {
      console.log('查看用户详情，row:', row, 'row.id:', row.id)
      router.push(`/user/${row.id}`)
    }

    // 显示添加对话框
    const showAddDialog = () => {
      dialogVisible.value = true
    }

    // 处理添加用户
    const handleAdd = async () => {
      loading.value = true
      try {
        const response = await userApi.addUser(addForm)
        if (response.data.code === 200) {
          ElMessage.success('添加成功')
          dialogVisible.value = false
          getAllUsers() // 重新获取用户列表
          // 重置表单
          Object.keys(addForm).forEach(key => {
            addForm[key] = key === 'age' || key === 'heightCm' || key === 'weightKg' || key === 'gpa' ? null : ''
          })
        } else {
          ElMessage.error(response.data.message || '添加失败')
        }
      } catch (error) {
        console.error('添加用户失败:', error)
        ElMessage.error('添加失败')
      } finally {
        loading.value = false
      }
    }

    // 处理编辑
    const handleEdit = (row) => {
      console.log('编辑用户，row:', row, 'row.id:', row.id)
      editForm.Id = row.id
      editForm.userName = row.userName
      editForm.email = row.email
      editForm.address = row.address
      editForm.age = row.age
      editForm.heightCm = row.heightCm
      editForm.weightKg = row.weightKg
      editForm.gpa = row.gpa
      editDialogVisible.value = true
    }

    // 处理更新用户
    const handleUpdate = async () => {
      loading.value = true
      try {
        const response = await userApi.updateUser(editForm)
        if (response.data.code === 200) {
          ElMessage.success('更新成功')
          editDialogVisible.value = false
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
      console.log('准备删除用户，row对象:', row, 'row.id:', row.id)
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
        ElMessage.info('已取消删除')
      })
    }

    // 返回
    const goBack = () => {
      router.go(-1)
    }

    // 组件挂载时获取用户列表
    onMounted(() => {
      getAllUsers()
    })

    return {
      loading,
      userList,
      dialogVisible,
      editDialogVisible,
      addForm,
      editForm,
      getAllUsers,
      viewUserDetail,
      showAddDialog,
      handleAdd,
      handleEdit,
      handleUpdate,
      handleDelete,
      goBack
    }
  }
}
</script>

<style scoped>
.all-users {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.actions {
  display: flex;
  gap: 10px;
}

.dialog-footer {
  text-align: right;
}
</style>
