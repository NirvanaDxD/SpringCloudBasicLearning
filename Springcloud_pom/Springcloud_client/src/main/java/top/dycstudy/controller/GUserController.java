package top.dycstudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import top.dycstudy.pojo.UserVO;


@RestController
@RequestMapping("client")
public class GUserController {
	
	//注入restTemplate模板工具
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("{id}")
	public UserVO queryOneUser(@PathVariable("id")Integer id){
		//获取host和port注册url
		String S_url = "http://localhost:8091/index/"+id;
		//提交请求获取相应数据
		UserVO uv = restTemplate.getForObject(S_url, UserVO.class);
		System.out.println(uv);
		System.out.println(S_url);
		return uv;
	}
	
	@GetMapping("findAll")
	public List<UserVO> findAll(){
		//获取host和port注册url
		String S_url = "http://localhost:8081/findAll/";
		//提交请求获取相应数据
		List<UserVO> list = restTemplate.getForObject(S_url,List.class);
		System.out.println(list);
		System.out.println(S_url);
		return list;
	}
	
	
}
