package cc.sharper.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 加上了线程池的服务端
 * Created by liumin3 on 2016/1/29.
 */
public class SocketServer3
{
    public static void main(String[] args)
    {
        ExecutorService pool = Executors.newFixedThreadPool(50);

        try
        {
            ServerSocket socket = new ServerSocket(2015);

            while (true)
            {
               Socket connection =  socket.accept();
                Callable<Void> task = new ThreadB(connection);
                pool.submit(task);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class  ThreadB  implements Callable<Void>
{
    private Socket connection;
    ThreadB(Socket connection)
    {
        this.connection = connection;
    }

    public Void call()
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
        } catch (IOException ex)
        {
            System.err.println(ex);
        } finally
        {
            try
            {
                connection.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}