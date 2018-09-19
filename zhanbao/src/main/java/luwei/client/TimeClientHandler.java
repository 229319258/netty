package luwei.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: ffq
 * @Description:
 * @Date: Create in 21:20 2018/9/2
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        发送消息
        byte[] req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();

        for (int i = 0; i < 100; i++) {
            ByteBuf reqBody = Unpooled.buffer(req.length);
            reqBody.writeBytes(req);
            ctx.writeAndFlush(reqBody);
//        ctx.write(reqBody);
        }
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println(body + " count:" + ++count);
    }


}
