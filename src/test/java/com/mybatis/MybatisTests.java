package com.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.User;
import com.entity.Customer;
import com.mapper.UserMapper;
import com.mapper.CustomerMapper;
import com.service.CustomerService;
import com.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 测试mybatis, springboot 整合
 */
@MapperScan(basePackages = "com.mapper")
@SpringBootTest
@Slf4j
class MybatisTests {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private IUserService userService;
	@Autowired
	private CustomerMapper CustomerMapper;
	@Autowired
	private CustomerService customerService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		List<User> userList = userMapper.selectList(null);
		System.out.println(userList.size());
		userList.forEach(System.out::println);
	}

	@Test
	public void testCustomer() {
		System.out.println(("----- selectAll method test ------"));
		Customer customer = customerService.getById(1);
		System.out.println(customer.getCustId());
		QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("cust_id").eq("country", "china");
		List<Customer> res = customerService.list(queryWrapper);
		for (Customer r : res) {
			System.out.println(r.getCustId());
		}
	}

	@Test
	public void testUserMapper() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("email", "839910307@qq.com").or(i -> i.eq("id", "123").eq("password", "202cb962ac59075b964b07152d234b70"));
		User user = userService.getOne(queryWrapper, false);
		log.info(user.getFname());
	}



}


