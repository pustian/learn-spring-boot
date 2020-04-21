swagger-ui 和 报文头统一定制
> 此处profiles 的使用

pom.xml
```xml
        <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```
SwaggerUI2
```java
@Configuration
@EnableSwagger2
public class SwaggerUI2 {
    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("服务接口文档范例")
                .description("服务接口文档，严格遵循RESTful API设计规范。")
                .contact(new Contact("田圃森","https://github.com/pustian","pustian@msn.com"))
                .version("1.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                //以扫描包的方式
                .apis(RequestHandlerSelectors.basePackage("tian.pusen.dev0.web"))
                .paths(PathSelectors.any())
                .build();
    }
}
```

https://swagger.io/docs/specification/basic-structure/

统一定义的规范，添加的响应msg和code 
放到响应报文头中

//如果有继承关系的