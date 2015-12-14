package cc.sharper.connection;

import java.io.*;
import java.net.*;

/**
 * Created by liumin3 on 2015/12/14.
 */
public class EncodingAwareSourceViewer {

    public static void main (String[] args)
    {
        String[] argss = new String[1];
        argss[0] = "https://www.baidu.com";
        for (int i = 0; i < argss.length; i++)
        {
            try
            {
                // set default encoding
                String encoding = "ISO-8859-1";
                URL u = new URL(argss[i]);
                URLConnection uc = u.openConnection();
                String contentType = uc.getContentType();
                int encodingStart = contentType.indexOf("charset=");
                if (encodingStart != -1)
                {
                    encoding = contentType.substring(encodingStart + 8);
                }
                InputStream in = new BufferedInputStream(uc.getInputStream());

                Reader r = new InputStreamReader(in, encoding);//  这里获取的是GBK
                int c;
                while ((c = r.read()) != -1)
                {
                    System.out.print((char) c);
                }
                r.close();
            } catch (MalformedURLException ex)
            {
                System.err.println(args[0] + " is not a parseable URL");
            } catch (UnsupportedEncodingException ex)
            {
                System.err.println("Server sent an encoding Java does not support: " + ex.getMessage());
            } catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
    }
}
