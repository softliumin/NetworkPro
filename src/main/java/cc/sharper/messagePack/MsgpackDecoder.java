package cc.sharper.messagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * Created by liumin3 on 2016/3/3.
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf>
{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception
    {
        final byte[] array;

        final int  length = msg.readableBytes();

        array = new byte[length];

        msg.getBytes(msg.readerIndex(),array,0,length);

        MessagePack msgPack = new MessagePack();

        out.add(msgPack.read(array));
    }
}
