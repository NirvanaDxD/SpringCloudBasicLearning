package top.dycstudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import top.dycstudy.pojo.UserVO;


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
	
	@HystrixCommand
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
