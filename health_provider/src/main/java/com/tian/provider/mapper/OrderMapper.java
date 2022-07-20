package com.tian.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.common.pojo.Order;
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
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select m.name `member` ,s.name setmeal,o.order_date orderDate,o.order_Type orderType\n" +
            "  from\n" +
            "  t_order o,\n" +
            "  t_member m,\n" +
            "  t_setmeal s\n" +
            "  where o.member_id=m.id and o.setmeal_id=s.id and o.id=#{id}")
    Map<String, Object> findById(Integer id);

    @Select("select count(id) from t_order where order_date = #{value}")
    Integer getOrderCountByDate(String value);

    @Select("select count(id) from t_order where order_date >= #{value}")
    Integer getOrderCountAfterDate(String value);

    @Select("select count(id) from t_order where order_date = #{value} and order_status = '已到诊'")
    Integer getVisitsCountByDate(String value);

    @Select("select count(id) from t_order where order_date >= #{value} and order_status = '已到诊'")
    Integer getVisitsCountAfterDate(String value);

    @Select(" select \n" +
            "      s.name, \n" +
            "      count(o.id) setmeal_count ,\n" +
            "      count(o.id)/(select count(id) from t_order) proportion\n" +
            "    from t_order o inner join t_setmeal s on s.id = o.setmeal_id\n" +
            "    group by o.setmeal_id\n" +
            "    order by setmeal_count desc \n" +
            "  \tlimit 0,4")
    List<Map> getHotSetmeal();
}
