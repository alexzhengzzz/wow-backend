package com;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.User;
import com.entity.Customer;
import com.mapper.UserMapper;
import com.mapper.CustomerMapper;
import com.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@MapperScan(basePackages = "com.mapper")
@SpringBootTest
class WowBackendApplicationTests {
	@Autowired
	private UserMapper userMapper;
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
	}



}


