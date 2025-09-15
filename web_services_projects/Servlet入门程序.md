
# Servlet 入门实践：HelloServlet

本文档将指导你在 **IntelliJ IDEA** 中使用 **Tomcat + Maven** 搭建 Servlet 项目，并实现一个最简单的 `HelloServlet` 程序。我们会用两种方式实现：

* 方式一：通过 **web.xml 配置 Servlet**
* 方式二：通过 **@WebServlet 注解配置 Servlet**

---

## 一、开发环境准备

1. **安装 JDK**

   * 推荐 JDK 8以上。
   * 配置 `JAVA_HOME` 和 `PATH`。

2. **安装 Tomcat**

   * 下载 Apache Tomcat（推荐 9.x 或 10.x版本）。
   * 解压后记住目录，比如 `D:\apache-tomcat-9.0.85`。

3. **安装 IntelliJ IDEA**（推荐 Ultimate 版，因为内置支持 Tomcat）。

4. **Maven**

   * IDEA 通常自带 Maven，可以直接用。
   * 如果要用本地安装的 Maven，需要配置 `MAVEN_HOME`。

---

## 二、创建 Maven Web 项目

在 IDEA 中操作：

1. 新建项目 `File -> New -> Project`。

2. 选择 **Maven**，勾选 `Create from archetype`，选择：

   ```
   org.apache.maven.archetypes:maven-archetype-webapp
   ```

3. 填写：

   * GroupId: `com.example`
   * ArtifactId: `HelloServletDemo`

4. 创建完成后，项目结构大致如下：

```
HelloServletDemo
│── src
│   ├── main
│   │   ├── java
│   │   └── webapp
│   │       └── WEB-INF
│   │           └── web.xml
│── pom.xml
```

---

## 三、配置 `pom.xml`和JSP页面

1. 在 `pom.xml` 中添加 **Servlet API 依赖**：

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>HelloServletDemo</artifactId>
    <packaging>war</packaging>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- Servlet API -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>HelloServletDemo</finalName>
    </build>
</project>

```
2. 修改欢迎页webapp目录下的index.jsp页面的代码：
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <meta charset="UTF-8">
    <body>
        <h2>Welcome to Servlet Demo</h2>
            <a href="hello">web.xml 方式</a>
            <p> <a href="hello2">注解方式</a> </p>
    </body>
</html>
```
---

## 四、方式一：基于 `web.xml` 配置 Servlet

### 1. 新建 `HelloServlet.java`

在 `src/main/java/com/example/` 下创建：

```java
package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<h1>Hello, Servlet! (Based on web.xml) I am QiQiao </h1>");
    }
}
```

### 2. 修改 `web.xml`

在 `src/main/webapp/WEB-INF/web.xml` 中配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
         http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

    <!-- Servlet 定义 -->
    <servlet>
        <servlet-name>HelloServlet</servlet-name>
        <servlet-class>com.example.HelloServlet</servlet-class>
    </servlet>

    <!-- Servlet 映射 -->
    <servlet-mapping>
        <servlet-name>HelloServlet</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>

</web-app>
```

### 3. 启动后访问

部署到 Tomcat 后，访问：

```
http://localhost:8080/HelloServletDemo/hello
```

页面显示：

```
Hello, Servlet! (web.xml 配置)
```

---

## 五、方式二：基于 @WebServlet 注解配置

### 1. 新建 `HelloServletAnnotation.java`

在 `src/main/java/com/example/` 下创建：

```java
package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HelloServletAnnotation", urlPatterns = "/hello2")
public class HelloServletAnnotation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<h1>Hello, Servlet! (Based on @WebServlet) I am QiQiao</h1>");
    }
}
```

### 2. 不需要改 `web.xml`

因为注解方式自动生效。

### 3. 启动后访问

部署到 Tomcat 后，访问：

```
http://localhost:8080/HelloServletDemo/hello2
```

页面显示：

```
Hello, Servlet! (@WebServlet 注解配置)
```


---
## 六、部署到 Tomcat

1. 在 IDEA 中：

   * `File -> Settings -> Application Servers -> 添加 Tomcat`，选择 Tomcat 安装目录。
   * 在 `Run -> Edit Configurations` 里添加 **Tomcat Server -> Local**。
   * 设置 `Deployment`，选择 `Artifact -> HelloServletDemo:war exploded`。

2. 启动 Tomcat，访问前面两个 URL 验证。

---

## 七、总结

* **web.xml 配置方式** 更传统，兼容旧项目。
* **@WebServlet 注解方式** 更简洁，现代项目常用。


