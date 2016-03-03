package cc.sharper.protocol.server;

import cc.sharper.protocol.MessageType;
import cc.sharper.protocol.struct.Header;
import cc.sharper.protocol.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class HeartBeatRespHandler extends ChannelHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception
    {
        NettyMessage message = (NettyMessage) msg;
        // 返回心跳应答消息
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value())
        {
            System.out.println("接收客户端发来的心跳检测信息： " + message);
            NettyMessage heartBeat = buildHeatBeat();//
            System.out.println("发送心跳响应信息给客户端： "+ heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else
            ctx.fireChannelRead(msg);
    }

    private NettyMessage buildHeatBeat()
    {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }

}
