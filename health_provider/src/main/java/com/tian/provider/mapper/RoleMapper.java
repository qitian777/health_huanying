package com.tian.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.common.pojo.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select  r.* \n" +
            "from t_role r ,t_user_role ur \n" +
            "where r.id = ur.role_id and ur.user_id = #{id}")
    Set<Role> getRolesByUserId(int id);
}
