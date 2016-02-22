package cc.sharper.nio;

import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.io.IOException;

/**
 * 这里主要使用缓冲区和通道
 */
public class ChargenClient
{

    public static int DEFAULT_PORT = 19;

    public static void main(String[] args)
    {

        args = new String[2];
        args[0] = "localhost";
        args[1] = "19";
        if (args.length == 0)
        {
            System.out.println("Usage: java ChargenClient host [port]");
            return;
        }

        int port;
        try
        {
            port = Integer.parseInt(args[1]);
        } catch (RuntimeException ex)
        {
            port = DEFAULT_PORT;
        }

        try
        {
            SocketAddress address = new InetSocketAddress(args[0], port);
            SocketChannel client = SocketChannel.open(address);

            ByteBuffer buffer = ByteBuffer.allocate(74);
            WritableByteChannel out = Channels.newChannel(System.out);

            while (client.read(buffer) != -1)//读取数据并放到缓冲区
            {
                buffer.flip();//准备写入，缓冲区数据不变
                out.write(buffer);
                buffer.clear();//重置回原始状态
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}