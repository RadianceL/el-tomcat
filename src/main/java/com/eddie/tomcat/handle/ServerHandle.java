package com.eddie.tomcat.handle;

import com.eddie.tomcat.core.ServletContainer;
import com.eddie.tomcat.provider.defaults.DefaultFaviconServlet;
import com.eddie.tomcat.test.MyServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author eddie
 * @createTime 2019-02-13
 * @description
 */
public class ServerHandle extends ChannelInboundHandlerAdapter {

    private static ServletContainer container = ServletContainer.getInstance();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        initContainer();
        System.out.println("初始化handlerServlet执行完毕");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("渠道注册成功");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("渠道激活成功");
    }

    private void initContainer(){
        container.addServlet("/", new MyServlet());
        container.addServlet("/favicon.ico", new DefaultFaviconServlet());
    }
}
