package com.eddie.tomcat.provider.defaults;

import com.eddie.tomcat.handle.incloud.Request;
import com.eddie.tomcat.handle.incloud.Response;
import com.eddie.tomcat.provider.Servlet;

/**
 * @author eddie
 * @createTime 2019-02-16
 * @description 获取网页logo缩略图
 */
public class DefaultFaviconServlet extends Servlet {

    @Override
    public void doGet(Request request, Response response) {

    }

    @Override
    public void doPost(Request request, Response response) {
        doGet(request, response);
    }
}
