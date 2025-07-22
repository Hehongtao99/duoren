<template>
  <div class="app-container">
    <el-container>
      <el-header>
        <h1>用户管理系统</h1>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
      <el-footer>
        <p>© 2023 用户管理系统</p>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
.app-container {
  height: 100vh;
  width: 100%;
}

.el-header {
  background-color: #409EFF;
  color: white;
  display: flex;
  align-items: center;
}

.el-footer {
  background-color: #f5f5f5;
  color: #666;
  text-align: center;
  padding: 20px 0;
}

body {
  margin: 0;
  padding: 0;
  font-family: Arial, sans-serif;
}
</style> 