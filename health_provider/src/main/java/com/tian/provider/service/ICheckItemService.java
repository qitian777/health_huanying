package com.tian.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.pojo.CheckItem;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
public interface ICheckItemService extends IService<CheckItem> {

    PageResult getCheckItemPage(QueryPageBean queryPageBean);
}
