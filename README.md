# gfc-draw

gfc-draw 是一个基础图形绘画软件，旨在提供一个简单易用的图形绘制界面，允许用户通过图形用户界面选择和绘制各种基本图形，包括三角形、方框、圆形、椭圆和连接线等。此外，软件还支持添加文字描述、图形的拷贝复制、图形组合以及撤销操作等功能。

## 功能特点

- **图形绘制**：支持绘制三角形、方框、圆形、椭圆和连接线等基本图形。
- **文字描述**：允许用户为图形添加文字描述。
- **图形操作**：支持单击选中图形、拷贝复制和图形组合等操作。
- **撤销功能**：提供撤销上一步操作的功能。
- **个性化设置**：支持个性化界面设置，包括字体大小和界面皮肤等。
- **图形调整**：支持通过拖拽调整图形和组合图形的大小。
- **多步撤销**：支持撤销多步操作。
- **图形存储**：使用json存储格式，可以保存和加载用户绘制的图形。

## 开始使用

### 环境要求

- Java 1.8 或更高版本
- Maven 3.6.0 或更高版本

### 构建项目

克隆项目到本地：

```bash
git clone https://github.com/your-repository/gfc-draw.git
```

进入项目目录：

```bash
cd gfc-draw
```

使用 Maven 构建项目：

```bash
mvn clean install
```

构建完成后，运行以下命令启动软件：

```bash
java -cp target/classes:$(mvn dependency:build-classpath | grep -v INFO) nju.gfcat.Applicationjava -jar target/gfc-draw-1.0-SNAPSHOT.jar
```
