package com.eddie.tomcat.handle;

import com.eddie.tomcat.core.ServletContainer;
import com.eddie.tomcat.provider.defaults.DefaultFaviconServlet;
import com.eddie.tomcat.test.MyServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author eddie
 * @createTime 2019-02-13
 * @description 服务器处理器初始化
 */
public class ServerHandle extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandle.class);
    private static ServletContainer container = ServletContainer.getInstance();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        initContainer();
        logger.info("Servlet 容器初始化完成");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.debug("Channel 注册成功");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.debug("Channel 激活成功");
    }

    private void initContainer(){
        // 注册 Servlet
        container.addServlet("/api/hello", new MyServlet());
        container.addServlet("/favicon.ico", new DefaultFaviconServlet());

        // 注册静态资源处理器（处理根路径和其他静态资源）
        StaticResourceHandler staticHandler = new StaticResourceHandler();
        container.addServlet("/", staticHandler);
        container.addServlet("/index.html", staticHandler);
    }
}
