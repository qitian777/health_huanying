package com.tian.backend.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.constant.RedisConstant;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.entity.Result;
import com.tian.common.pojo.Setmeal;
import com.tian.common.utils.QiniuUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/12
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String url = "http://health-provider-consumer/setmeal";

    /**
     * @Author QiGuang
     * @Description 图片上传
     * @Param
     */
    @PostMapping(value = "/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file) {
        //原始文件名称比如 aa.jpg
        String originalFilename = file.getOriginalFilename() ;
        //将原始名称修改为：唯一文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        //上传文件，上传到哪呢？图片服务器七牛云
        //把图片发放到距离图片最近的服务器上，降低我们自身服务器的带宽消耗
        boolean upload = QiniuUtils.upload(file, fileName);
        if (upload){
            //上传成功
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    /**
     * @Author QiGuang
     * @Description 新增
     * @Param
     */
    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkGroupIds){
        Boolean b = restTemplate.postForObject(url+"/add/"+ Arrays.toString(checkGroupIds), setmeal, Boolean.class);
        if (b != null && b) {
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        }
        return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
    }

    /**
     * @Author QiGuang
     * @Description 编辑
     * @Param
     */
    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    @PostMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkGroupIds){
        Boolean b = restTemplate.postForObject(url+"/edit/"+ Arrays.toString(checkGroupIds), setmeal, Boolean.class);
        if (b != null && b) {
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        }
        return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
    }

    /**
     * @Author QiGuang
     * @Description 分页查询
     * @Param
     */
    @PreAuthorize("hasAuthority('SETMEAL_QUERY')")
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return restTemplate.postForObject(url+"/findPage",queryPageBean,PageResult.class);
    }

    /**
     * @Author QiGuang
     * @Description 根据检查套餐id查询对应的所有检查项id
     * @Param
     */
    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(Integer id){
        List<Integer> list= restTemplate.getForObject(url+"/findCheckGroupIdsBySetmealId/"+id, List.class);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }

    /**
     * @Author QiGuang
     * @Description 删除
     * @Param
     */
    @PreAuthorize("hasAuthority('SETMEAL_DELETE')")
    @GetMapping("/delete")
    public Result delete(Integer id){
        Boolean b = restTemplate.getForObject(url + "/delete/" + id, Boolean.class);
        if (b != null && b) {
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        }
        return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
    }
}
