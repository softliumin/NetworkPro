package cc.sharper.protocol.client;

import cc.sharper.protocol.MessageType;
import cc.sharper.protocol.struct.Header;
import cc.sharper.protocol.struct.NettyMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;


/**
 * 握手认证客户端，通道激活时，发送握手请求
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(buildLoginReq()); //发送握手请求
    }

    /**
     * 对握手返回信息进行处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        NettyMessage message = (NettyMessage) msg;

        // 如果是握手应答消息，需要判断是否认证成功
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value())
        {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0)
            {
                // 握手失败，关闭连接
                ctx.close();
            } else
            {
                System.out.println("Login is ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else
        {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginReq()
    {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.fireExceptionCaught(cause);
    }
}
