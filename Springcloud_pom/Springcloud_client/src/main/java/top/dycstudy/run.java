package top.dycstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(run.class, args);
	}
	
	//注入RestTemplate
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
