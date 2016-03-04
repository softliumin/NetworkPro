package cc.sharper.mysimplechat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by liumin3 on 2016/3/4.
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String>
{
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception
    {
        System.out.println(msg);
    }
}
