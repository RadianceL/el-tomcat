package com.eddie.tomcat.provider;

import com.eddie.tomcat.handle.incloud.Request;
import com.eddie.tomcat.handle.incloud.Response;

/**
 * @author eddie
 * @createTime 2019-02-13
 * @description
 */
public abstract class Servlet {

    public abstract void doGet(Request request, Response response);

    public abstract void doPost(Request request,Response response);
}
