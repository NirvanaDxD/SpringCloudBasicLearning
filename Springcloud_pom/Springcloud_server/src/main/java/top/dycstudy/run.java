package top.dycstudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.dycstudy.dao")
public class run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(run.class, args);
	}

}
