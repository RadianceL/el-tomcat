package com.eddie.tomcat.handle;

import com.eddie.tomcat.core.ServletContainer;
import com.eddie.tomcat.handle.incloud.Request;
import com.eddie.tomcat.handle.incloud.Response;
import com.eddie.tomcat.provider.Servlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;



/**
 * @author eddie
 * @createTime 2019-02-13
 * @description Tomcat服务处理器
 */
public class TomcatServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 获取Servlet容器单例
     */
    private ServletContainer container = ServletContainer.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest) msg;
            // 转交给我们自己的request实现
            Request request = new Request(ctx, httpRequest);
            // 转交给我们自己的response实现
            Response response = new Response(ctx, httpRequest);

            String path = request.getPath();
            Servlet servlet = container.getServlet(path);

            String method = request.getMethod();
            // 实际业务处理
            System.out.println("获取本次请求的方法：" + method);
            switch (method){
                case "GET":
                    servlet.doGet(request,response);
                    break;
                case "POST":
                    servlet.doPost(request,response);
                    break;
                case "PUT":
                    servlet.doGet(request,response);
                    break;
                default:
                    servlet.doPost(request,response);
                    break;
            }

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        throw new RuntimeException(cause);
    }
}
