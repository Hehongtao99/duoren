<template>
  <div id="app">
    <el-container>
      <el-header>
        <div class="header-content">
          <h1>用户管理系统</h1>
          <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            router
          >
            <el-menu-item index="/">用户列表</el-menu-item>
            <el-menu-item index="/all-users">所有用户</el-menu-item>
            <el-menu-item index="/notes">文章管理</el-menu-item>
            <el-menu-item index="/categories">分类管理</el-menu-item>
            <el-menu-item index="/tags">标签管理</el-menu-item>
          </el-menu>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      activeIndex: '/'
    }
  },
  watch: {
    $route(to) {
      this.activeIndex = to.path
    }
  },
  mounted() {
    this.activeIndex = this.$route.path
  },
  methods: {
    handleSelect(key) {
      this.activeIndex = key
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.el-header {
  background-color: #545c64;
  color: #fff;
  padding: 0;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 20px;
}

.header-content h1 {
  margin: 0;
  font-size: 20px;
  color: #fff;
}

.el-menu-demo {
  background-color: transparent;
  border-bottom: none;
}

.el-menu--horizontal > .el-menu-item {
  color: #fff;
  border-bottom: 2px solid transparent;
}

.el-menu--horizontal > .el-menu-item:hover,
.el-menu--horizontal > .el-menu-item.is-active {
  background-color: rgba(255, 255, 255, 0.1);
  color: #fff;
  border-bottom-color: #409eff;
}

.el-main {
  padding: 0;
  background-color: #f5f5f5;
  min-height: calc(100vh - 60px);
}
</style>
