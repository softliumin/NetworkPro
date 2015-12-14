package cc.sharper.connection;


import java.io.*;
import java.net.*;

/**
 *
 * Created by liumin3 on 2015/12/14.
 */
public class BinarySaver
{
    public static void main (String[] args)
    {
        String[] argss = new String[1];
        argss[0] = "http://img30.360buyimg.com/jr_image/jfs/t1453/235/52887469/33762/cee0a85f/5551f1ceNa245d111.jpg";
        for (int i = 0; i < argss.length; i++)
        {
            try
            {
                URL root = new URL(argss[i]);
                saveBinaryFile(root);
            } catch (MalformedURLException ex)
            {
                System.err.println(argss[i] + " is not URL I understand.");
            } catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
    }

    public static void saveBinaryFile(URL u) throws IOException
    {
        URLConnection uc = u.openConnection();
        String contentType = uc.getContentType();
        int contentLength = uc.getContentLength();
        if (contentType.startsWith("text/") || contentLength == -1 )
        {
            throw new IOException("This is not a binary file.");
        }
        InputStream raw = uc.getInputStream();
        try
        {
            InputStream in  = new BufferedInputStream(raw);
            byte[] data = new byte[contentLength];
            int offset = 0;
            while (offset < contentLength)
            {
                int bytesRead = in.read(data, offset, data.length - offset);
                if (bytesRead == -1)
                    break;
                offset += bytesRead;
            }

            if (offset != contentLength)
            {
                throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
            }
            String filename = u.getFile();
            filename = filename.substring(filename.lastIndexOf('/') + 1);
            FileOutputStream fout = new FileOutputStream(filename);
            try
            {
                fout.write(data);
                fout.flush();
            }catch (Exception e)
            {
                e.printStackTrace();
            }finally {
                fout.close();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
