package com.zone.shiro.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zone.shiro.entiry.Permission;
import com.zone.shiro.entiry.Role;
import com.zone.shiro.entiry.User;
import com.zone.shiro.repository.PermissionMapper;
import com.zone.shiro.repository.RoleMapper;
import com.zone.shiro.repository.UserMapper;
import com.zone.shiro.service.IShiroService;

@Service
public class ShiroServiceImpl implements IShiroService {
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<Role> getRoleByUserId(long userId) {
		return roleMapper.selectByUserId(userId);
	}

	@Override
	public List<Permission> getPermissionByUserId(long userId) {
		List<Role> roleList = roleMapper.selectByUserId(userId);
		List<Long> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toList());
		return permissionMapper.getPermissionsByRoleIds(roleIds);
	}

	@Override
	public User getUserByName(String userName) {
		return userMapper.getUserByName(userName);
	}

}
