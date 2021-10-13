package com.abc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author xlf
 */
public class SomeServer {
    public static void main(String[] args) throws InterruptedException {
        //用于处理客户端连接请求，连接上后将请求发送给childGroup中的eventLoop进行处理
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        //用于处理客户端请求
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            //用于配置整个netty代码，将各个组件关联起来
            ServerBootstrap bootstrap = new ServerBootstrap();
            //指定eventGroup
            bootstrap.group(parentGroup, childGroup)
                    //指定使用NIO进行通信
                    .channel(NioServerSocketChannel.class)
                    //指定处理器 让childGroup中的eventLoop绑定的线程来处理的处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        // 当Channel初始创建完毕后就会触发该方法的执行，用于初始化Channel
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加Http编解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 添加大块数据Chunk处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 添加Chunk聚合处理器
                            pipeline.addLast(new HttpObjectAggregator(4096));
                            // 添加WebSocket协议转换处理器
                            pipeline.addLast(new WebSocketServerProtocolHandler("/some"));
                            // 添加自定义处理器
                            pipeline.addLast(new SomeServerHandler());
                        }
                    });
            //绑定端口号
            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("服务器启动成功。监听的端口号为：8888");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
