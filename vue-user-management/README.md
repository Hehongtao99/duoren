# 用户管理系统前端

## 项目介绍
这是一个基于Vue 3 + Element Plus开发的用户管理系统前端，用于对接后端的用户管理API。

## 功能特性
- 用户列表展示
- 用户分页查询
- 用户详情查看
- 用户信息编辑
- 用户删除

## 技术栈
- Vue 3
- Vuex 4
- Vue Router 4
- Element Plus
- Axios
- Vite (构建工具)

## 项目结构
```
vue-user-management/
├── public/              # 静态资源
├── src/
│   ├── api/             # API接口封装
│   ├── assets/          # 项目资源文件
│   ├── components/      # 通用组件
│   ├── router/          # 路由配置
│   ├── store/           # Vuex状态管理
│   ├── utils/           # 工具函数
│   ├── views/           # 页面组件
│   ├── App.vue          # 根组件
│   └── main.js          # 入口文件
├── index.html           # HTML入口
├── vite.config.js       # Vite配置
└── package.json         # 项目依赖
```

## 启动项目
```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build

# 预览生产版本
npm run preview
```

## 后端API
后端API基于Spring Boot + MyBatis-Plus开发，提供了用户的增删改查功能。 