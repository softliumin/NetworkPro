package cc.sharper.simplechat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String>
{
    //netty5 所有改变   channelRead0废除了
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception
//    {
//        System.out.println(s);
//    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception
    {
        System.out.println(msg);
    }


}
