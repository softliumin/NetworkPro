package cc.sharper.mysimplechat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by liumin3 on 2016/3/4.
 */
public class ChatClient
{
    public static void main(String[] args) throws  Exception
    {
        new ChatClient().connect("localhost",8080);

    }

    public void connect(String address,Integer port) throws  Exception
    {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        try
        {
            b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception
                    {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        pipeline.addLast("decoder", new StringDecoder());
                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast("handler", new ChatClientHandler());
                    }
                });

            Channel channel = b.connect("localhost", 8080).sync().channel();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true)
            {
                channel.writeAndFlush(in.readLine()+"\r\n");
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            eventLoopGroup.shutdownGracefully();
        }


    }
}
