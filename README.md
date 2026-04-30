# HyperSpring
HyperSpring 是一个轻量级、手写实现的微型 IoC（控制反转）容器。它旨在通过极简的代码复刻 Spring 框架的核心设计模式，帮助开发者深入理解依赖注入（DI）、控制反转（IoC）以及 AOP（面向切面编程）的底层原理。
该项目不包含 Spring 的复杂配置和冗余功能，仅保留最核心的容器实现，是学习框架设计和 Java 反射机制的绝佳实践。
## 核心特性

    ● ** 轻量级核心**：零第三方依赖，纯原生 Java 实现，代码精简，易于阅读和调试。
    ● ** 依赖注入 (DI)**：支持基于注解（Annotation）的自动装配，包括构造器注入和字段注入。
    ● ** Bean 容器管理**：实现了 Bean 的生命周期管理，支持单例（Singleton）和作用域配置。
    ● ** 自动扫描机制**：支持指定包路径扫描，自动识别 @Component、@Service 等组件并注册到容器。
    ● ** 高性能反射**：利用 Java 反射 API 高效解析类结构，实现对象的动态实例化。
## 技术栈
    
    ● 语言：Java 17
    ● 核心技术：Java Reflection, Java Annotations, File I/O
    ● 构建工具：Maven / Gradle
## 快速开始

    1. 定义组件
    使用 @Component 或自定义注解标记你的类：
    代码

        @Component
        public class UserService {
            private final UserRepository userRepository;
        
            // 支持构造器注入
            public UserService(UserRepository userRepository) {
                this.userRepository = userRepository;
            }
        
            public void printUser() {
                System.out.println("User: " + userRepository.findById(1));
            }
        }

        2. 启动容器
        在你的主程序中初始化 HyperSpring 容器：
        代码

        public class Application {
            public static void main(String[] args) {
                // 初始化容器，指定扫描的包路径
                ApplicationContext context = new HyperSpringApplicationContext("com.example");
        
                // 获取 Bean
                UserService userService = context.getBean(UserService.class);
                
                // 使用 Bean
                userService.printUser();
            }
        }

## 架构设计
    HyperSpring 的核心架构主要由以下几个部分组成：
    1. BeanDefinition：用于描述 Bean 的元数据（类名、作用域、依赖关系）。
    2. BeanFactory：负责 Bean 的实例化、配置和组装。
    3. ApplicationContext：提供高层访问接口，负责包扫描和 BeanDefinition 的加载。
    项目结构
    代码
    
    HyperSpring/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com.hyperspring/
    │   │   │       ├── core/          # 核心容器实现
    │   │   │       ├── annotation/    # 自定义注解定义
    │   │   │       └── util/          # 反射与扫描工具类
    ├── pom.xml
    └── README.md
    
    许可证
    本项目采用 MIT 许可证。
