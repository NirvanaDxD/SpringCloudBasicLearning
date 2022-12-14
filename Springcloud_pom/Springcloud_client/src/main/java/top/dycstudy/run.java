package top.dycstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

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
	
	@Bean
	public ServletRegistrationBean getServlet() {    
	    HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();        
	    ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);    
	    registrationBean.setLoadOnStartup(1);    
	    registrationBean.addUrlMappings("/hystrix.stream");    
	    registrationBean.setName("HystrixMetricsStreamServlet");    
	    return registrationBean;
	}
}
