package com.hrbeu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Classname DownloadControllerBySSM
 * @Description TODO
 * @Date 2021/2/20 12:57
 * @Created by nxt
 */
@RestController
public class DownloadControllerBySSM {
    @GetMapping("/download")
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getServletContext().getRealPath("/WEB-INF/upload");
        String fileName = "1.png";
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
        File file = new File(path,fileName);
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        len = inputStream.read(buffer);
        while (len!=-1){
            outputStream.write(buffer,0,len);
            outputStream.flush();
            len = inputStream.read(buffer);
        }
        outputStream.close();
        inputStream.close();
        return "success";

    }
}
