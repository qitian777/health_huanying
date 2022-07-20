package com.tian.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.entity.Result;
import com.tian.common.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
public interface ISetmealService extends IService<Setmeal> {

    Boolean saveSetmeal(Setmeal setmeal, List<Integer> checkGroupIds);

    Boolean updateSetmeal(Setmeal setmeal, List<Integer> checkGroupIds);

    PageResult getCheckGroupPage(QueryPageBean queryPageBean);

    List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    Boolean removeSetmeal(Integer id);

    Setmeal findById(int id);

    Map<String, Object> getSetmealCount();
}
