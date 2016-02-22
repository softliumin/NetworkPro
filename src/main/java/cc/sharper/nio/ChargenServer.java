package cc.sharper.nio;

import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.io.IOException;

/**
 * 主要是使用通道和缓冲区
 *
 *
 */
public class ChargenServer
{
    public static int DEFAULT_PORT = 19;

    public static void main(String[] args)
    {

        int port;
        try
        {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException ex)
        {
            port = DEFAULT_PORT;
        }
        System.out.println("Listening for connections on port " + port);

        byte[] rotation = new byte[95 * 2];
        for (byte i = ' '; i <= '~'; i++)
        {
            rotation[i - ' '] = i;
            rotation[i + 95 - ' '] = i;
        }

        ServerSocketChannel serverChannel;
        Selector selector;
        try
        {
            serverChannel = ServerSocketChannel.open();

            ServerSocket ss = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            ss.bind(address);

            serverChannel.configureBlocking(false);//设置为非阻塞模式
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);//注册channel
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return;
        }

        while (true)
        {
            try
            {
                selector.select();
            } catch (IOException ex)
            {
                ex.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext())
            {

                SelectionKey key = iterator.next();
                iterator.remove();
                try
                {
                    if (key.isAcceptable())
                    {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
                        ByteBuffer buffer = ByteBuffer.allocate(74);
                        buffer.put(rotation, 0, 72);
                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');
                        buffer.flip();
                        key2.attach(buffer);//附加到通道的键上··
                    } else if (key.isWritable())
                    {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if (!buffer.hasRemaining())
                        {
                            //用下一行重新填充缓冲区
                            // Refill the buffer with the next lines
                            buffer.rewind();
                            // Get the old first character  得到上一行的首字符
                            int first = buffer.get();
                            // Get ready to change the data in the buffer  准备改变缓冲区中的数据
                            buffer.rewind();
                            // Find the new first characters position in rotation  寻找rotation中新的首字符位置
                            int position = first - ' ' + 1;
                            // copy the data from rotation into the buffer  讲数据从rotation复制到缓冲区
                            buffer.put(rotation, position, 72);
                            // Store a line break at the end of the buffer  在缓冲区末尾存贮一个行分隔符
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');
                            // Prepare the buffer for writing 准备缓冲区进行写入
                            buffer.flip();
                        }
                        client.write(buffer);
                    }
                } catch (IOException ex)
                {
                    key.cancel();
                    try
                    {
                        key.channel().close();
                    } catch (IOException cex)
                    {
                    }
                }
            }
        }
    }
}