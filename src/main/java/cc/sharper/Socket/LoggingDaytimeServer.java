package cc.sharper.Socket;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 * Created by sharper on 2015/12/14.
 */
public class LoggingDaytimeServer
{
    public final static int PORT = 13;
    private final static Logger auditLogger = Logger.getLogger("requests");
    private final static Logger errorLogger = Logger.getLogger("errors");

    public static void main(String[] args)
    {

        ExecutorService pool = Executors.newFixedThreadPool(50);

        try
        {
                ServerSocket server = new ServerSocket(PORT);
            while (true)
            {
                try
                {
                    Socket connection = server.accept();
                    Callable<Void> task = new DaytimeTask(connection);
                    pool.submit(task);
                } catch (IOException ex)
                {
                    errorLogger.log(Level.SEVERE, "accept error", ex);
                } catch (RuntimeException ex)
                {
                    errorLogger.log(Level.SEVERE, "unexpected error " + ex.getMessage(), ex);
                }
            }
        } catch (IOException ex)
        {
            errorLogger.log(Level.SEVERE, "Couldn't start server", ex);
        } catch (RuntimeException ex)
        {
            errorLogger.log(Level.SEVERE, "Couldn't start server: " + ex.getMessage(), ex);
        }
    }

    private static class DaytimeTask implements Callable<Void>
    {

        private Socket connection;

        DaytimeTask(Socket connection)
        {
            this.connection = connection;
        }

        //@Override
        public Void call()
        {
            try
            {
                Date now = new Date();
                // write the log entry first in case the client disconnects
                //先写入日志记录，以防万一客户端断开连接
                auditLogger.info(now + " " + connection.getRemoteSocketAddress());

                Writer out = new OutputStreamWriter(connection.getOutputStream());
                out.write(now.toString() +"\r\n");
                out.flush();
            } catch (IOException ex)
            {
                // client disconnected; ignore; 客户端断开连接
            } finally
            {
                try
                {
                    connection.close();
                } catch (IOException ex)
                {
                    // ignore;
                }
            }
            return null;
        }
    }
}
