package com.tian.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.common.pojo.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("select  p.* \n" +
            "from t_permission p ,t_role_permission rp \n" +
            "where p.id = rp.permission_id and rp.role_id = #{id}")
    Set<Permission> getPerssionByRoleId(int id);
}
