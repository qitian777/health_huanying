package com.tian.common.utils;


import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:    七牛云工具类
 * @Author QiGuang
 * @Date 2022/7/13
 * @Version 1.0
 */
public class QiniuUtils {
    public static  final String url = "http://rextua32c.hn-bkt.clouddn.com/";  //七牛云图片服务器域名（有效1个月）

    public  static String accessKey = "";
    public  static String accessSecretKey = "";
    public  static String bucket = "qiqi007";

    public static boolean upload(MultipartFile file, String fileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());  //根据自己的对象空间的地址选（华南）
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传 (个人存储空间名字)
        // String bucket = "个人存储空间名字";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //删除文件
    public static boolean delete(String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        Auth auth = Auth.create(accessKey, accessSecretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
            return true;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            ex.printStackTrace();
            return false;
        }
    }
}
