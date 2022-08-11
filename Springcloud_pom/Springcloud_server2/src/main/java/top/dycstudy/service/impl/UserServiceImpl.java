package top.dycstudy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import top.dycstudy.dao.UserMapper;
import top.dycstudy.pojo.Scuser;
import top.dycstudy.service.UserService;

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
