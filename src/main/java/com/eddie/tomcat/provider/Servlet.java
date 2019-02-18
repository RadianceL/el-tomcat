package com.eddie.tomcat.provider;

import com.eddie.tomcat.handle.incloud.Request;
import com.eddie.tomcat.handle.incloud.Response;

/**
 * @author eddie
 * @createTime 2019-02-13
 * @description
 */
public abstract class Servlet {

    /**
     * 处理Get请求
     * @param request
     * @param response
     */
    public abstract void doGet(Request request, Response response);

    /**
     * 处理Post请求
     * @param request
     * @param response
     */
    public abstract void doPost(Request request, Response response);

    /**
     * 处理Put请求
     * @param request
     * @param response
     */
    public void doPut(Request request, Response response){}

    /**
     * 处理Delete请求
     * @param request
     * @param response
     */
    public void doDelete(Request request, Response response){}
}
