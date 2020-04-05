# thymeleaf

https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#a-multi-language-welcome

### pom.xml 中增加

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
```
如果要修改版本可以增加properties 中增加版本，查看spring-boot-starter-thymeleaf 中的依赖包的对应版本

thymeleaf默认，
页面跳转默认路径：src/main/resources/templates
静态资源默认路径：src/main/resources/static

### application.properties

```properties
#<!-- 关闭thymeleaf缓存 开发时使用 否则没有实时画面-->
spring.thymeleaf.cache=false
## 检查模板是否存在，然后再呈现
spring.thymeleaf.check-template-location=true
#Content-Type值
spring.thymeleaf.content-type=text/html
#启用MVC Thymeleaf视图分辨率
spring.thymeleaf.enabled=true
## 应该从解决方案中排除的视图名称的逗号分隔列表
##spring.thymeleaf.excluded-view-names=
#模板编码
spring.thymeleaf.mode=LEGACYHTML5
# 在构建URL时预先查看名称的前缀
spring.thymeleaf.prefix=classpath:/templates/
# 构建URL时附加查看名称的后缀.
spring.thymeleaf.suffix=.html
# 链中模板解析器的顺序
#spring.thymeleaf.template-resolver-order= o
# 可以解析的视图名称的逗号分隔列表
#spring.thymeleaf.view-names=
#thymeleaf end
```

> 1. 结尾一定要有------ #thymeleaf end --------- 否则掉坑
>
> 2. spring.thymeleaf.mode = LEGACYHTML5
>    spring.thymeleaf.mode的默认值是HTML5，其实是一个很严格的检查，改为LEGACYHTML5可以得到一个可能更友好亲切的格式要求。
>
>    + 可能需要搭配一个额外的库
>
>    

### 创建一个index.html页面

在resources/templates目录创建index.html页面

> thymeleaf的名空间 `xmlns:th="http://www.thymeleaf.org"`
index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <!-- 使用th:text属性输出 -->
    <div th:text="${message}" ></div>
</body>
</html>
```

### controller层

注入的时候一定要是Controller 不要是RestController 因为它是rest接口（json格式） 是解析不到html

变量表达式的作用是：从web作用域里面取到对应的值，作用域包括 **request、session、application**。

```html
${param.x} 返回名为x 的 request参数。（可能有多个值）
${session.x} 返回名为x的Session参数。
${application.x} 返回名为 servlet context 的参数。
```



### thymeleaf 语法

>  手册中的 第 `10 Attribute Precedence`节

#### 标签

|Order|Feature|Attributes|JSP标签||
| ---- | ---- | ---- |---- |---- |
|1|Fragment inclusion|th:insert|jsp:include||
|||th:replace|||
|2|Fragment iteration|th:each|c:foreach||
|3|Conditional evaluation|th:if|c:if||
|||th:unless|||
|||th:switch|||
|||th:case|||
|4|Local variable definition|th:object|c:set||
|||th:with|||
|5|General attribute modification|th:attr||任意属性修改|
|||th:attrprepend||支持prepend|
|||th:attrappend||支持append属性|
|6|Specific attribute modification|th:value||修改任意属性默认值|
|||th:href|||
|||th:src|||
|||...|||
|7|Text (tag body modification)|th:text||标签体内容： 转义特殊字符|
|||th:utext||不转义特殊字符|
|8|Fragment specification|th:fragment|||
|9|Fragment removal|th:remove|||

#### 表达式

> 手册中的 第  `4 Standard Expression Syntax` 节

> Variable Expressions: ${...} 
>
> 1. 获取对象属性方法
> 2. 内置对象
> 3. 内置工具对象
>
> Selection Variable Expressions *{...}
>
> ​	配合th:object 使用的
>
> Message Expressions #{...}  获取国际化内容的
>
> Link URL Expressions @{...}  定义url连接的
>
> Fragment Expressions~{...} 片段引用表达式

|                          |                                |                                        |
| ------------------------ | ------------------------------ | -------------------------------------- |
| Simple expressions:      | Variable Expressions           | ${...}                                 |
|                          | Selection Variable Expressions | *{...}                                 |
|                          | Message Expressions            | #{...}                                 |
|                          | Link URL Expressions           | @{...}                                 |
|                          | Fragment Expressions           | ~{...}                                 |
| Literals                 | Text literals                  | 'one text' , 'Another one!' ,…         |
|                          | Number literals                | 0 , 34 , 3.0 , 12.3 ,…                 |
|                          | Boolean literals               | true , false                           |
|                          | Null literal                   | null                                   |
|                          | Literal tokens                 | one , sometext , main ,…               |
| Text operations          | String concatenation           | +                                      |
|                          | Literal substitutions          | The name is ${name}                    |
| Arithmetic operations    | Binary operators               | + , - , * , / , %                      |
|                          | Minus sign (unary operator)    | -                                      |
| Boolean operations       | Binary operators               | and , or                               |
| Comparisons and equality | Comparators                    | > , < , >= , <=  ( gt , lt , ge , le ) |
|                          | Equality operators             | == , !=  ( eq , ne )                   |
| Conditional operators    | If-then                        | (if) ? (then)                          |
|                          | If-then-else                   | (if) ? (then) : (else)                 |
|                          | Default                        | (value) ?: (defaultvalue)              |
| Special tokens           | No-Operation                   | _                                      |

