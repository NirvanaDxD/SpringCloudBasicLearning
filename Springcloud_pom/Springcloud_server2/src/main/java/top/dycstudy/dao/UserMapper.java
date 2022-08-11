package top.dycstudy.dao;

import java.util.List;

import top.dycstudy.pojo.Scuser;

public interface UserMapper {
	
	Scuser selectById(Integer id); //根据id查询

	List<Scuser> findAll();//查询全部
	
}
