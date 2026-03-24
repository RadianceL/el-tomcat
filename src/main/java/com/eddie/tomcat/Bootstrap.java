package com.eddie.tomcat;

import com.eddie.tomcat.config.ServerConfig;
import com.eddie.tomcat.handle.ServerHandle;
import com.eddie.tomcat.handle.TomcatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.AttributeKey;

/**
 * @author eddie
 * @createTime 2019-02-13
 * @description 启动类
 */
public class Bootstrap {

    public static void main(String[] args) {
        ServerConfig config = ServerConfig.getInstance();
        int port = config.getPort();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, config.isKeepAlive());
            bootstrap.childAttr(AttributeKey.newInstance("childAttr"), "childAttrValue");
            bootstrap.handler(new ServerHandle());
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new HttpResponseEncoder());
                    ch.pipeline().addLast(new HttpRequestDecoder());
                    // 添加 HttpObjectAggregator 以支持 POST 请求体解析
                    ch.pipeline().addLast(new HttpObjectAggregator(65536));
                    ch.pipeline().addLast(new TomcatServerHandler());
                }
            });
            System.out.println("EL-Tomcat 启动成功，监听端口: " + port);
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
