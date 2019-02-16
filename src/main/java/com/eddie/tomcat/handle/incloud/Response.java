package com.eddie.tomcat.handle.incloud;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

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

    public void write(String out) throws Exception {
        try {
            if (out == null || out.length() == 0) {
                return;
            }
            // 设置 http协议及请求头信息
            FullHttpResponse response = new DefaultFullHttpResponse(
                    // 设置http版本为1.1
                    HttpVersion.HTTP_1_1,
                    // 设置响应状态码
                    HttpResponseStatus.OK,
                    // 将输出值写出 编码为UTF-8
                    Unpooled.wrappedBuffer(out.getBytes("UTF-8")));
            // 设置连接类型 为 JSON
            response.headers().set(CONTENT_TYPE, "text/json");
            // 设置请求头长度
            response.headers().set(CONTENT_LANGUAGE, response.content().readableBytes());
            // 设置超时时间为5000ms
            response.headers().set(EXPIRES, 5000);
            ctx.write(response);
        } finally {
            ctx.flush();
            ctx.close();
        }
    }
}
