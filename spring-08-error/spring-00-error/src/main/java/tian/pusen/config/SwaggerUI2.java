//package tian.pusen.config;
//
//import org.springframework.context.annotation.Profile;
//import springfox.documentation.service.Contact;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//
//@Configuration
//@EnableSwagger2
//public class SwaggerUI2 {
//    @Profile(value = {"dev0"})
//    @Bean
//    public Docket createRestApiDev0() {
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title("服务接口文档范例")
//                .description("服务接口文档，严格遵循RESTful API设计规范。")
//                .contact(new Contact("田圃森","https://github.com/pustian","pustian@msn.com"))
//                .version("1.0")
//                .build();
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .select()
//                //以扫描包的方式
//                .apis(RequestHandlerSelectors.basePackage("tian.pusen.web"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    @Profile(value = {"dev1"})
//    @Bean
//    public Docket createRestApiDev1() {
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title("服务接口文档范例")
//                .description("服务接口文档，严格遵循RESTful API设计规范。")
//                .contact(new Contact("田圃森","https://github.com/pustian","pustian@msn.com"))
//                .version("1.0")
//                .build();
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .select()
//                //以扫描包的方式
//                .apis(RequestHandlerSelectors.basePackage("tian.pusen.dev1.web"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//}
