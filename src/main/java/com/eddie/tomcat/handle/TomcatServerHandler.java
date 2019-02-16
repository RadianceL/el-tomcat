package com.eddie.tomcat.handle;

import com.eddie.tomcat.handle.incloud.Request;
import com.eddie.tomcat.handle.incloud.Response;
import com.eddie.tomcat.test.MyServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;



/**
 * @author eddie
 * @createTime 2019-02-13
 * @description Tomcat服务处理器
 */
public class TomcatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest){
            HttpRequest r = (HttpRequest) msg;

            // 转交给我们自己的request实现
            Request request = new Request(ctx,r);
            // 转交给我们自己的response实现
            Response response = new Response(ctx,r);
            // 实际业务处理
            MyServlet.class.newInstance().doGet(request,response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }


}
