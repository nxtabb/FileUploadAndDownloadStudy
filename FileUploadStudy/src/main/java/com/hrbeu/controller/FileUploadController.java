package com.hrbeu.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @Classname FileUploadController
 * @Description TODO
 * @Date 2021/2/20 09:51
 * @Created by nxt
 */

public class FileUploadController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = "";
        //1.判断上传的表单的普通的表单还是带文件的表单
        if(ServletFileUpload.isMultipartContent(req)){
            try {
            //如果不是普通表单，则进行处理
            //2.创建一个上传文件保存路径，建议在WEB-INF路径下，安全，且用户无法直接访问。
            String uploadPath = this.getServletContext().getRealPath("WEB-INF/upload");
            File uploadFile = new File(uploadPath);
            if(!uploadFile.exists()){
                uploadFile.mkdir();
            }
            //3.缓冲，临时文件,如果文件超过了与其大小，则过几天自动删除，或宜兴用户转为永久
            String tmpPath = this.getServletContext().getRealPath("/WEB-INF/tmp");
            File tmpFile = new File(tmpPath);
            if(!tmpFile.exists()){
               tmpFile.mkdir();
            }

            //4.处理上传文件，一般需要通过获取流再上传，可以使用request.getInputStream()十分麻烦
            //建设使用Apache的文件上传组建 common-file upload 依赖于commons-io
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024*1024);
            factory.setRepository(tmpFile);

            //2.获取ServletFileUpload
             ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
             //2.1监听文件上传进度
            servletFileUpload.setProgressListener(new ProgressListener() {
                public void update(long pBytesRead, long pContentLength, int pItems) {
                    System.out.println("总大小："+pContentLength+"已上传："+pBytesRead);
                }
            });
            //2.2处理乱码
            servletFileUpload.setHeaderEncoding("UTF-8");
            //2.3设置单个文件上传最大值
            servletFileUpload.setFileSizeMax(1024*1024*10);
            //2.4设置总共的最大值
            servletFileUpload.setSizeMax(1024*1024*30);
            //3.处理上传文件
            //3.1把前端请求封装成FileItem对象,需要从servletFileUpload中拿到的
            List<FileItem> fileItems = servletFileUpload.parseRequest(req);
            //遍历表单中的每一项
                for (FileItem fileItem : fileItems) {
                    //判断type 是不是file
                    if(fileItem.isFormField()){
                        //如果不是
                        String name = fileItem.getFieldName();
                        String value = fileItem.getString("UTF-8");
                        System.out.println(name+":"+value);
                    }
                    else {
                        //如果是file
                        //处理文件
                        //得到文件上传的名字
                        String uploadFileName = fileItem.getName();
                        if(uploadFileName.trim().equals("")||uploadFileName==null){
                            continue;
                        }
                        //文件自身的名称
                        String fileOriginName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1);
                        //扩展名
                        String extName = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
                        //文件处理完毕，存放地址编写
                        String uuid = UUID.randomUUID().toString();
                        String realPath = uploadPath+File.separator+uuid;
                        File realPathFile = new File(realPath);
                        if(!realPathFile.exists()){
                            realPathFile.mkdir();
                        }
                        //io输入输出
                        InputStream inputStream = fileItem.getInputStream();
                        FileOutputStream fos = new FileOutputStream(realPath+"/"+fileOriginName);
                        byte[] buffer = new byte[1024*1024];
                        int len = 0;
                        while ((len=inputStream.read(buffer))>0){
                            fos.write(buffer,0,len);
                        }

                        fos.close();
                        inputStream.close();
                        fileItem.delete();
                    }
                }
            } catch (FileUploadException e) {
                msg = "文件上传失败";
            }
            msg = "文件上传成功";
        }else {
            //如果是普通表单，则直接退出
            return;
        }
        req.setAttribute("msg",msg);
        req.getRequestDispatcher("info.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
