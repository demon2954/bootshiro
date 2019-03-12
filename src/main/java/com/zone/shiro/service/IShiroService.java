package com.zone.shiro.service;

import java.util.List;

import com.zone.shiro.entiry.Permission;
import com.zone.shiro.entiry.Role;
import com.zone.shiro.entiry.User;

public interface IShiroService {
	public List<Role> getRoleByUserId(long userId);

	public List<Permission> getPermissionByUserId(long userId);
	
	public User getUserByName(String userName);
}
