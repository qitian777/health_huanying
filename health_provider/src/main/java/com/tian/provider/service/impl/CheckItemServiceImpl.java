package com.tian.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.pojo.CheckItem;
import com.tian.provider.mapper.CheckItemMapper;
import com.tian.provider.service.ICheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Service
@Transactional
public class CheckItemServiceImpl extends ServiceImpl<CheckItemMapper, CheckItem> implements ICheckItemService {
    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * @Author QiGuang
     * @Description 分页查询
     * @Param
     */
    @Override
    public PageResult getCheckItemPage(QueryPageBean queryPageBean) {
        Page<CheckItem> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<CheckItem> wrapper=new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(queryPageBean.getQueryString()),"code",queryPageBean.getQueryString())
                .or()
                .eq(StringUtils.isNotBlank(queryPageBean.getQueryString()),"name",queryPageBean.getQueryString());
        checkItemMapper.selectPage(page,wrapper);
        return new PageResult(page.getTotal(),page.getRecords());
    }


}
