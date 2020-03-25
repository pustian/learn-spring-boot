# yaml 

### 基本语法

>+ key: value 表示键值对
>+ 使用缩进表示层级关系
>  - 缩进不允许使用tab，只允许空格
>  - 缩进的空格数不重要，只要相同层级的元素左对齐即可
>+ 大小写敏感
>+ '#'表示注释
>+ 字符串不用加引号，双引号引用不转义， 单引号字符串转义以普通字符串输出。
>  +  "zhansan \n lisi" 输出 ：张三 有个换行 李四

### 对象

键值对形式表示

```yaml
person:
	firstName: pusen
	lastName: tian
	height: 180
	weight: 75
```

### 数组

```yaml
pet:
   - cat
   - dog
   - bird
```



### 纯量
> 纯量是最基本的，不可再分的值，包括：
字符串

```yaml
string:
    - 哈哈
    - 'Hello world'  #可以使用双引号或者单引号包裹特殊字符
    - newline
      newline2    #字符串可以拆成多行，每一行会被转化成一个空格
```

布尔值

```yaml
boolean: 
    - TRUE  #true,True都可以
    - FALSE  #false，False都可以
```

整数，浮点数

```yaml
float:
    - 3.14
    - 6.8523015e+5  #可以使用科学计数法
int:
    - 123
    - 0b1010_0111_0100_1010_1110    #二进制表示
```

Null

```yaml
parent: ~  #使用~表示null
```

时间，日期

```yaml
date:
    - 2018-02-17    #日期必须使用ISO 8601格式，即yyyy-MM-dd
datetime: 
    - 2018-02-17T15:02:31+08:00    #时间使用ISO 8601格式，时间和日期之间使用T连接，最后使用+代表时区
```
### 引用

> & 锚点和 * 别名，可以用来引用:
> & 用来建立锚点（defaults），<< 表示合并到当前数据，* 用来引用锚点
```yaml
defaults: &defaults
  adapter:  postgres
  host:     localhost

development:
  database: myapp_development
  <<: *defaults

test:
  database: myapp_test
  <<: *defaults
```
同义于
```yaml
defaults:
  adapter:  postgres
  host:     localhost

development:
  database: myapp_development
  adapter:  postgres
  host:     localhost

test:
  database: myapp_test
  adapter:  postgres
  host:     localhost
```

# Spring boot中引用yaml
### 方法一 application.yml 中追加
直接再application.yml 中增加描述例如
#### application.yml
```yaml
person:
    name: 田圃森
    birthday: 1982/12/06
    weight: 75
    height: 180
    hobbies:
        - basketball
        - novel
    labels:
        profile: nice
        work: hard
    computer:
        cpus: 4
        mem: 8g
    computers:
        -   cpus: 2
            mem: 16g
        -   cpus: 4
            mem: 32g
        -   cpus: 1
            mem: 1g
```
#### Person.java 
```java
@Component
@ConfigurationProperties(prefix = "person")// 接受application.yml中前缀为 person 下面的属性
public class Person {
    private String name;
    private Date birthday;
    private Integer weight;
    private Integer height;
    private List<String> hobbies;
    private Map<String, String> labels;
    private List<Computer> computers;
    // getter/setter 
}

```
#### Computer.java
```java
public class Computer{
    private Integer cpus;
    private String mem;
    // getter/setter
}
```
#### 主方法类
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
#### 测试方法类
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    Person person;
    @Test
    public void contextLoads() {
        System.out.println(person);
    }
}
```
### 方法二 独立的properties
> 不支持新增独立的yml配置文件
#### person.properties 和application.yml 同样的目录
> 同方法一中的内容
```properties
person.name=田圃森
person.birthday=1982/12/06
person.weight=75
person.height=180
person.hobbies[0]=basketball
person.hobbies[1]=novel
person.labels[profile]=nice
person.labels[work]=hard
person.computers[0].cpus=2
person.computers[0].mem=16g
person.computers[1].cpus=8
person.computers[1].mem=32g
```
#### Person.java
> 与方法一比较，Person上新增一行注解
> ```java
>@PropertySource(value = "classpath:person.properties") // 自定义配置文件目前只支持properties方式
> ```
具体如下
```java
@Component
@ConfigurationProperties(prefix = "person")// 接受application.yml中前缀为 person 下面的属性
@PropertySource(value = "classpath:person.properties") // 自定义配置文件目前只支持properties方式
public class Person {
    private String name;
    private Date birthday;
    private Integer weight;
    private Integer height;
    private List<String> hobbies;
    private Map<String, String> labels;
    private List<Computer> computers;
    // getter/setter 
}

```
#### Computer.java
> 同方法一
```java
public class Computer{
    private Integer cpus;
    private String mem;
    // getter/setter
}
```
#### 主方法类
> 同方法一
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
#### 测试方法类
> 同方法一
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    Person person;
    @Test
    public void contextLoads() {
        System.out.println(person);
    }
}
```
#### pom.xml
> 在2.x 版本中不需要添加以下代码
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
```
#### 解决中文乱码问题
汉语可以用jdk自带工具 native2ascii 转换后使用
```bash
[C:\~]$ native2ascii
田圃森
\u7530\u5703\u68ee
# 此处田圃森为输入。 输出修改properties中的汉字即可
```
松散绑定
> birth_day birthday birth-day 都可以匹配
# FQA:
> application.properties和application.yaml文件 同时配置 application.properties 中生效

# Value
```java
@Value("${xxxx}")
private String xx;
```

>  使用见 HelloController.java

# value-configuration

| |@Configuration|@Value| |
|---|---|---|---|
|功能|批量注入配置文件的属性|单个指定|
|松散绑定|支持|不支持| |
|SpEL|不支持|支持| |
|JSR303数据校验|支持|不支持|@Valid，@Email等 hibernate包支持|
|复杂类型封装|支持|不支持| 复杂类型比如list，map|

如果只需要获取某一个值得话，直接用value

### 属性松散绑定


> + 表示驼峰式、下划线(_)、短横线(-) 大小写不区分

### SpEL

> SpEL（Spring Expression Language），即Spring表达式语言，是比JSP的EL更强大的一种表达式语言
> + Value(#{表达式})
>
> ```java
>     //@Value能修饰成员变量和方法形参
>     //#{}内就是表达式的内容
>     @Value("#{表达式}")
>     public String arg;
> ```
>
> + XML 
>
> ```xml
> <bean id="xxx" class="com.java.XXXXX.xx">
>     <!-- 同@Value,#{}内是表达式的值，可放在property或constructor-arg内 -->
>     <property name="arg" value="#{表达式}">
> </bean>
> ```
>
> + Expression
>
> ```java
> import org.springframework.expression.Expression;
> import org.springframework.expression.ExpressionParser;
> import org.springframework.expression.spel.standard.SpelExpressionParser;
> import org.springframework.expression.spel.support.StandardEvaluationContext;
>  
> public class SpELTest {
>  
>     public static void main(String[] args) {
>  
>         //创建ExpressionParser解析表达式
>         ExpressionParser parser = new SpelExpressionParser();
>         //表达式放置
>         Expression exp = parser.parseExpression("表达式");
>         //执行表达式，默认容器是spring本身的容器：ApplicationContext
>         Object value = exp.getValue();
>         
>         /**如果使用其他的容器，则用下面的方法*/
>         //创建一个虚拟的容器EvaluationContext
>         StandardEvaluationContext ctx = new StandardEvaluationContext();
>         //向容器内添加bean
>         BeanA beanA = new BeanA();
>         ctx.setVariable("bean_id", beanA);
>         
>         //setRootObject并非必须；一个EvaluationContext只能有一个RootObject，引用它的属性时，可以不加前缀
>         ctx.setRootObject(XXX);
>         
>         //getValue有参数ctx，从新的容器中根据SpEL表达式获取所需的值
>         Object value = exp.getValue(ctx);
>     }
> }
> ```

#### 语法
##### 字面量赋值
```xml
<!-- 整数 -->
<property name="count" value="#{5}" />
<!-- 小数 -->
<property name="frequency" value="#{13.2}" />
<!-- 科学计数法 -->
<property name="capacity" value="#{1e4}" />
<!-- 字符串  #{"字符串"} 或  #{'字符串'} -->
<property name="name" value="#{'我是字符串'}" />
<!-- Boolean -->
<property name="enabled" value="#{false}" />
```
##### 引用Bean、属性和方法（必须是public修饰的）
```xml
<property name="car" value="#{car}" />
<!-- 引用其他对象的属性 -->
<property name="carName" value="#{car.name}" />
<!-- 引用其他对象的方法 -->
<property name="carPrint" value="#{car.print()}" />
```
...
