SpringApplication
构造函数
> 实例化了 初始化上下文的各种接口--ApplicationContextInitializer以及监听器--ApplicationListener，
> 要注意的是这里的实例化，并不像平时的Spring Components一样通过注解和扫包完成，
> 而是通过一种不依赖Spring上下文的加载方法，这样才能在Spring完成启动前做各种配置
>
>Spring的解决方法是以接口的全限定名作为key，实现类的全限定名作为value记录在项目的META-INF/spring.factories文件中，然后通过SpringFactoriesLoader工具类提供静态方法进行类加载并缓存下来，spring.factories是Spring Boot的核心配置文件，后面会继续说明。

run方法完成了所有Spring的整个启动过程：准备Environment——发布事件——创建上下文、bean——刷新上下文——结束，其中穿插了很多监听器的动作，并且很多逻辑都是靠各种监听器的实现类执行的，所以在分析run方法之前，先看下各种核心监听器、接口的作用。
