package com.lexizhi.qiniu.controller;

import com.lexizhi.qiniu.service.QiniuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: laoyu
 * @Date: 2019/12/9 15:25
 * @Description:
 */
@Controller
public class IndexController {
    @Resource
    private QiniuService qiniuService;

    @PostMapping(value = "/testUpload")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "error";
        }

        try {
            String fileUrl = qiniuService.saveImage(file);
            return "success,imageUrl = " + fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }


    @GetMapping(value = {"/index"})
    public String index() {
        return "index";
    }
}
