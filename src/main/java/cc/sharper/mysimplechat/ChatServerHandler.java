package cc.sharper.mysimplechat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by liumin3 on 2016/3/4.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String>
{

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception
    {
        Channel incoming = ctx.channel();

        for (Channel channel : channels)
        {
            if (channel != incoming )
            {
                channel.writeAndFlush("["+incoming.remoteAddress()+"]" +msg +"\n");
            }else
            {
                channel.writeAndFlush("[你]"+msg+ "\n");
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();

        channels.writeAndFlush("[服务器]"+incoming.remoteAddress()+"加入\n");

        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();

        channels.writeAndFlush("[服务器]"+incoming.remoteAddress()+"离开\n");

        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();
        System.out.println("聊天客户端"+ incoming.remoteAddress() +"在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();
        System.out.println("聊天客户端"+incoming.remoteAddress() +"掉线");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        Channel incoming = ctx.channel();
        System.out.println("聊天客户端" + incoming.remoteAddress()+"异常");

        cause.printStackTrace();
        ctx.channel();
    }
}
