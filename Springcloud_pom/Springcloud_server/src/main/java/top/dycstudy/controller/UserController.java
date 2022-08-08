package top.dycstudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.dycstudy.dao.UserMapper;
import top.dycstudy.pojo.Scuser;


@RestController
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	
	@GetMapping("index/{id}")
	public Scuser select(@PathVariable("id")Integer id){
		Scuser user = userMapper.selectById(id);
		return user;
	}
	
	@RequestMapping("findAll")
	public List<Scuser> findAll(){
		List<Scuser> list = userMapper.findAll();
		return list;
	}
	
}
