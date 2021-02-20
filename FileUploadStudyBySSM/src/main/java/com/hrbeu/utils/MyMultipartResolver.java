package com.hrbeu.utils;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Classname MyMultipartResolver
 * @Description TODO
 * @Date 2021/2/20 13:35
 * @Created by nxt
 */
public class MyMultipartResolver extends CommonsMultipartResolver {
    private HttpServletRequest request;
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        upload.setSizeMax(-1);
        if (request != null) {
            HttpSession session = request.getSession();
            MyProgressListener uploadProgressListener = new MyProgressListener(session);
            upload.setProgressListener(uploadProgressListener);
        }
        return upload;
    }
    public MultipartHttpServletRequest resolveMultipart(
            HttpServletRequest request) throws MultipartException {
        this.request = request;// 获取到request,要用到session
        return super.resolveMultipart(request);
    }


    @SuppressWarnings("unchecked")
    @Override
    public MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {

        HttpSession session = request.getSession();

        String encoding = "utf-8";
        FileUpload fileUpload = prepareFileUpload(encoding);

        MyProgressListener uploadProgressListener = new MyProgressListener(session);
        fileUpload.setProgressListener(uploadProgressListener);
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        }
        catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        }
        catch (FileUploadException ex) {
            throw new MultipartException("Could not parse multipart servlet request", ex);
        }
    }
}
