package cc.sharper.connection;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by liumin3 on 2015/12/14.
 */
public class HeaderViewer
{

    public static void main(String[] args)
    {
        String[] argss = new String[1];
        argss[0] = "http://www.oreilly.com";
        for (int i = 0; i < argss.length; i++) {
            try {
                URL u = new URL(argss[0]);
                URLConnection uc = u.openConnection();
                System.out.println("Content-type: " + uc.getContentType());
                if (uc.getContentEncoding() != null) {
                    System.out.println("Content-encoding: "
                            + uc.getContentEncoding());
                }
                if (uc.getDate() != 0) {
                    System.out.println("Date: " + new Date(uc.getDate()));
                }
                if (uc.getLastModified() != 0) {
                    System.out.println("Last modified: "
                            + new Date(uc.getLastModified()));
                }
                if (uc.getExpiration() != 0) {
                    System.out.println("Expiration date: "
                            + new Date(uc.getExpiration()));
                }
                if (uc.getContentLength() != -1) {
                    System.out.println("Content-length: " + uc.getContentLength());
                }


                //          Content-type: text/html; charset=utf-8
//        Date: Mon Dec 14 17:50:10 CST 2015
//        Last modified: Mon Dec 14 17:27:14 CST 2015
               //         Expiration date: Mon Dec 14 21:31:41 CST 2015
            } catch (MalformedURLException ex) {
                System.err.println(args[i] + " is not a URL I understand");
            } catch (IOException ex) {
                System.err.println(ex);
            }
            System.out.println();
        }
    }
}


