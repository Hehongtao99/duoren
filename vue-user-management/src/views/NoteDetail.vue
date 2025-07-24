<template>
  <div class="note-detail" v-loading="loading">
    <div class="header">
      <el-button @click="goBack" icon="ArrowLeft">返回</el-button>
      <div class="actions">
        <el-button type="primary" @click="editNote">编辑</el-button>
        <el-button type="danger" @click="deleteNote">删除</el-button>
      </div>
    </div>

    <div class="content" v-if="note">
      <div class="title">
        <h1>{{ note.noteName }}</h1>
      </div>

      <div class="meta">
        <div class="meta-item">
          <span class="label">作者：</span>
          <span class="value">{{ note.userName }}</span>
        </div>
        <div class="meta-item">
          <span class="label">分类：</span>
          <span class="value">{{ note.categoryName }}</span>
        </div>
        <div class="meta-item">
          <span class="label">标签：</span>
          <div class="tags">
            <el-tag
              v-for="tag in note.tags"
              :key="tag.tagId"
              size="small"
              style="margin-right: 5px"
            >
              {{ tag.tagName }}
            </el-tag>
          </div>
        </div>
        <div class="meta-item">
          <span class="label">创建时间：</span>
          <span class="value">{{ formatDate(note.createTime) }}</span>
        </div>
        <div class="meta-item">
          <span class="label">更新时间：</span>
          <span class="value">{{ formatDate(note.updateTime) }}</span>
        </div>
      </div>

      <div class="note-content">
        <div class="content-text">
          {{ note.noteContent }}
        </div>
      </div>
    </div>

    <!-- 编辑对话框 -->
    <el-dialog
      v-model="editDialogVisible"
      title="编辑文章"
      width="60%"
      @close="resetEditForm"
    >
      <el-form :model="editForm" :rules="rules" ref="editFormRef" label-width="100px">
        <el-form-item label="文章标题" prop="noteName">
          <el-input v-model="editForm.noteName" placeholder="请输入文章标题" />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="editForm.categoryId" placeholder="请选择分类">
            <el-option
              v-for="category in categories"
              :key="category.categoryId"
              :label="category.categoryName"
              :value="category.categoryId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="editForm.tagIds" multiple placeholder="请选择标签">
            <el-option
              v-for="tag in tags"
              :key="tag.tagId"
              :label="tag.tagName"
              :value="tag.tagId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="文章内容" prop="noteContent">
          <el-input
            v-model="editForm.noteContent"
            type="textarea"
            :rows="10"
            placeholder="请输入文章内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitEdit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import noteApi from '@/api/note'
import categoryApi from '@/api/category'
import tagApi from '@/api/tag'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'NoteDetail',
  data() {
    return {
      note: null,
      categories: [],
      tags: [],
      loading: false,
      editDialogVisible: false,
      editForm: {
        noteId: null,
        noteName: '',
        categoryId: null,
        tagIds: [],
        noteContent: ''
      },
      rules: {
        noteName: [
          { required: true, message: '请输入文章标题', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '请选择分类', trigger: 'change' }
        ],
        noteContent: [
          { required: true, message: '请输入文章内容', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.loadNote()
    this.loadCategories()
    this.loadTags()
  },
  methods: {
    async loadNote() {
      this.loading = true
      try {
        const noteId = this.$route.params.id
        const response = await noteApi.getNoteById(noteId)
        this.note = response.data.data
      } catch (error) {
        ElMessage.error('加载文章详情失败')
        this.goBack()
      } finally {
        this.loading = false
      }
    },
    async loadCategories() {
      try {
        const response = await categoryApi.getAllCategories()
        this.categories = response.data.data
      } catch (error) {
        ElMessage.error('加载分类列表失败')
      }
    },
    async loadTags() {
      try {
        const response = await tagApi.getAllTags()
        this.tags = response.data.data
      } catch (error) {
        ElMessage.error('加载标签列表失败')
      }
    },
    goBack() {
      this.$router.go(-1)
    },
    editNote() {
      this.editForm = {
        noteId: this.note.noteId,
        noteName: this.note.noteName,
        categoryId: this.note.categoryId,
        tagIds: this.note.tags.map(tag => tag.tagId),
        noteContent: this.note.noteContent
      }
      this.editDialogVisible = true
    },
    async deleteNote() {
      try {
        await ElMessageBox.confirm('确定要删除这篇文章吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await noteApi.deleteNote(this.note.noteId)
        ElMessage.success('删除成功')
        this.$router.push('/notes')
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    },
    async submitEdit() {
      try {
        await this.$refs.editFormRef.validate()
        await noteApi.updateNote(this.editForm)
        ElMessage.success('更新成功')
        this.editDialogVisible = false
        this.loadNote()
      } catch (error) {
        ElMessage.error('更新失败')
      }
    },
    resetEditForm() {
      this.editForm = {
        noteId: null,
        noteName: '',
        categoryId: null,
        tagIds: [],
        noteContent: ''
      }
      this.$refs.editFormRef?.resetFields()
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleString('zh-CN')
    }
  }
}
</script>

<style scoped>
.note-detail {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.title {
  margin-bottom: 20px;
}

.title h1 {
  margin: 0;
  color: #333;
  font-size: 28px;
  font-weight: 600;
}

.meta {
  margin-bottom: 30px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.meta-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.meta-item:last-child {
  margin-bottom: 0;
}

.label {
  font-weight: 600;
  color: #666;
  min-width: 80px;
}

.value {
  color: #333;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
}

.note-content {
  background-color: #fff;
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 30px;
}

.content-text {
  line-height: 1.8;
  font-size: 16px;
  color: #333;
  white-space: pre-wrap;
}

.dialog-footer {
  text-align: right;
}
</style>
