package cc.sharper.mysimplechat;

import cc.sharper.simplechat.SimpleChatClientInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by liumin3 on 2016/3/4.
 */
public class ChatServer
{
    private int port;

    public ChatServer(int port)
    {
        this.port = port;
    }

    public void run() throws Exception
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try
        {

            ServerBootstrap b = new  ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatClientInitializer())
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            System.out.println("服务器启动了！");

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();


        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {


            System.out.println("服务器关闭了");
        }
    }

    public static void main(String[] args) throws Exception
    {
        new ChatServer(8080).run();
    }


}
