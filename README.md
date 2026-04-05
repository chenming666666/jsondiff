# JSON Diff Visualization

一个基于 Web 的 JSON 文档对比工具，支持可视化高亮显示两个 JSON 文档之间的差异。适用于大型 JSON 文件的比较。

## 功能特性

- **双栏 JSON 输入**：左右两个文本框，分别粘贴原始 JSON 和修改后的 JSON
- **JSON 语法校验**：服务端校验 JSON 格式，提供详细的错误提示
- **JSON 格式化**：一键美化 JSON，自动缩进排版
- **逐行差异对比**：并排显示差异，颜色高亮标识：
  - 绿色背景：新增内容
  - 红色背景：删除内容
  - 无高亮：未变更内容
- **大文件支持**：支持最大 10MB 的 JSON 数据

## 技术栈

### 后端

| 技术 | 版本 |
|------|------|
| Java | 17+ |
| Spring Boot | 3.1.4 |
| Gson | 2.10.1 |
| java-diff-utils | 4.12 |
| Maven | - |

### 前端

| 技术 | 版本 |
|------|------|
| Vue.js | 3.3.4 |
| Vite | 4.4.9 |
| diff | 5.1.0 |

## 快速开始

### 前置要求

- **Java 17+**（推荐通过 Homebrew 安装：`brew install openjdk`）
- **Node.js 16+**
- **Maven 3.6+**（`brew install maven`）

### 一键启动

**macOS / Linux：**

```bash
./start.sh
```

**Windows：**

```cmd
start.bat
```

### 手动启动

```bash
# 终端 1 - 启动后端（端口 8080）
mvn spring-boot:run

# 终端 2 - 启动前端（端口 5173）
cd frontend && npm run dev
```

### 构建运行

```bash
mvn clean package
java -jar target/json-diff-1.0.0.jar
```

启动后访问 http://localhost:5173 使用。

## 项目结构

```
jsondiff/
├── pom.xml                          # Maven 构建配置
├── start.sh                         # macOS/Linux 启动脚本
├── start.bat                        # Windows 启动脚本
├── src/main/java/com/jsondiff/
│   ├── JsonDiffApplication.java     # Spring Boot 入口
│   ├── config/
│   │   └── WebConfig.java           # CORS 配置
│   ├── controller/
│   │   └── JsonDiffController.java  # REST 控制器
│   ├── model/
│   │   ├── JsonFormatRequest.java   # 请求 DTO
│   │   └── JsonFormatResponse.java  # 响应 DTO
│   └── service/
│       ├── JsonParseService.java    # JSON 格式化
│       └── JsonValidationService.java # JSON 校验
├── src/main/resources/
│   └── application.properties       # 应用配置
└── frontend/
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── App.vue                  # 根组件
        ├── components/
        │   ├── Toolbar.vue          # 工具栏
        │   ├── JsonInput.vue        # JSON 输入框
        │   └── JsonDiffViewer.vue   # 差异展示
        └── services/
            └── api.js               # API 请求封装
```

## API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/format` | 校验并格式化两个 JSON 字符串 |

**请求示例：**

```json
{
  "json1": "{\"name\":\"Alice\"}",
  "json2": "{\"name\":\"Bob\",\"age\":30}"
}
```

**响应示例：**

```json
{
  "success": true,
  "formatted1": "{\n  \"name\": \"Alice\"\n}",
  "formatted2": "{\n  \"name\": \"Bob\",\n  \"age\": 30\n}",
  "error": null
}
```

## 配置说明

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| `server.port` | `8080` | 后端端口 |
| `spring.servlet.multipart.max-request-size` | `10MB` | 最大请求大小 |
| `server.connection-timeout` | `30000` | 连接超时（毫秒） |
| `cors.allowed-origins` | `http://localhost:5173` | 允许的前端域名 |

## 常见问题

**启动时报 `ClassNotFoundException`？**

确保已设置 `JAVA_HOME` 环境变量：

```bash
export JAVA_HOME="$(ls -d /opt/homebrew/Cellar/openjdk/*/libexec/openjdk.jdk/Contents/Home | head -1)"
```

**前端启动报 `esbuild` 错误？**

删除 `node_modules` 后重新安装：

```bash
cd frontend && rm -rf node_modules && npm install
```
