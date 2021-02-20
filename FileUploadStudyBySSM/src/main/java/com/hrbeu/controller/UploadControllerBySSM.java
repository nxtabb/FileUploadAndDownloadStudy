package com.hrbeu.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @Classname UploadControllerBySSM
 * @Description TODO
 * @Date 2021/2/20 11:57
 * @Created by nxt
 */
@RestController
public class UploadControllerBySSM {
    @PostMapping("/upload")
    public String upload(@RequestParam("files") List<CommonsMultipartFile> files, HttpServletRequest request) throws IOException {
        for (CommonsMultipartFile file : files) {
            String fileOriginName = file.getOriginalFilename();
            if(fileOriginName.trim().equals("")){
                return "false";
            }
            System.out.println("文件名："+fileOriginName);
            String uuid = UUID.randomUUID().toString();
            String path = request.getServletContext().getRealPath("/WEB-INF/upload");
            File realPath = new File(path);
            if(!realPath.exists()){
                realPath.mkdir();
            }
            System.out.println("上传文件路径"+realPath);
            InputStream inputStream = file.getInputStream();
            File testFile = new File(realPath,fileOriginName);
            System.out.println(testFile);
            OutputStream os = new FileOutputStream(testFile);
            int len = 0;
            byte[] buffer = new byte[1024];
            len = inputStream.read(buffer);
            while (len!=-1){
                os.write(buffer,0,len);
                os.flush();
                len = inputStream.read(buffer);
                System.out.println(request.getSession().getAttribute("upload_ps"));
            }
            os.close();
            inputStream.close();
        }

        return "success";


    }
}
