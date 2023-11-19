package com.example.bigevent.controller;

import com.example.bigevent.Utils.AliOssUtil;
import com.example.bigevent.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        //保证文件名是唯一的，从而防止文件覆盖
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // file.transferTo(new File("C:\\Users\\yangyq03\\Desktop\\" + fileName));
        String uploadFileUrl = AliOssUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(uploadFileUrl);
    }
}
