package com.lexizhi.qiniu.service;

import com.alibaba.fastjson.JSONObject;
import com.lexizhi.qiniu.util.FileUtil;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author: laoyu
 * @Date: 2019/12/9 15:36
 * @Description:
 */
@Service
public class QiniuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QiniuService.class);
    // 设置好账号的ACCESS_KEY和SECRET_KEY
    public String ACCESS_KEY="JWK7sWfpWtDZZiTjKnJIIndmi6Tbntizn1Jw5iaA";
    public String SECRET_KEY="-aw9b425sUNPtUQHEWpbevqcQVzVbIJcxzg4yN9F";
    // 要上传的空间
    public String bucketname="laoyu-images";

    //秘钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //构造一个带指定Zone对象的配置类，不同的七牛云存储区域调用不同的zone
    //zone1-->华北
    Configuration cfg = new Configuration(Zone.zone1());
    // ...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);

    //域名
    private static String QINIU_IMAGE_DOMAIN = "http://qiniu.lexizhi.com/";


    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if ((dotPos < 0)) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            //判断是否是合法的文件后缀
            if (!FileUtil.isFileAllowed(fileExt)) {
                return null;
            }
            //设置最终保存的文件名
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;

            //调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());

            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                LOGGER.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (Exception e) {
            // 请求失败时打印的异常的信息
            LOGGER.error("七牛异常:" + e.getMessage());
            return null;
        }
    }
}
