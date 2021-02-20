package com.hrbeu.utils;

import org.apache.commons.fileupload.ProgressListener;
import sun.net.ProgressEvent;

import javax.servlet.http.HttpSession;

/**
 * @Classname MyProgressListene
 * @Description TODO
 * @Date 2021/2/20 13:39
 * @Created by nxt
 */
public class MyProgressListener implements ProgressListener {
    private HttpSession session;
    public MyProgressListener() {
    }
    public MyProgressListener(HttpSession _session) {
        session=_session;
        ProgressEntity ps = new ProgressEntity();
        session.setAttribute("upload_ps", ps);
    }
    public void update(long pBytesRead, long pContentLength, int pItems) {
        ProgressEntity ps = (ProgressEntity) session.getAttribute("upload_ps");
        ps.setpBytesRead(pBytesRead);
        ps.setpContentLength(pContentLength);
        ps.setpItems(pItems);
        //更新
        session.setAttribute("upload_ps", ps);
    }



}
