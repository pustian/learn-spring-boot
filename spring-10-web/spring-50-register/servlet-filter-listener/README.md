MyServlet
MyFilter
MyListener

配置 
方法1 主启动类上增加注解 
@ServletComponentScan

方法2 使用配置类
WebCoreConfig.java


curl http://localhost:8080/myservlet

遗留问题
servlet filter listener 在javaweb中初始的作用配置