package com.zone.shiro.config;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.zone.shiro.entiry.Permission;
import com.zone.shiro.entiry.Role;
import com.zone.shiro.entiry.User;
import com.zone.shiro.service.IShiroService;

public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	IShiroService shiroService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		// 获取登录用户名
		String name = (String) principalCollection.getPrimaryPrincipal();
		// 查询用户名称
		User user = shiroService.getUserByName(name);
		// 添加角色和权限
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		List<Role> roleList = shiroService.getRoleByUserId(user.getId());
		List<Permission> permissionList = shiroService.getPermissionByUserId(user.getId());

		simpleAuthorizationInfo.addStringPermissions(permissionList.stream().map(Permission::getPermission).collect(Collectors.toSet()));
		simpleAuthorizationInfo.addRoles(roleList.stream().map(Role::getRoleName).collect(Collectors.toSet()));
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
		if (authenticationToken.getPrincipal() == null) {
			return null;
		}
		// 获取用户信息
		String name = authenticationToken.getPrincipal().toString();
		Object credentials = authenticationToken.getCredentials();
		String str = new String((char[]) credentials);
		User user = shiroService.getUserByName(name);
		if (user == null) {
			// 这里返回后会报出对应异常
			throw new DisabledAccountException();
		} else {
			// 这里验证authenticationToken和simpleAuthenticationInfo的信息
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
			return simpleAuthenticationInfo;
		}
	}

}
