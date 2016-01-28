package cc.sharper.Socket;

import java.net.*;
import java.io.*;

public class SocketInfo
{

    public static void main(String[] args)
    {
        args = new String[2];
        args[0] = "www.jd.com";
        args[1] = "www.qq.com";
        for (String host : args)
        {
            try
            {
                Socket theSocket = new Socket(host, 80);
                System.out.println("Connected to " + theSocket.getInetAddress()
                        + " on port " + theSocket.getPort() + " from port "
                        + theSocket.getLocalPort() + " of "
                        + theSocket.getLocalAddress());
            } catch (UnknownHostException ex)
            {
                System.err.println("I can't find " + host);
            } catch (SocketException ex)
            {
                System.err.println("Could not connect to " + host);
            } catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
    }
}