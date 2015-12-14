package cc.sharper.connection;

import java.io.*;
import java.net.*;


/**
 * Created by liumin3 on 2015/12/14.
 */
public class TestConnection {
    public static void main(String[] args)
    {
        String[] argss = new String[1];
        argss[0] = "http://gupiao.jd.com/find/";
        if  (argss.length > 0)
        {
            try
            {
                // Open the URLConnection for reading
                URL u = new URL(argss[0]);
                URLConnection uc = u.openConnection();
                String ty = uc.getContentType();//text/html;charset=GBK
                System.out.println("ContentType:"+ty);
                System.out.println("=====================================");
                System.out.println(uc.getExpiration());
                System.out.println(uc.getLastModified());
                System.out.println(uc.getExpiration());
                System.out.println(uc.getDate());
                System.out.println(uc.getContentEncoding());//内容编码----》这玩意和字符编码不同
                System.out.println("=====================================");
                InputStream raw = uc.getInputStream();
                try
                { // autoclose

                    InputStream buffer = new BufferedInputStream(raw);
                    // chain the InputStream to a Reader
                    Reader reader = new InputStreamReader(buffer);
                    int c;
                    while ((c = reader.read()) != -1)
                    {
                       // System.out.print((char) c); //
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    raw.close();
                }

            } catch (MalformedURLException ex)
            {
                System.err.println(argss[0] + " is not a parseable URL");
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
