# HyperSpring - 手写微型 IoC 容器
    这是一个基于 Java 注解处理器（Annotation Processing）实现的轻量级依赖注入框架。
    它模仿了 Spring 的核心机制，但使用编译期代码生成替代了运行时反射，实现了零开销的依赖注入。

##  1. 整理项目结构（去噪）
    四个模块
    <modules>
        <module>hyperspring-annotations</module>
        <module>hyperspring-processor</module>
        <module>hyperspring-core</module>
        <module>hyperspring-boot-starter</module>
    </modules>
        
    在上传之前，一定要清理掉编译生成的垃圾文件，否则会被认为不专业。
    删除：所有模块下的 target/ 目录。
    删除：.class 文件。
    保留：src/ 源码和 pom.xml。
    确认：确保 .gitignore 文件里包含了 /target 和 *.class。

## 2.项目的门面 写清楚这个项目的价值
    
### 核心特性
    编译期代码生成：利用 JavaPoet 和 AutoService，在编译阶段自动生成 $$HyperFactory 工厂类。
    高性能：完全摒弃运行时反射，启动速度和运行效率对标原生 Java 代码。
    类型安全：在编译阶段进行依赖检查，将错误拦截在运行之前。
    IoC 容器：实现了 Bean 的生命周期管理、依赖注入（字段注入）。
### 技术栈
    Java 17
    Maven (多模块管理)
    JavaPoet (代码生成)
    AutoService (SPI 注册)
###  快速开始

    安装父工程：mvn clean install
    配置 IDEA：开启 Settings -> Compiler -> Annotation Processors。
    运行 Demo：执行 DemoApp 的 main 方法。

### 为什么写这个项目？
    为了深入理解 Spring Framework 的核心原理，探索元编程在 Java 中的应用，
    以及如何通过编译期处理优化运行时性能。

##  3. 添加开源许可证
    在 GitHub 根目录下创建一个名为 LICENSE 的文件。
    如果你想让别人随便用，选 MIT License。
    如果你想保护你的著作权，选 Apache License 2.0。
    直接在 GitHub 创建仓库时，网页上会提示你选择，点一下就行。

## 4. 提交信息要规范

    不要只写 "update" 或 "fix"。好的提交记录能让看的人觉得你很严谨。
    feat: 实现 HyperContainer 核心容器
    fix: 修复 Maven 版本依赖冲突问题
    refactor: 优化 Processor 代码生成逻辑

## 5. 进阶建议（可选）

    如果你想让它更像一个成熟的开源项目：
    Javadoc：给你的 @HyperComponent 和 HyperContainer 加上简单的注释。
    截图：截一张 IDEA 控制台成功运行的图，放在 README 里，证明它是真的能跑起来的。

