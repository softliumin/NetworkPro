package cc.sharper.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by liumin3 on 2015/12/16.
 */
public class SocketServer
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket ss = new ServerSocket(2015);
            while (true)
            {
                Socket socket = ss.accept();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String result = bufferedReader.readLine();
                System.out.println(result);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
