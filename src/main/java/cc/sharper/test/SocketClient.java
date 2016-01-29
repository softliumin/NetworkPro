package cc.sharper.test;

import java.io.*;
import java.net.Socket;

/**
 * Created by liumin3 on 2015/12/16.
 */
public class SocketClient
{
    public static void main(String[] args)
    {
        try
        {
            for (int x=0 ;x<100;x++)
            {
                String msg = "你好，服务器,我是时间都客户端传输来的数据！";
                Socket socket = new Socket("localhost",2015);

                socket.setSoTimeout(5000);

                OutputStream out = socket.getOutputStream();
                Writer writer = new OutputStreamWriter(out, "UTF-8");
                writer = new BufferedWriter(writer);
                writer.write(msg+"\r\n");
                writer.flush();

                InputStream in = socket.getInputStream();

                Reader reader = new InputStreamReader(in,"UTF-8");

                BufferedReader   reader2 = new BufferedReader(reader);

                String result = reader2.readLine();
                System.out.println(result);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
