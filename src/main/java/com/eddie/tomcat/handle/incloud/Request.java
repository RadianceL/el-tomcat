package com.eddie.tomcat.handle.incloud;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;


import java.util.List;
import java.util.Map;

/**
 * @author eddie
 * @createTime 2019-02-13
 * @description 请求接收器
 */
public class Request {

    private HttpRequest httpRequest;

    public Request(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getUri() {
        return httpRequest.uri();
    }

    public String getMethod() {
        return httpRequest.method().name();
    }

    public String getPath(){
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.path();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (null == param) {
            return null;
        } else {
            return param.get(0);
        }
    }
}
