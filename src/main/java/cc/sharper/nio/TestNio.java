package cc.sharper.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by liumin3 on 2015/10/8.
 */
public class TestNio
{


    public  void tt()
    {
        try
        {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();

            ServerSocket ss = socketChannel.socket();

            ss.bind(new InetSocketAddress(80));



            SocketChannel clientChannel =  socketChannel.accept();

            clientChannel.configureBlocking(false);


            socketChannel.configureBlocking(false);


            Selector selector = Selector.open();

            socketChannel.register(selector, SelectionKey.OP_ACCEPT);

            SelectionKey key =  clientChannel.register(selector, SelectionKey.OP_ACCEPT);






        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
