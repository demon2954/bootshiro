package com.zone.shiro.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zone.shiro.entiry.Permission;

public interface PermissionMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Permission record);

	int insertSelective(Permission record);

	Permission selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Permission record);

	int updateByPrimaryKey(Permission record);

	List<Permission> getPermissionsByRoleIds(@Param("ids") List<Long> ids);
}