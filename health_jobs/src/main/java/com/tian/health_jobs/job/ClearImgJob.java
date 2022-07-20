package com.tian.health_jobs.job;

import com.tian.common.constant.RedisConstant;
import com.tian.common.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/13
 * @Version 1.0
 */
@Component
@Slf4j
public class ClearImgJob {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Scheduled(cron = "0 0 2 * * ?")
    public void myTimes() {
        Set<String> set = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set != null){
            log.info("开始删除七牛云中多余图片----------");
            for (String picName : set) {
                System.out.println(picName);
                //删除七牛云服务器上的图片
                QiniuUtils.delete(picName);
                //从Redis集合中删除图片名称
                redisTemplate.opsForSet().remove(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                log.info("已删除图片："+picName);
            }
            log.info("删除完毕------------------------");
        }
    }
}
