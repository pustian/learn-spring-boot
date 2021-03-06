https://baomidou.gitee.io/mybatis-plus-doc/#/spring-boot
mybatis-plus 2.X 版本

### pom.xml 引用
```xml
<!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>2.3.3</version>
</dependency>
```
```xml
<!-- MP 核心库 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus</artifactId>
    <version>2.3.3</version>
</dependency>
```

### 代码生成
```xml
<!-- 模板引擎 -->
<dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity-engine-core</artifactId>
    <version>最新版本</version>
</dependency>
```
```xml
<!-- 模板引擎，需要指定 mpg.setTemplateEngine(new FreemarkerTemplateEngine()); -->
<dependency>
    <groupId>org.freemarker</groupId>
    <artifactId>freemarker</artifactId>
    <version>最新版本</version>
</dependency>
```
> 注意调整其中的部分字段
> url, username, password, type
> directory
```java
package tian.pusen;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


public class MPGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        // mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D://logs/");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        // .setKotlin(true) 是否生成 kotlin 代码
        gc.setAuthor("pustian");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysq.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("ldap4$");
        dsc.setUrl("jdbc:mysql://192.168.1.106:3306/data_dictionary?serverTimezone=Asia/Shanghai");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(new String[] { "tlog_", "tsys_" });// 此处可以修改为您的表前缀
        strategy.setTablePrefix(new String[] {""});
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        // strategy.setInclude(new String[] { "user" }); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 字段名生成策略
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("tian.pusen");
        pc.setModuleName("data");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        //InjectionConfig cfg = new InjectionConfig() {
        //    @Override
        //    public void initMap() {
        //        Map<String, Object> map = new HashMap<String, Object>();
        //        map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
        //        this.setMap(map);
        //    }
        //};

        //// 自定义 xxList.jsp 生成
        //List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        //focList.add(new FileOutConfig("/template/list.jsp.vm") {
        //    @Override
        //    public String outputFile(TableInfo tableInfo) {
        //        // 自定义输入文件名称
        //        return "D://my_" + tableInfo.getEntityName() + ".jsp";
        //    }
        //});
        //cfg.setFileOutConfigList(focList);
        //mpg.setCfg(cfg);

        //// 调整 xml 生成目录演示
        //focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
        //    @Override
        //    public String outputFile(TableInfo tableInfo) {
        //        return "/develop/code/xml/" + tableInfo.getEntityName() + ".xml";
        //    }
        //});
        //cfg.setFileOutConfigList(focList);
        //mpg.setCfg(cfg);

        //// 关闭默认 xml 生成，调整生成 至 根目录
        //TemplateConfig tc = new TemplateConfig();
        //tc.setXml(null);
        //mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
         TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        //// 打印注入设置【可无】
        //System.err.println(mpg.getCfg().getMap().get("abc"));
    }
}
```

application.yml
```yaml
server:
    port: 8080
spring:
    profiles:
        active: dev2

mybatis-plus:
    # 如果是放在src/main/java目录下 classpath:/com/yourpackage/*/mapper/*Mapper.xml
    # 如果是放在resource目录 classpath:/mapper/*Mapper.xml
    mapper-locations: classpath:mapper/*Mapper.xml
    #实体扫描，多个package用逗号或者分号分隔
    typeAliasesPackage: tian.pusen.data.entity
#    global-config:
#        dbType: mysql
#        #主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
#        id-type: 3
#        #字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
#        field-strategy: 2
##        #驼峰下划线转换
##        db-column-underline: true
#        #mp2.3+ 全局表前缀 mp_
#        #table-prefix: mp_
#        #刷新mapper 调试神器
#        #refresh-mapper: true
#        #数据库大写下划线转换
#        #capital-mode: true
##        # Sequence序列接口实现类配置
##        key-generator: com.baomidou.mybatisplus.incrementer.OracleKeyGenerator
##        #逻辑删除配置（下面3个配置）
##        logic-delete-value: 1
##        logic-not-delete-value: 0
##        sql-injector: com.baomidou.mybatisplus.mapper.LogicSqlInjector
##        #自定义填充策略接口实现
##        meta-object-handler: com.baomidou.springboot.MyMetaObjectHandler
    configuration:
        #配置返回数据库(column下划线命名&&返回java实体是驼峰命名)，自动匹配无需as（没开启这个，SQL需要写as： select user_id as userId）
        map-underscore-to-camel-case: true
        cache-enabled: false
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
#        #配置JdbcTypeForNull, oracle数据库必须配置
#        jdbc-type-for-null: 'null'

---
spring:
    profiles: dev
    datasource:
        url: jdbc:mysql://192.168.1.106:3306/data_dictionary?serverTimezone=Asia/Shanghai
        username: root
        password: ldap4$
        driver-class-name: com.mysql.cj.jdbc.Driver
---
spring:
    profiles: dev2
    datasource:
        url: jdbc:mysql://192.168.122.249:3306/data-dictionary?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai
        username: root
        password: mysql123
        driver-class-name: com.mysql.cj.jdbc.Driver
```

