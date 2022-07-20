package com.tian.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.common.pojo.CheckGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Repository
public interface CheckGroupMapper extends BaseMapper<CheckGroup> {
    @Insert("insert into t_check_group_check_item(checkgroup_id,checkitem_id)" +
            " values(#{checkGroupId},#{checkItemId})")
    void setCheckGroupAndCheckItem(int checkGroupId,int checkItemId);

    @Select("select checkitem_id from t_check_group_check_item where checkgroup_id = #{id}")
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    @Delete("delete from t_check_group_check_item where checkgroup_id = #{id}")
    void deleteCheckGroupAndCheckItemByCheckGroupId(int id);
}
