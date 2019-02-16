package com.eddie.tomcat.test;

import com.eddie.tomcat.handle.incloud.Request;
import com.eddie.tomcat.handle.incloud.Response;
import com.eddie.tomcat.provider.Servlet;

/**
 * @author eddie
 * @createTime 2019-02-13
 * @description
 */
public class MyServlet extends Servlet {

    @Override
    public void doGet(Request request, Response response) {
        try {
            // 获取 name 参数 并返回
            String uri = request.getUri();
            System.out.println(uri);
            response.write(request.getParameter("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        doGet(request,response);
    }
}
