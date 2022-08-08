# SpringCloud

## 第一阶段服务提供者搭建 ( 服务端 )

![image-20220805100705501](E:\typora图片\image-20220805100705501-16596652320521.png)

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

<img src="E:\typora图片\image-20220805101906174.png" alt="image-20220805101906174" style="zoom: 67%;" />

<img src="E:\typora图片\image-20220805101936479.png" alt="image-20220805101936479" style="zoom:67%;" />

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

![image-20220805102336280](E:\typora图片\image-20220805102336280.png)

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

根据id查询

![image-20220805103906786](E:\typora图片\image-20220805103906786.png)

![image-20220805103924582](E:\typora图片\image-20220805103924582.png)

![image-20220805103932150](E:\typora图片\image-20220805103932150.png)

查询全部信息

![image-20220805104039612](E:\typora图片\image-20220805104039612.png)



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

![image-20220805105322956](E:\typora图片\image-20220805105322956.png)

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

![image-20220805110314231](E:\typora图片\image-20220805110314231.png)

![image-20220805110325474](E:\typora图片\image-20220805110325474.png)

![image-20220805110336005](E:\typora图片\image-20220805110336005.png)

查询全部信息

![image-20220805110403710](E:\typora图片\image-20220805110403710.png)



