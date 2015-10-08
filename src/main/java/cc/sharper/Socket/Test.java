package cc.sharper.Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by liumin3 on 2015/9/28.
 */
public class Test {

    public static void main(String[] args) throws  Exception
    {
        Socket socket = null;
        try
        {
            socket = new Socket("time.nist.gov",13);
            socket.setSoTimeout(15000);

            InputStream in = socket.getInputStream();
            StringBuffer time = new StringBuffer();
            InputStreamReader reader = new InputStreamReader(in,"ASCII");
            for (int c=reader.read();c != -1;c=reader.read())
            {
                time.append((char) c);
            }
            System.out.println(time);
        }catch(Exception e)
        {
            System.err.println(e);
        }finally {
            if(socket!= null)
            {
                try {
                    socket.close();
                }catch (IOException e)
                {
                    //todo
                }
            }
        }
    }
}
