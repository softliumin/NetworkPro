package cc.sharper.address;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by liumin3 on 2015/12/14.
 */
public class TestAddress
{
    public static void main(String[] args)
    {
        try
        {
            InetAddress address = InetAddress.getByName("gupiao.jd.com");
            //System.out.println(address);
            // www.baidu.com/61.135.169.121
            // 208.201.239.36
            InetAddress address2 =InetAddress.getByName("127.0.0.1");
            //System.out.println(address2.getHostName());

            InetAddress[] add3 =  InetAddress.getAllByName("gupiao.jd.com");

            for(InetAddress a: add3)
            {
                System.out.println(a);
            }

            InetAddress address4 = InetAddress.getLocalHost();
            System.out.println(address4);//JRA1W11T0269/10.13.24.17

            System.out.println(address4.getHostName());

        } catch (Exception ex)
        {
            System.out.println("Could not find www.baidu.com");
        }
    }

}
