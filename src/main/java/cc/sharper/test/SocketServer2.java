package cc.sharper.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * 加上多线程的时间服务端
 * Created by liumin3 on 2016/1/29.
 */
public class SocketServer2
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket socket = new ServerSocket(2015);

            while(true)
            {
                try
                {
                    Socket connection = socket.accept();
                    ThreadA a = new ThreadA(connection);
                    a.start();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
//    private static class  ThreadA extends Thread
//    {
//        private Socket connection;
//
//        ThreadA(Socket connection)
//        {
//            this.connection = connection;
//            System.out.println("新的线程："+ Thread.currentThread().getName()+ ":" + Thread.currentThread().getId());
//        }
//
//        @Override
//        public void run()
//        {
//            try
//            {
//                BufferedReader bufferedReader = new BufferedReader(
//                        new InputStreamReader(connection.getInputStream()));
//                String result = bufferedReader.readLine();
//                System.out.println(result);
//
//                Writer writer =new OutputStreamWriter(connection.getOutputStream());
//                writer.write("去死吧，客户端\r\n");
//                writer.flush();
//
//
//            } catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }

}


class  ThreadA extends Thread
{
    private Socket connection;

    ThreadA(Socket connection)
    {
        this.connection = connection;
        // this.getName() Thread.currentThread().getName()
        System.out.println("新的线程："+ Thread.currentThread().getName()+ ":" + Thread.currentThread().getId());
    }

    @Override
    public void run()
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String result = bufferedReader.readLine();
            System.out.println(result);

            Writer writer =new OutputStreamWriter(connection.getOutputStream());
            writer.write("去死吧，客户端\r\n");
            writer.flush();

            System.out.println(Thread.currentThread().getName());

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}