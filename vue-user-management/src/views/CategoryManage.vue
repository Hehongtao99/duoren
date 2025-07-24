<template>
  <div class="category-manage">
    <div class="header">
      <h2>分类管理</h2>
      <el-button type="primary" @click="showAddDialog">添加分类</el-button>
    </div>

    <!-- 分类列表 -->
    <el-table :data="categories" style="width: 100%" v-loading="loading">
      <el-table-column prop="categoryId" label="ID" width="80" />
      <el-table-column prop="categoryName" label="分类名称" min-width="200" />
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column prop="updateTime" label="更新时间" width="180" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" type="primary" @click="editCategory(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteCategory(scope.row.categoryId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑分类对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑分类' : '添加分类'"
      width="400px"
      @close="resetForm"
    >
      <el-form :model="categoryForm" :rules="rules" ref="categoryFormRef" label-width="100px">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="categoryForm.categoryName" placeholder="请输入分类名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import categoryApi from '@/api/category'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'CategoryManage',
  data() {
    return {
      categories: [],
      loading: false,
      dialogVisible: false,
      isEdit: false,
      categoryForm: {
        categoryId: null,
        categoryName: ''
      },
      rules: {
        categoryName: [
          { required: true, message: '请输入分类名称', trigger: 'blur' },
          { min: 1, max: 50, message: '分类名称长度在 1 到 50 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.loadCategories()
  },
  methods: {
    async loadCategories() {
      this.loading = true
      try {
        const response = await categoryApi.getAllCategories()
        this.categories = response.data.data
      } catch (error) {
        ElMessage.error('加载分类列表失败')
      } finally {
        this.loading = false
      }
    },
    showAddDialog() {
      this.isEdit = false
      this.dialogVisible = true
    },
    editCategory(category) {
      this.isEdit = true
      this.categoryForm = {
        categoryId: category.categoryId,
        categoryName: category.categoryName
      }
      this.dialogVisible = true
    },
    async deleteCategory(categoryId) {
      try {
        await ElMessageBox.confirm('确定要删除这个分类吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await categoryApi.deleteCategory(categoryId)
        ElMessage.success('删除成功')
        this.loadCategories()
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    },
    async submitForm() {
      try {
        await this.$refs.categoryFormRef.validate()
        if (this.isEdit) {
          await categoryApi.updateCategory(this.categoryForm.categoryId, this.categoryForm.categoryName)
          ElMessage.success('更新成功')
        } else {
          await categoryApi.addCategory(this.categoryForm.categoryName)
          ElMessage.success('添加成功')
        }
        this.dialogVisible = false
        this.loadCategories()
      } catch (error) {
        ElMessage.error(this.isEdit ? '更新失败' : '添加失败')
      }
    },
    resetForm() {
      this.categoryForm = {
        categoryId: null,
        categoryName: ''
      }
      this.$refs.categoryFormRef?.resetFields()
    }
  }
}
</script>

<style scoped>
.category-manage {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>
