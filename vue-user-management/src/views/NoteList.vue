<template>
  <div class="note-list">
    <div class="header">
      <h2>文章管理</h2>
      <el-button type="primary" @click="showAddDialog">添加文章</el-button>
    </div>

    <!-- 搜索筛选 -->
    <div class="search-bar">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="文章标题">
          <el-input v-model="searchForm.noteName" placeholder="请输入文章标题" clearable />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="searchForm.categoryId" placeholder="请选择分类" clearable>
            <el-option
              v-for="category in categories"
              :key="category.categoryId"
              :label="category.categoryName"
              :value="category.categoryId"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchNotes">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 文章列表 -->
    <el-table :data="notes" style="width: 100%" v-loading="loading">
      <el-table-column prop="noteId" label="ID" width="80" />
      <el-table-column prop="noteName" label="文章标题" min-width="200" />
      <el-table-column prop="userName" label="作者" width="120" />
      <el-table-column prop="categoryName" label="分类" width="120" />
      <el-table-column label="标签" width="200">
        <template #default="scope">
          <el-tag
            v-for="tag in scope.row.tags"
            :key="tag.tagId"
            size="small"
            style="margin-right: 5px"
          >
            {{ tag.tagName }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="viewNote(scope.row.noteId)">查看</el-button>
          <el-button size="small" type="primary" @click="editNote(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteNote(scope.row.noteId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :page-sizes="[10, 20, 50, 100]"
        :total="pagination.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 添加/编辑文章对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑文章' : '添加文章'"
      width="60%"
      @close="resetForm"
    >
      <el-form :model="noteForm" :rules="rules" ref="noteFormRef" label-width="100px">
        <el-form-item label="文章标题" prop="noteName">
          <el-input v-model="noteForm.noteName" placeholder="请输入文章标题" />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="noteForm.categoryId" placeholder="请选择分类">
            <el-option
              v-for="category in categories"
              :key="category.categoryId"
              :label="category.categoryName"
              :value="category.categoryId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="noteForm.tagIds" multiple placeholder="请选择标签">
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
            v-model="noteForm.noteContent"
            type="textarea"
            :rows="10"
            placeholder="请输入文章内容"
          />
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
import noteApi from '@/api/note'
import categoryApi from '@/api/category'
import tagApi from '@/api/tag'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'NoteList',
  data() {
    return {
      notes: [],
      categories: [],
      tags: [],
      loading: false,
      dialogVisible: false,
      isEdit: false,
      searchForm: {
        noteName: '',
        categoryId: null
      },
      pagination: {
        current: 1,
        size: 10,
        total: 0
      },
      noteForm: {
        noteId: null,
        noteName: '',
        categoryId: null,
        tagIds: [],
        noteContent: '',
        Id: 1 // 暂时固定为1，实际应该从登录用户获取
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
    this.loadNotes()
    this.loadCategories()
    this.loadTags()
  },
  methods: {
    async loadNotes() {
      this.loading = true
      try {
        const params = {
          current: this.pagination.current,
          size: this.pagination.size,
          ...this.searchForm
        }
        const response = await noteApi.getPageNotes(params)
        this.notes = response.data.records
        this.pagination.total = response.data.total
      } catch (error) {
        ElMessage.error('加载文章列表失败')
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
    searchNotes() {
      this.pagination.current = 1
      this.loadNotes()
    },
    resetSearch() {
      this.searchForm = {
        noteName: '',
        categoryId: null
      }
      this.pagination.current = 1
      this.loadNotes()
    },
    handleSizeChange(val) {
      this.pagination.size = val
      this.loadNotes()
    },
    handleCurrentChange(val) {
      this.pagination.current = val
      this.loadNotes()
    },
    showAddDialog() {
      this.isEdit = false
      this.dialogVisible = true
    },
    viewNote(noteId) {
      this.$router.push(`/note/${noteId}`)
    },
    editNote(note) {
      this.isEdit = true
      this.noteForm = {
        noteId: note.noteId,
        noteName: note.noteName,
        categoryId: note.categoryId,
        tagIds: note.tags.map(tag => tag.tagId),
        noteContent: '', // 需要通过详情接口获取
        Id: note.Id
      }
      this.loadNoteContent(note.noteId)
      this.dialogVisible = true
    },
    async loadNoteContent(noteId) {
      try {
        const response = await noteApi.getNoteById(noteId)
        this.noteForm.noteContent = response.data.data.noteContent
      } catch (error) {
        ElMessage.error('加载文章内容失败')
      }
    },
    async deleteNote(noteId) {
      try {
        await ElMessageBox.confirm('确定要删除这篇文章吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await noteApi.deleteNote(noteId)
        ElMessage.success('删除成功')
        this.loadNotes()
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    },
    async submitForm() {
      try {
        await this.$refs.noteFormRef.validate()
        if (this.isEdit) {
          await noteApi.updateNote(this.noteForm)
          ElMessage.success('更新成功')
        } else {
          await noteApi.addNote(this.noteForm)
          ElMessage.success('添加成功')
        }
        this.dialogVisible = false
        this.loadNotes()
      } catch (error) {
        ElMessage.error(this.isEdit ? '更新失败' : '添加失败')
      }
    },
    resetForm() {
      this.noteForm = {
        noteId: null,
        noteName: '',
        categoryId: null,
        tagIds: [],
        noteContent: '',
        Id: 1
      }
      this.$refs.noteFormRef?.resetFields()
    }
  }
}
</script>

<style scoped>
.note-list {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-bar {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.search-form {
  margin: 0;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}
</style>
