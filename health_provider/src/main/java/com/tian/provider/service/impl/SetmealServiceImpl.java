package com.tian.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.pojo.Setmeal;
import com.tian.provider.mapper.SetmealMapper;
import com.tian.provider.service.ISetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Service
@Transactional
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public Boolean saveSetmeal(Setmeal setmeal, List<Integer> checkGroupIds) {
        try {
            setmealMapper.insert(setmeal);
            if (checkGroupIds != null && checkGroupIds.size() > 0) {
                for (Integer checkGroupId : checkGroupIds) {
                    setmealMapper.setSetmealAndCheckGroup(setmeal.getId(), checkGroupId);
                }
            }
        } catch (Exception e) {
            log.error("SetmealServiceImpl.saveSetmeal ERROR:"+e.toString());
            return false;
        }
        return true;
    }

    @Override
    public Boolean updateSetmeal(Setmeal setmeal, List<Integer> checkGroupIds) {
        try{
            setmealMapper.updateById(setmeal);
            setmealMapper.deleteSetMealAndCheckGroup(setmeal.getId());
            if (checkGroupIds != null && checkGroupIds.size() > 0) {
                for (Integer checkGroupId : checkGroupIds) {
                    setmealMapper.setSetmealAndCheckGroup(setmeal.getId(), checkGroupId);
                }
            }
        }catch (Exception e){
            log.error("SetmealServiceImpl.updateSetmeal ERROR:"+e.toString());
            return false;
        }
        return true;
    }

    @Override
    public PageResult getCheckGroupPage(QueryPageBean queryPageBean) {
        Page<Setmeal> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<Setmeal> wrapper=new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(queryPageBean.getQueryString()),"code",queryPageBean.getQueryString())
                .or()
                .eq(StringUtils.isNotBlank(queryPageBean.getQueryString()),"help_code",queryPageBean.getQueryString())
                .or()
                .eq(StringUtils.isNotBlank(queryPageBean.getQueryString()),"name",queryPageBean.getQueryString());
        setmealMapper.selectPage(page,wrapper);
        return new PageResult(page.getTotal(),page.getRecords());
    }

    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(Integer id) {
        return setmealMapper.findCheckGroupIdsBySetmealId(id);
    }

    @Override
    public Boolean removeSetmeal(Integer id) {
        try{
            setmealMapper.deleteSetMealAndCheckGroup(id);
            setmealMapper.deleteById(id);
        }catch (Exception e){
            log.error("SetmealServiceImpl.removeSetmeal ERROR:"+e.toString());
            return false;
        }
        return true;
    }

    @Override
    public Setmeal findById(int id) {
        return setmealMapper.findById(id);
    }

    @Override
    public Map<String, Object> getSetmealCount() {
        List<Map<String, Object>> list = setmealMapper.getSetmealCount();
        Map<String,Object> map = new HashMap<>();
        map.put("setmealCount",list);

        List<String> setmealNames = new ArrayList<>();
        for(Map<String,Object> m : list){
            String name = (String) m.get("name");
            setmealNames.add(name);
        }
        map.put("setmealNames",setmealNames);
        return map;
    }
}
