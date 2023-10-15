package com.sky.controller.admin;


import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     * 前端传递过来的参数是文件类型，Spring MVC对其进行了包装，需要使用MultipartFile类型的参数来接收
     * 方法参数必须与前端传递过来的参数名称相同
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);

        try {
            //原始文件名
            String objectName = file.getOriginalFilename();
            //获取后缀名
            String lastName = objectName.substring(objectName.lastIndexOf("."));
            //修改后文件名，为保证文件名是唯一的
            String firstName = UUID.randomUUID().toString();

            objectName = firstName+lastName;
            aliOssUtil.upload(file.getBytes(), objectName);

            return Result.success(objectName);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
