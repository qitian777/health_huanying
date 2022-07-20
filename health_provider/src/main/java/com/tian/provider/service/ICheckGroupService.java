package com.tian.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.pojo.CheckGroup;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
public interface ICheckGroupService extends IService<CheckGroup> {

    Boolean saveCheckGroup(CheckGroup checkGroup, List<Integer> checkItemIds);

    PageResult getCheckGroupPage(QueryPageBean queryPageBean);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    Boolean updateCheckGroup(CheckGroup checkGroup, List<Integer> checkItemIds);

    Boolean removeCheckGroup(Integer id);
}
