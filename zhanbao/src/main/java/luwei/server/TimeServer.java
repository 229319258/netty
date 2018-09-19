package luwei.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author: ffq
 * @Description:
 * @Date: Create in 20:52 2018/9/2
 */
public class TimeServer {

    public void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
//                    option(ChannelOption.SO_BACKLOG, 1024) .
        childHandler(new ChannelInitializer() {
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024)).addLast(new StringDecoder()).addLast(new TimeServerHandler());
    }
});

//            绑定端口，同步等待成功
            ChannelFuture future = serverBootstrap.bind(port).sync();

            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new TimeServer().bind(8010);
    }
}
