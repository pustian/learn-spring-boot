安装
官网下载 spring-cli 安装，然后将bin目录配置环境变量。
安装完成可通过 spring version 查看版本。
```bash
# https://docs.spring.io/spring-boot/docs/
wget https://repo.spring.io/release/org/springframework/boot/spring-boot-cli/2.2.0.RELEASE/spring-boot-cli-2.2.0.RELEASE-bin.tar.gz

tar xvf spring-boot-cli-2.2.0.RELEASE-bin.tar.gz
mv spring-boot-cli-2.2.0.RELEASE /usr/local
ln -sf /usr/local/spring-boot-cli-2.2.0.RELEASE /usr/local/spring-boot-cli
```
~/.bashrc追加
```bash
export SPRING_BOOT_CLI_HOME=/usr/local/spring-boot-cli
export CLASSPATH=$SPRING_BOOT_CLI_HOME/lib:$CLASSPATH
export PATH=$SPRING_BOOT_CLI_HOME/bin:$PATH
```
```bash
source ~/.bashrc
spring --version
```
```
dew@diyu204:~$ spring --version
Spring CLI v2.2.0.RELEASE
```

app.groovy
```groovy
@RestController
class ThisWillActuallyRun {

    @RequestMapping("/")
    String home() {
        "Hello World!"
    }

}
```

```bash
spring run app.groovy
```
```bash
curl http://localhost:8080/
```
命令行运行
```bash
mvn spring-boot:run
```
jar package
pom.xml
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
```bash
mvn package
mvn clean package -DskipTests
jar tvf target/myproject-0.0.1-SNAPSHOT.jar
```


