# SpringCloud

## 第一阶段服务提供者搭建 ( 服务端 )

![](https://github.com/NirvanaDxD/PicGoSave/blob/main/202208100910135.png?raw=true)

### 1.创建一个测试数据库

#### 数据库要求

数据库名： msc_test
实体表名 : sc_user
字段信息：
	id 编号
	uname 用户名
	password 密码
	sex 性别
	addr 地址
插入任意三条数据

### 2.创建父工程模块

#### 2.1编写父类的pom.xml

引入依赖包

```xml
	<!-- 引入父类坐标 -->
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.2.RELEASE</version>
		<relativePath />
	</parent>
	<!-- 常用属性 -->
	<properties>
        <!-- 设置编码格式 -->
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<!-- JDK版本 -->
        <java.version>1.8</java.version>
		<pageHelper.starter.version>1.2.5</pageHelper.starter.version>
	</properties>
	<dependencyManagement>
		<dependencies>
			<!-- springCloud -->
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>Greenwich.SR1</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
			<!-- 整合mybatis -->
			<dependency>
				<groupId>org.mybatis.spring.boot</groupId>
				<artifactId>mybatis-spring-boot-starter</artifactId>
				<version>2.0.1</version>
			</dependency>
			<!-- mysql驱动 -->
			<dependency>
				<groupId>mysql</groupId>
				<artifactId>mysql-connector-java</artifactId>
				<version>5.1.47</version>
			</dependency>
			<!-- 通用Mapper启动器 -->
			<dependency>
				<groupId>tk.mybatis</groupId>
				<artifactId>mapper-spring-boot-starter</artifactId>
				<version>${mapper.starter.version}</version>
			</dependency>

		</dependencies>
	</dependencyManagement>
```



引入maven插件，用于配置热部署

```xml
<!-- maven插件 -->
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<fork>true</fork>
					<addResources>true</addResources>
				</configuration>
			</plugin>
		</plugins>
	</build>
```

#### 2.2创建服务提供者工程模块

##### 2.2.1创建服务提供者子项目

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100922326.png)

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100922341.png)



##### 2.2.2在子项目 pom 中添加依赖

```xml
	<dependencies>
		<!-- springboot启动器 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!-- mybatis 依赖 -->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
		</dependency>
		<!-- mysql驱动包 -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
		<!-- 热部署 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>
	</dependencies>
```

##### 2.2.3创建服务提供者项目架构

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100924223.png)

##### 2.2.4创建配置文件 [ application.yml ]

```properties
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/msc_test
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver

#端口号配置
server:
  port: 8081

mybatis:
  type-aliases-package: top.dycstudy.pojo
#sql 信息打印到控制台输出
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

##### 2.2.5创建 springboot 的启动类

```java
@SpringBootApplication
@MapperScan("top.dycstudy.dao")
public class run {
	public static void main(String[] args) {
		SpringApplication.run(run.class, args);
	}
}
```

##### 2.2.6创建控制器类

```java
@RestController
public class UserController {
	//自动注入
	@Autowired
	private UserMapper userMapper;
	//根据id查询
	@GetMapping("index/{id}")
	public Scuser select(@PathVariable("id")Integer id){
		Scuser user = userMapper.selectById(id);
		return user;
	}
	//查询全部数据
	@RequestMapping("findAll")
	public List<Scuser> findAll(){
		List<Scuser> list = userMapper.findAll();
		return list;
	}
	
}
```

##### 2.2.7创建实体类 ,  getter/setter 和重写toString方法

```java
public class Scuser {

	private Integer id;
	private String uname;
	private String password;
	private String sex;
	private String addr;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Scuser(Integer id, String uname, String password, String sex, String addr) {
		super();
		this.id = id;
		this.uname = uname;
		this.password = password;
		this.sex = sex;
		this.addr = addr;
	}
	public Scuser() {
		super();
	}
	@Override
	public String toString() {
		return "Scuser [id=" + id + ", uname=" + uname + ", password=" + password + ", sex=" + sex + ", addr=" + addr+ "]";
	}
}
```

##### 2.2.8创建dao层mapper接口

```java
public interface UserMapper {
	Scuser selectById(Integer id); //根据id查询
	List<Scuser> findAll();//查询全部
}
```

##### 2.2.9创建mapper.xml

```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "mybatis-3-mapper.dtd" >
<mapper namespace="top.dycstudy.dao.UserMapper">
	//根据id查询
	<select id="selectById" resultType="scuser" parameterType="int">
		select * from sc_user where id=#{id}
	</select>
	//查询全部数据
	<select id="findAll" resultType="scuser">
		select * from sc_user
	</select>
</mapper>
```

##### 2.2.10创建抽象类UserService

```java
public interface UserService {
	Scuser selectById(Integer id); //根据id查询
	List<Scuser> findAll();//查询全部
}
```

##### 2.2.11创建接口UserServiceImpl实现抽象类中方法

```java
public class UserServiceImpl implements UserService{
	@Resource(name="userMapper")
	private UserMapper userMapper;
	
	@Override
	public Scuser selectById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectById(id);
	}

	@Override
	public List<Scuser> findAll() {
		// TODO Auto-generated method stub
		return userMapper.findAll();
	}
}
```

##### 2.2.12启动服务，测试运行

**根据id查询**

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100924567.png)
![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100924590.png)
![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100924609.png)



**查询全部信息**
![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100924621.png)





## 第二阶段服务消费者搭建 ( 客户端 )

### 1.在父类项目中创建一个消费服务的子项目

#### 1.1在子项目pom中添加依赖

```xml
	<dependencies>
		<!-- springboot启动器 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!-- 热部署 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>
	</dependencies>
```

#### 1.2创建服务消费者项目架构

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100927432.png)

#### 1.3创建启动器 , 注入RestTemplate

```java
@SpringBootApplication
public class run {
	public static void main(String[] args) {
		SpringApplication.run(run.class, args);
	}
	//注入RestTemplate
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
```

#### 1.4创建实体类 , getter/setter和重写toString方法

```java
public class UserVO {

	private Integer id;
	private String uname;
	private String password;
	private String sex;
	private String addr;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public UserVO(Integer id, String uname, String password, String sex, String addr) {
		super();
		this.id = id;
		this.uname = uname;
		this.password = password;
		this.sex = sex;
		this.addr = addr;
	}
	public UserVO() {
		super();
	}
	@Override
	public String toString() {
		return "Scuser [id=" + id + ", uname=" + uname + ", password=" + password + ", sex=" + sex + ", addr=" + addr+ "]";
	}
}
```

#### 1.5创建控制器类

```java
@RestController
@RequestMapping("client")
public class GUserController {
	//注入restTemplate模板工具
	@Autowired
	private RestTemplate restTemplate;
	//根据id查询
	@GetMapping("{id}")
	public UserVO queryOneUser(@PathVariable("id")Integer id){
		//获取host和port注册url
		String S_url = "http://localhost:8081/index/"+id;
		//提交请求获取相应数据
		UserVO uv = restTemplate.getForObject(S_url, UserVO.class);
		return uv;
	}
	//查询全部数据
	@GetMapping("findAll")
	public List<UserVO> findAll(){
		//获取host和port注册url
		String S_url = "http://localhost:8081/findAll/";
		//提交请求获取相应数据
		List<UserVO> list = restTemplate.getForObject(S_url,List.class);
		return list;
	}
}
```

#### 1.6启动服务，测试运行

注意此处启动需要先启动服务提供者 ( 服务端 )

根据id查询

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100933290.png)

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100933234.png)

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100933262.png)





查询全部信息

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100933279.png)



## 第三阶段Eureka服务搭建

### 1.创建一个eureka子项目

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100934185.png)



### 2.添加 eureka 依赖 

```xml
	<dependencies>
        <!-- eureka-server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <!-- 热部署 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>
    </dependencies>
```



### 3.创建启动器

```java
@SpringBootApplication
@EnableEurekaServer
public class run {
	public static void main(String[] args) {
		SpringApplication.run(run.class, args);
	}
}
```

### 4.编写 application.yml 配置文件

eureka 配置通过eureka.client.registerWithEureka false 和 fetchRegistry false 来表明自己是一个
eureka server

```properties
#服务端口号
server:
  port: 8093
eureka:
  client:
  #是否将自己注册到EurekaServer
    register-with-eureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://localhost:8093/eureka/
```

### 5.Eureka 服务器启动

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100934101.png)



## 第四阶段注册服务端客户端到Eureka

### 创建一个服务提供者 ( eureka client )

当client 向 server 注册时，它会提供一些元数据，例如主机和端口， URL ，主页等。 Eureka server
从每个 client 实例接收心跳消息。如果心跳超时，则通常将该实例从注册 server 中删除。

### 需求准备

1、 需要准备一个 eureka 服务，即按照上面的搭建的 eureka 注册启动器可以继续使用。
2、准备服务提供者项目，测试项目无误可正常启动访问。
3、准备服务消费者项目，测试项目无误可正常启动访问。
4、配置完成后能在 eureka 启动页面可以看到服务提供者、服务消费者的注册服务 。

### 服务注册

修改服务提供者

#### 1.添加依赖

在服务提供者中添加Eureka的客户端依赖

客户端代码会自动把服务注册到EurekaServer中

```xml
	<!-- eureka客户端依赖 -->
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

#### 2.服务提供者启动器修改

启动一个服务注册中心，只需要在springboot 工程的启动 application 类上加 一个注解

@EnableDiscoveryClient和@EnableEurekaClient

共同点就是：都是能够让注册中心能够发现，扫描到改服务。

不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心

```java
@SpringBootApplication
@MapperScan("top.dycstudy.dao")
@EnableEurekaClient
public class run {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(run.class, args);
	}
}
```

#### 3.修改yml文件

eureka是一个高可用的组件，它没有后端缓存，每一个实例注册之后需要向注册中心发送心跳（因此可以在内存中完成），在默认情况下eureka server也是一个eureka client ,必须要指必须要指定一个server。

```properties
#对项目进行重命名
spring:
  application:
    name: msc_server
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8093/eureka/
```

#### 4.测试

先启动 Eureka 注册中心，然后启动服务提供者。

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100934763.png)

### 服务发现

#### 1.添加依赖

```xml
	<!-- eureka客户端依赖 -->
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```



#### 2.服务消费者启动器修改

```java
@SpringBootApplication
@EnableEurekaClient
public class run {
	public static void main(String[] args) {
		SpringApplication.run(run.class, args);
	}
	//注入RestTemplate
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
```



#### 3.修改yml文件

```properties
#端口号配置
server:
  port: 8092
#对项目进行重命名
spring:
  application:
    name: msc_client
  
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8093/eureka/
```



#### 4.测试

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100935188.png)



## 第五阶段Eureka集群搭建

### Eureka 集群的配置 

Eureka Server 即服务的注册中心，在刚才的案例中，我们只有一个 EurekaServer，事实上 EurekaServer 也可以是一个集群，形成高可用的 Eureka 中心。 

服务同步 

​		多个 Eureka Server 之间也会互相注册为服务，当服务提供者注册到 Eureka Server 集群 中的某个节点时，该节点会把服务的信息同步给集群中的每个节点，从而实现数据同步。因 此，无论客户端访问到 Eureka Server 集群中的任意一个节点，都可以获取到完整的服务列 表信息。 

而作为客户端，需要把信息注册到每个 Eureka 中： 

如果有三个 Eureka，则每一个 EurekaServer 都需要注册到其它几个 Eureka 服务中，例如： 有三个分别为 10066、10067、10068，

则： 	10066 要注册到 10067 和 10068 上 

​			10067 要注册到 10066 和 10068 上 

​			10068 要注册到 10066 和 10067 上



### 需求准备

本次先用 2 个端口进行模拟配置，Eureka 暂时 用 8093、8094两个端口模拟搭建即可。 然后把服务提供者和服务消费者两个项目注册到端口为 8093和 8094的两个 Eureka 上。

### 1.添加一个新的 eureka 子项目

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100935486.png)



### 2.yml 配置文件编写

设置eureka2的端口为8094,配置服务名、实例名字、其他 Eureka 服务的地址。

```properties
server:
  port: 8094	#端口号为8094
  
spring:
  application:
    name: Eureka_server2
  
eureka:
  instance: 
      hostname: eureka2
  client: 
    registerWithEureka: true
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://localhost:8093/eureka/	#将自己注册到其他的eureka中，这里是注册到原有eureka中
    
```

在原有的eureka中也要对yml配置文件进行修改

```properties
server:
  port: 8093	#端口号为8093
spring:
  application:
    name: Eureka_server 
eureka:
  instance:
    hostname: eureka1
  client: 
    registerWithEureka: true
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://localhost:8094/eureka/	#将自己注册到其他的eureka中，这里是注册到新建的eureka1中
```



### 3.配置主机 host 文件

```
127.0.0.1 eureka1
127.0.0.1 eureka2
```

修改host之后可以通过eureka1和eureka2进行访问



### 4.服务提供者注册到集群服务

在服务端的yml配置中进行修改

```properties
eureka:
  client:
    service-url:
      defaultZone: http://eureka1:8093/eureka/,http://eureka2:8094/eureka/
					#同时在两个eureka中注册，用逗号进行分割
```



### 5.服务消费者注册到集群服务

在客户端的yml配置中进行修改

```properties
eureka:
  client:
    service-url:
      defaultZone: http://eureka1:8093/eureka/,http://eureka2:8094/eureka/
					#同时在两个eureka中注册，用逗号进行分割
```



### 6.测试

访问端口8093	http://eureka1:8093

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100935291.png)



访问端口8094	http://eureka1:8094

![](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208100935666.png)

## 第六阶段继续添加Eureka到集群中

### 1.创建一个新的eureka子项目

<img src="https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208101004817.png" alt="image-20220810100451549" style="zoom:67%;" />

### 2.yml配置文件编写

```properties
server:
  port: 8095
  
spring:
  application:
    name: Eureka_server3
  
eureka:
  instance: 
      hostname: eureka3
  client: 
    registerWithEureka: true
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://localhost:8093/eureka/,http://localhost:8094/eureka/
```

对原先的两个Eureka的yml也要进行修改

需要将eureka3注册到原先两个eureka中

### 3.配置主机 host 文件

```
127.0.0.1 eureka1
127.0.0.1 eureka2
127.0.0.1 eureka3
```

### 4.服务提供者注册到集群服务

在服务端的yml配置中进行修改

```properties
eureka:
  client:
    service-url:
      defaultZone: http://eureka1:8093/eureka/,http://eureka2:8094/eureka/,http://eureka3:8095/eureka/
					#同时在两个eureka中注册，用逗号进行分割
```



### 5.服务消费者注册到集群服务

在客户端的yml配置中进行修改

```properties
eureka:
  client:
    service-url:
      defaultZone: http://eureka1:8093/eureka/,http://eureka2:8094/eureka/,http://eureka3:8095/eureka/
					#同时在两个eureka中注册，用逗号进行分割
```

### 6.测试

eureka1

![image-20220810101109754](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208101011195.png)

eureka2

![image-20220810101133161](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208101011731.png)

eureka3

![image-20220810101157174](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208101011548.png)



## 第七阶段Ribbon负载均衡

### 1.创建一个服务提供者，设置端口号为8096

```properties
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/msc_test
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
  application:
    name: msc-server		#此处设置的应用名需要和先前的服务端相同

#端口号配置
server:
  port: 8096

mybatis:
  type-aliases-package: top.dycstudy.pojo
#sql 信息打印到控制台输出
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

eureka:
  client:
    service-url:
      defaultZone: http://eureka1:8093/eureka/,http://eureka2:8094/eureka/,http://eureka3:8095/eureka/

```

### 2.开启负载均衡

因为 Eureka 中已经集成了 Ribbon，所以我们无需引入新的依赖。直接修改代码： 在 RestTemplate 的配置方法上添加 @LoadBalanced 注解，修改控制器代码。

```java
@SpringBootApplication
@EnableEurekaClient
public class run {
	public static void main(String[] args) {
		SpringApplication.run(run.class, args);
	}	
	//注入RestTemplate
	@LoadBalanced	//开启负载均衡
	@Bean	
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}

```

### 3.修改获取资源路径

```java
@RestController
@RequestMapping("client")
public class GUserController {
	//注入restTemplate模板工具
	@Autowired
	private RestTemplate restTemplate;
	//根据id获取信息
	@GetMapping("{id}")
	public UserVO queryOneUser(@PathVariable("id")Integer id){
		//获取host和port注册url
		String S_url = "http://msc-server/index/"+id;
		//提交请求获取相应数据
		UserVO uv = restTemplate.getForObject(S_url, UserVO.class);
		System.out.println(uv);
		System.out.println(S_url);
		return uv;
	}
	//获取全部信息
	@GetMapping("findAll")
	public List<UserVO> findAll(){
		//获取host和port注册url
		String S_url = "http://msc-server/findAll/";
		//提交请求获取相应数据
		List<UserVO> list = restTemplate.getForObject(S_url,List.class);
		System.out.println(list);
		System.out.println(S_url);
		return list;
	}	
}
```

11行和22行的资源路径对应需要访问的应用服务名称

### 4.测试

![image-20220811091439317](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208110918061.png)

![image-20220811091423657](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208110918906.png)

### bug

#### 在搭建过程中遇到一个bug

![image-20220811091901451](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208110919962.png)

```
提示Request URI does not contain a valid hostname: http://MSC_SERVER/index/1
	请求URI不包含有效的主机名：http://MSC_SERVER/index/1
```

#### 错误原因分析

之前创建服务提供者的时候自定义的应用名称为msc_server

在应用名称中出现下划线，负载均衡就无法解析，所以将msc_server改为msc-server即可



## 第八阶段Hystrix搭建

### Hystix 是 Netflix 开源的一个延迟和容错库，用于隔离访问远程服务、第三方库，防止出现级联失败。

服务器支持的线程和并发数有限，请求一直阻塞，会导致服务器资源耗尽，从而导致所有其它服务都不可用，形成雪崩效应。

这就好比，一个汽车生产线，生产不同的汽车，需要使用不同的零件，如果某个零件因为种

种原因无法使用，那么就会造成整台车无法 装配，陷入等待零件的状态，直到零件到位，才

能继续组装。 此时如果有很多个车型都需要这个零件，那么整个工厂都将陷入等待的状态，

导致所有生产都陷入瘫痪。一个零件的波及范围不断扩大。

Hystix解决雪崩问题的手段主要是服务降级，包括：

1、线程隔离

2、服务熔断

### 一、线程隔离，服务降级

==服务降级：优先保证核心服务，而非核心服务不可用或弱可用。==

#### 1.引入依赖

```xml
	<!-- Hystrix依赖 -->
        <dependency>
        	<groupId>org.springframework.cloud</groupId>
        	<artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency
```

#### 2.开启熔断

在启动类上添加注解：@EnableCircuitBreaker

```java
@SpringBootApplication
@EnableEurekaClient
//开启熔断
@EnableCircuitBreaker
public class run {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(run.class, args);
	}	
	//注入RestTemplate
	@LoadBalanced
	@Bean	
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
```

#### 3.编写降级逻辑

当目标服务的调用出现故障，我们希望快速失败，给用户一个友好提示。因此需要提前编写 好失败时的 降级处理逻辑，要使用 HystixCommond 来完成： 使用默认的 Fallback。 我们把 fallback 写在了某个业务方法上，如果这样的方法很多，那岂不是要写很多。所以我 们可以把 Fallback 配置加在类上，实现默认 fallback： @DefaultProperties(defaultFallback = "defaultFallBack")：在类上指明统一的失败降级方法

在客户端controller层添加注解

```java
@RestController
@RequestMapping("client")
//Fallback 配置
@DefaultProperties(defaultFallback = "defaultFallBack")
public class GUserController {
	//注入restTemplate模板工具
	@Autowired
    private RestTemplate restTemplate;
   
    //编写降级逻辑，需要返回字符串，测试的时候需要关闭服务提供者服务
	@GetMapping("{id}")
	@HystrixCommand
	public String queryOneUser(@PathVariable("id")Integer id){
		//获取host和port注册url
		String S_url = "http://msc-server/index/"+id;
		//提交请求获取相应数据
		String uv = restTemplate.getForObject(S_url, String.class);
		System.out.println(uv);
		System.out.println(S_url);
		return uv;
	}
    //返回类型是String，当线程降级的时候返回字符串
	public String defaultFallBack(){
		return "网络拥挤无法访问";
	}	
}
```

#### 4.测试

只打开服务消费者，不启动服务提供者，进行测试

![image-20220812103457169](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208121035583.png)

### 二、服务熔断

熔断器，也叫断路器，其英文单词为：Circuit Breaker

状态机有 3 个状态：

- Closed：关闭状态（断路器关闭），所有请求都正常访问。 

- Open：打开状态（断路器打开），所有请求都会被降级。Hystix 会对请求情况计数，当 一定时间内失败请求百分比达到阈值，则触发熔断，断路器会完全关闭。默认失败比例 的阈值是 50%，请求次数最少不低于 20 次。 

- Half Open：半开状态，open 状态不是永久的，打开后会进入休眠时间（默认是 5S）。 随后断路器 会自动进入半开状态。此时会释放 1 次请求通过，若这个请求都是健康的， 则会完全关闭断路器， 否则继续保持打开，再次进行休眠计时。

#### 1.修改服务消费者

为了能够精确控制请求的成功或失败，我们在 consumer 的调用业务中加入一段逻辑：

```java
@RestController
@RequestMapping("client")
@DefaultProperties(defaultFallback = "defaultFallBack")
public class GUserController {
	
	//注入restTemplate模板工具
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("{id}")
	@HystrixCommand
	public String queryOneUser(@PathVariable("id")Integer id){
		if(id==2){
			throw new RuntimeException("sagfasgf2");
		}
		//获取host和port注册url
		String S_url = "http://msc-server/index/"+id;
		//提交请求获取相应数据
		String uv = restTemplate.getForObject(S_url, String.class);
		System.out.println(uv);
		System.out.println(S_url);
		return uv;
	}
	public String defaultFallBack(){
		return "网络拥挤无法访问";
	}
}
```

这样如果参数是 id 为 2，一定失败，其它情况都成功。

#### 2.测试

打开服务端和客户端进行测试

![image-20220812104516690](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208121045379.png)

### 三、hystrix 服务监控 Dashboard

#### 1.引入依赖

```xml
		<!-- 引入dashboard依赖 -->
		<dependency>
        	<groupId>org.springframework.cloud</groupId>
        	<artifactId>spring-cloud-netflix-hystrix-dashboard</artifactId>
        </dependency> 
        <!-- 引入actuator监控器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

#### 2.在客户端启动类添加注解@EnableHystrixDashboard

```java
@SpringBootApplication
@EnableEurekaClient
//开启熔断
@EnableCircuitBreaker
//开启仪表盘
@EnableHystrixDashboard
public class run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(run.class, args);
	}
	//注入RestTemplate
	@LoadBalanced
	@Bean	
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
```

#### 3.测试

启动仪表盘:http://localhost:8092/hystrix

![image-20220812104948547](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208121049968.png)

加载出如下页面则启动成功

#### 4.访问仪表盘

在2.0以上版本需要在客户端启动类加上以下代码

```java
	@Bean
	public ServletRegistrationBean getServlet() {    
	    HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();        
	    ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);    
	    registrationBean.setLoadOnStartup(1);    
	    registrationBean.addUrlMappings("/hystrix.stream");    
	    registrationBean.setName("HystrixMetricsStreamServlet");    
	    return registrationBean;
	}
```

![image-20220812105259821](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208121053348.png)

在地址栏输入刚才的客户端地址+端口号+/hystrix.stream

点击Monitor Stream进入仪表盘页面

并同时启动客户端进行访问

![image-20220812105613438](https://raw.githubusercontent.com/NirvanaDxD/PicGoSave/main/202208121056012.png)

仪表盘开始解析服务响应数据，则搭建完成





