package cc.sharper.simplechat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String>
{

    //假如客户端连接剧增，会怎样呢？
    /**
     * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
     * A closed Channel is automatically removed from the collection,
     */
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //将消息转发到其他客户端
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception
    {
        Channel incoming = ctx.channel();
        for (Channel channel : channels)
        {
            if (channel != incoming)
            {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + msg + "\n");
            } else
            {
                channel.writeAndFlush("[you]" + msg + "\n");
            }
        }
    }




    //接收到客户端连接，存入对应的channels（ChannelGroup ），通知其他人
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");

        channels.add(ctx.channel());
    }

    //客户端连接断开时，删除channels中对应的对象，通知其他人
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");

        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }

    //netty5下面的method已经废除，改为messageReceived
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception
//    { // (4)
//        Channel incoming = ctx.channel();
//        for (Channel channel : channels)
//        {
//            if (channel != incoming)
//            {
//                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
//            } else
//            {
//                channel.writeAndFlush("[you]" + s + "\n");
//            }
//        }
//    }

    //监听客户端活动
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "在线");
    }

    //监听客户端不活动
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "掉线");
    }

    //出现了异常，关闭对饮的连接
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}