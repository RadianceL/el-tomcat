package com.eddie.tomcat.handle.incloud;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * @author eddie
 * @createTime 2019-02-13
 * @description 返回
 */
public class Response {

    private ChannelHandlerContext ctx;

    private HttpRequest r;

    public Response(ChannelHandlerContext ctx, HttpRequest r) {
        this.ctx = ctx;
        this.r = r;
    }

    public void write(String out) {
        write(out, HttpResponseStatus.OK, "text/plain; charset=UTF-8");
    }

    public void write(String out, HttpResponseStatus status, String contentType) {
        try {
            if (out == null) {
                out = "";
            }
            // 设置 http协议及请求头信息
            FullHttpResponse response = new DefaultFullHttpResponse(
                    // 设置http版本为1.1
                    HttpVersion.HTTP_1_1,
                    // 设置响应状态码
                    status,
                    // 将输出值写出 编码为UTF-8
                    Unpooled.wrappedBuffer(out.getBytes(StandardCharsets.UTF_8)));
            // 设置连接类型
            response.headers().set(CONTENT_TYPE, contentType);
            // 设置内容长度
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            // 设置连接保持
            response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.write(response);
        } finally {
            ctx.flush();
        }
    }

    public void writeError(HttpResponseStatus status, String message) {
        String html = "<html><body><h1>" + status.code() + " " + status.reasonPhrase() + "</h1><p>" + message + "</p></body></html>";
        write(html, status, "text/html; charset=UTF-8");
    }
}
