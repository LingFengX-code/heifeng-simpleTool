package com.heifeng.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: XLF
 * @Date: 2021/06/22/10:16
 * @Description: 服务器启动类
 *  该服务器就是用于创建并初始化服务器启动对象 ServerBootStrap
 */
public class HttpServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup,childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpChannelInitializer());
        ChannelFuture future = bootstrap.bind(8888).sync();
        future.channel().closeFuture().sync();

        //关闭组
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
