<template>
  <div class="user-list-container">
    <div class="header-actions">
      <h2>用户管理</h2>
      <el-button type="primary" @click="goToAllUsers">查看所有用户</el-button>
    </div>

    <div class="search-container">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="用户名">
          <el-input v-model="searchForm.userName" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="searchForm.email" placeholder="请输入邮箱" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table :data="userList" border style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="userName" label="用户名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :page-size="pageSize"
        :current-page="currentPage"
        :page-sizes="[10, 20, 50, 100]"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

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
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'UserList',
  setup() {
    const store = useStore()
    const router = useRouter()
    const loading = ref(false)
    const dialogVisible = ref(false)
    
    // 搜索表单
    const searchForm = reactive({
      userName: '',
      email: ''
    })
    
    // 编辑表单
    const editForm = reactive({
      id: null,
      userName: '',
      email: ''
    })
    
    // 从store获取数据
    const userList = computed(() => store.state.users)
    const total = computed(() => store.state.totalUsers)
    const currentPage = computed(() => store.state.currentPage)
    const pageSize = computed(() => store.state.pageSize)
    
    // 加载用户列表
    const loadUserList = async () => {
      loading.value = true
      try {
        await store.dispatch('getPageUsers', {
          current: currentPage.value,
          size: pageSize.value,
          userName: searchForm.userName,
          email: searchForm.email
        })
      } catch (error) {
        ElMessage.error('获取用户列表失败')
        console.error(error)
      } finally {
        loading.value = false
      }
    }
    
    // 跳转到所有用户页面
    const goToAllUsers = () => {
      router.push('/all-users')
    }
    
    // 搜索
    const handleSearch = () => {
      store.commit('SET_CURRENT_PAGE', 1) // 重置页码
      loadUserList()
    }
    
    // 重置搜索
    const resetSearch = () => {
      searchForm.userName = ''
      searchForm.email = ''
      handleSearch()
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
        const result = await store.dispatch('updateUser', editForm)
        if (result.code === 200) {
          ElMessage.success('更新成功')
          dialogVisible.value = false
          loadUserList()
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
    const handleDelete = (row) => {
      ElMessageBox.confirm('确定要删除该用户吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        loading.value = true
        try {
          const result = await store.dispatch('deleteUser', row.id)
          if (result.code === 200) {
            ElMessage.success('删除成功')
            loadUserList()
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
    
    // 分页相关
    const handleSizeChange = (val) => {
      store.commit('SET_PAGE_SIZE', val)
      loadUserList()
    }
    
    const handleCurrentChange = (val) => {
      store.commit('SET_CURRENT_PAGE', val)
      loadUserList()
    }
    
    // 初始化
    onMounted(() => {
      loadUserList()
    })
    
    return {
      loading,
      userList,
      total,
      currentPage,
      pageSize,
      searchForm,
      editForm,
      dialogVisible,
      goToAllUsers,
      handleSearch,
      resetSearch,
      handleEdit,
      confirmEdit,
      handleDelete,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.user-list-container {
  padding: 20px;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-container {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style> 