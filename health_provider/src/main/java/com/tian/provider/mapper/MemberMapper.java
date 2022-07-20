package com.tian.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.common.pojo.Member;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    @Select("select count(id) from t_member where reg_time <= #{value}")
    int getMemberCountBeforeDate(String value);

    @Select("select count(id) from t_member where reg_time = #{value}")
    Integer getMemberCountByDate(String value);

    @Select("select count(id) from t_member")
    Integer getMemberTotalCount();

    @Select("select count(id) from t_member where reg_time >= #{value}")
    Integer getMemberCountAfterDate(String value);
}
