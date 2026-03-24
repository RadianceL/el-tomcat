package com.eddie.tomcat.handle.incloud;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eddie
 * @createTime 2019-02-13
 * @description 请求接收器
 */
public class Request {

    private ChannelHandlerContext ctx;
    private HttpRequest httpRequest;
    private FullHttpRequest fullHttpRequest;
    private Map<String, String> bodyParams = new HashMap<>();

    public Request(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.ctx = ctx;
        this.httpRequest = httpRequest;
        // 如果是 FullHttpRequest，解析请求体
        if (httpRequest instanceof FullHttpRequest) {
            this.fullHttpRequest = (FullHttpRequest) httpRequest;
            parseBody();
        }
    }

    private void parseBody() {
        if (fullHttpRequest == null) return;

        ByteBuf content = fullHttpRequest.content();
        if (content.isReadable()) {
            String body = content.toString(CharsetUtil.UTF_8);
            // 解析 application/x-www-form-urlencoded 格式的请求体
            if (body.contains("=")) {
                String[] pairs = body.split("&");
                for (String pair : pairs) {
                    String[] kv = pair.split("=", 2);
                    if (kv.length == 2) {
                        bodyParams.put(kv[0], kv[1]);
                    }
                }
            }
        }
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
        // 先从 URL 参数中查找
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (param != null && !param.isEmpty()) {
            return param.get(0);
        }
        // 再从请求体中查找
        return bodyParams.get(name);
    }

    public String getBody() {
        if (fullHttpRequest == null) return null;
        return fullHttpRequest.content().toString(CharsetUtil.UTF_8);
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public String getHeader(String name) {
        return httpRequest.headers().get(name);
    }
}
