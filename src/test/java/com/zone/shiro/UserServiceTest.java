package com.zone.shiro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.zone.shiro.entiry.User;
import com.zone.shiro.repository.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserServiceTest {
	@Autowired
	UserMapper userMapper;

	@Test
	public void userTest() {
		User user = userMapper.selectByPrimaryKey(1L);

		System.out.println(user);
	}
}
