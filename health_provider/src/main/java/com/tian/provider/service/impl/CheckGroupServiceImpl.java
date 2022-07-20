package com.tian.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.pojo.CheckGroup;
import com.tian.provider.mapper.CheckGroupMapper;
import com.tian.provider.service.ICheckGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
@Slf4j
public class CheckGroupServiceImpl extends ServiceImpl<CheckGroupMapper, CheckGroup> implements ICheckGroupService {
    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public Boolean saveCheckGroup(CheckGroup checkGroup, List<Integer> checkItemIds) {
        try{
            checkGroupMapper.insert(checkGroup);
            if (checkItemIds!=null&&checkItemIds.size()>0){
                for (Integer checkItemId : checkItemIds) {
                    checkGroupMapper.setCheckGroupAndCheckItem(checkGroup.getId(),checkItemId);
                }
            }
        }catch (Exception  e){
            log.error("CheckGroup.saveCheckGroup  ERROR:"+e.toString());
            return false;
        }
        return true;
    }

    @Override
    public PageResult getCheckGroupPage(QueryPageBean queryPageBean) {
        Page<CheckGroup> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<CheckGroup> wrapper=new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(queryPageBean.getQueryString()),"code",queryPageBean.getQueryString())
                .or()
                .eq(StringUtils.isNotBlank(queryPageBean.getQueryString()),"help_code",queryPageBean.getQueryString())
                .or()
                .eq(StringUtils.isNotBlank(queryPageBean.getQueryString()),"name",queryPageBean.getQueryString());
        checkGroupMapper.selectPage(page,wrapper);
        return new PageResult(page.getTotal(),page.getRecords());
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupMapper.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public Boolean updateCheckGroup(CheckGroup checkGroup, List<Integer> checkItemIds) {
        try{
            checkGroupMapper.updateById(checkGroup);
            checkGroupMapper.deleteCheckGroupAndCheckItemByCheckGroupId(checkGroup.getId());
            if (checkItemIds!=null&&checkItemIds.size()>0){
                for (Integer checkItemId : checkItemIds) {
                    checkGroupMapper.setCheckGroupAndCheckItem(checkGroup.getId(),checkItemId);
                }
            }
        }catch (Exception e){
            log.error("CheckGroup.updateCheckGroup  ERROR:"+e.toString());
            return false;
        }
        return true;
    }

    @Override
    public Boolean removeCheckGroup(Integer id) {
        try{
            checkGroupMapper.deleteCheckGroupAndCheckItemByCheckGroupId(id);
            checkGroupMapper.deleteById(id);
        }catch (Exception e){
            log.error("CheckGroup.removeCheckGroup  ERROR:"+e.toString());
            return false;
        }
        return true;
    }
}
