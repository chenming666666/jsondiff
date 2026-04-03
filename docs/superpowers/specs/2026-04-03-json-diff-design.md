# JSON Diff Visualization - 设计文档

## 项目概述

构建一个JavaWeb项目，实现JSON对比功能，能够处理大JSON文件并提供可视化的差异对比展示。

## 技术选型

### 后端技术栈
- **框架**: Spring Boot 3.x + Maven
- **JSON解析**: Google Gson
- **差异对比**: java-diff-utils
- **运行环境**: JDK 17+

### 前端技术栈
- **框架**: Vue.js 3.x
- **diff库**: jsondiff (npm package)
- **打包工具**: Vite

### 整体架构选择
方案三：**前后端混合处理**
- 后端：负责JSON格式验证、流式解析、格式化，处理大JSON避免前端内存问题
- 前端：负责diff计算、可视化展示、用户交互

## 项目结构

### 后端结构
```
json-diff/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/jsondiff/
        │   ├── JsonDiffApplication.java      # Spring Boot启动类
        │   ├── controller/
        │   │   └── JsonDiffController.java  # REST API控制器
        │   ├── service/
        │   │   ├── JsonParseService.java    # JSON解析格式化服务
        │   │   └── JsonValidationService.java # JSON验证服务
        │   ├── model/
        │   │   └── JsonFormatRequest.java   # 请求模型
        │   │   └── JsonFormatResponse.java  # 响应模型
        │   └── config/
        │       └── WebConfig.java            # CORS跨域配置
        └── resources/
            └── application.properties        # Spring Boot配置
```

### 前端结构
```
json-diff/frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── App.vue             # 根组件
    ├── main.js             # 入口文件
    ├── components/
    │   ├── JsonInput.vue   # JSON输入组件（双栏输入）
    │   ├── JsonDiffViewer.vue  # 差异展示组件
    │   └── Toolbar.vue     # 工具栏组件
    ├── services/
    │   └── api.js          # 后端API调用封装
    └── style.css           # 全局样式
```

## API 设计

### POST /api/format
格式化JSON，验证JSON格式是否正确

**请求体**:
```json
{
  "json1": "string",
  "json2": "string"
}
```

**响应**:
```json
{
  "success": true,
  "formatted1": "string",
  "formatted2": "string",
  "error": "string | null"
}
```

## 大JSON处理策略

针对支持**1-10MB**级别JSON文件：

1. **流式解析**: 使用Gson的JsonReader进行流式解析，避免一次性加载整个JSON到内存
2. **增量格式化**: 边解析边生成格式化输出，减少内存占用
3. **大小限制**: 配置最大10MB请求大小限制，防止OOM
4. **错误处理**: 提前验证JSON格式，给出清晰错误提示

## 前端功能设计

### 核心功能
1. **双栏输入**: 左右两栏分别输入/粘贴JSON文本
2. **对比按钮**: 点击后调用后端格式化，前端计算diff并展示
3. **差异高亮**:
   - 新增：绿色背景高亮
   - 删除：红色背景高亮
   - 修改：黄色背景高亮
4. **格式化展示**: 保持JSON缩进结构，便于阅读
5. **清空按钮**: 一键清空两个输入框

### UI布局
- 上半部分：工具栏（对比按钮、清空按钮）
- 中间部分：双栏输入区域，等宽分布
- 下半部分：差异对比展示区域，并排展示两个JSON的差异结果

## 核心依赖

### 后端Maven依赖
```xml
<!-- Spring Boot Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
</dependency>

<!-- Java Diff Utils -->
<dependency>
    <groupId>io.github.java-diff-utils</groupId>
    <artifactId>java-diff-utils</artifactId>
    <version>4.12</version>
</dependency>
```

### 前端npm依赖
```json
"dependencies": {
  "vue": "^3.3.4",
  "jsondiffpatch": "^0.6.0"
},
"devDependencies": {
  "@vitejs/plugin-vue": "^4.3.4",
  "vite": "^4.4.9"
}
```

## 配置

### application.properties
```properties
# 服务器端口
server.port=8080

# 最大请求大小
spring.servlet.multipart.max-request-size=10MB
spring.servlet.multipart.max-file-size=10MB

# CORS允许的源
cors.allowed-origins=http://localhost:5173
```
