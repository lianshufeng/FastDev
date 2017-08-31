# 快速开发框架
#### 基于 Spring mvc 4 的架构 , 真正零配置的快速开发框架。

#### 建议环境: 
    ## JDK 1.8   eclipse   tomcat 8.0 

#### 环境地址：
    jdk: 
        http://www.oracle.com/technetwork/java/javase/downloads/index.html
    eclipse:
        https://www.eclipse.org/downloads/
    tomcat:
        http://tomcat.apache.org/
    git:
        http://git-scm.com/downloads
    TortoiseGit:
        https://tortoisegit.org/
        
        
#### maven镜像 : 
    http://maven.aliyun.com/nexus/content/groups/public/

#### 项目描述：
    
    遵循Spring规范,由maven进行项目管理,将资源(html,css,js,jar 等)打包到jar包中，增量更新仅需替换jar包。
    核心项目：
        PServer(Web环境) + PCore (核心功能块) + PParent (依赖包、字符编码、JDK版本) 
    组件项目：
        Restfull+模版引擎(freemark,groovymark,thymeleaf,velocity)、 JDBC连接池、NoSql(Mongo)等...      
        所有依赖包均在模块内的pom中申明,不耦合核心项目,更多后续还在持续添加中...

#### 例子:
    Example-TemplateMark : 模版引擎
    
    



