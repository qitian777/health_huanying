package com.tian.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.common.pojo.Setmeal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Repository
public interface SetmealMapper extends BaseMapper<Setmeal> {

    @Insert("insert into t_setmeal_checkgroup(setmeal_id,checkgroup_id)" +
            " values(#{id},#{checkGroupId})")
    void setSetmealAndCheckGroup(Integer id, Integer checkGroupId);

    @Delete("delete from t_setmeal_checkgroup where setmeal_id = #{id}")
    void deleteSetMealAndCheckGroup(Integer id);

    @Select("select checkgroup_id from t_setmeal_checkgroup where setmeal_id = #{id}")
    List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    Setmeal findById(int id);

    @Select("select s.name,count(o.id) as value \n" +
            "  \tfrom t_order o ,t_setmeal s \n" +
            "  \twhere o.setmeal_id = s.id \n" +
            "  \tgroup by s.name")
    List<Map<String, Object>> getSetmealCount();
}
