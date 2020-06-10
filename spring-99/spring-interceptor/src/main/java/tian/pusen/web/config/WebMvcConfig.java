package tian.pusen.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    /**
//     * 自定义JSON转换器
//     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        //日期格式化
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        //处理中文乱码问题
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//
//        converter.setSupportedMediaTypes(fastMediaTypes);
//        converter.setFastJsonConfig(fastJsonConfig);
//        converters.add(converter);
//    }

    @Autowired
    LogHandlerInterceptor logHandlerInterceptor;

    /**
     * 添加自定义拦截器
     * .addPathPatterns("/**")  拦截的请求路径
     * .excludePathPatterns("/user"); 排除的请求路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("Add Interceptor");
        registry.addInterceptor(logHandlerInterceptor)
                .addPathPatterns("/**")
//                .excludePathPatterns("/v1")
                ;
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
