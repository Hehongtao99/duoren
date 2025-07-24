<template>
  <div class="tag-manage">
    <div class="header">
      <h2>标签管理</h2>
      <el-button type="primary" @click="showAddDialog">添加标签</el-button>
    </div>

    <!-- 标签列表 -->
    <el-table :data="tags" style="width: 100%" v-loading="loading">
      <el-table-column prop="tagId" label="ID" width="80" />
      <el-table-column prop="tagName" label="标签名称" min-width="200" />
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column prop="updateTime" label="更新时间" width="180" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" type="primary" @click="editTag(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteTag(scope.row.tagId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑标签对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑标签' : '添加标签'"
      width="400px"
      @close="resetForm"
    >
      <el-form :model="tagForm" :rules="rules" ref="tagFormRef" label-width="100px">
        <el-form-item label="标签名称" prop="tagName">
          <el-input v-model="tagForm.tagName" placeholder="请输入标签名称" />
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
import tagApi from '@/api/tag'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'TagManage',
  data() {
    return {
      tags: [],
      loading: false,
      dialogVisible: false,
      isEdit: false,
      tagForm: {
        tagId: null,
        tagName: ''
      },
      rules: {
        tagName: [
          { required: true, message: '请输入标签名称', trigger: 'blur' },
          { min: 1, max: 20, message: '标签名称长度在 1 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.loadTags()
  },
  methods: {
    async loadTags() {
      this.loading = true
      try {
        const response = await tagApi.getAllTags()
        this.tags = response.data.data
      } catch (error) {
        ElMessage.error('加载标签列表失败')
      } finally {
        this.loading = false
      }
    },
    showAddDialog() {
      this.isEdit = false
      this.dialogVisible = true
    },
    editTag(tag) {
      this.isEdit = true
      this.tagForm = {
        tagId: tag.tagId,
        tagName: tag.tagName
      }
      this.dialogVisible = true
    },
    async deleteTag(tagId) {
      try {
        await ElMessageBox.confirm('确定要删除这个标签吗？删除后相关文章的标签关联也会被删除。', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await tagApi.deleteTag(tagId)
        ElMessage.success('删除成功')
        this.loadTags()
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    },
    async submitForm() {
      try {
        await this.$refs.tagFormRef.validate()
        if (this.isEdit) {
          await tagApi.updateTag(this.tagForm.tagId, this.tagForm.tagName)
          ElMessage.success('更新成功')
        } else {
          await tagApi.addTag(this.tagForm.tagName)
          ElMessage.success('添加成功')
        }
        this.dialogVisible = false
        this.loadTags()
      } catch (error) {
        ElMessage.error(this.isEdit ? '更新失败' : '添加失败')
      }
    },
    resetForm() {
      this.tagForm = {
        tagId: null,
        tagName: ''
      }
      this.$refs.tagFormRef?.resetFields()
    }
  }
}
</script>

<style scoped>
.tag-manage {
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
