package cc.sharper.Socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by liumin3 on 2015/9/28.
 */
public class ShowLocalSocketPort {
    public static void main(String[] args) {

        String host = "localhost";
        for(int i=0;i<1024;i++)
        {
            try
            {
                Socket socket = new Socket(host,i);
                System.out.println("this is a server on port "+i+" of "+ host);
            }catch (UnknownHostException ex)
            {
                System.err.println(ex);
                break;
            }catch (IOException ex)
            {
                //这个端口不是一个服务器
            }
        }

    }
}
