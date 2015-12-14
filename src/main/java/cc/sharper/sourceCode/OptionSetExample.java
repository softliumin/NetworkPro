package cc.sharper.sourceCode;

import java.io.IOException;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.channels.*;

public class OptionSetExample {

  public static void main(String[] args) throws IOException {
    NetworkChannel channel = SocketChannel.open();
    channel.setOption(StandardSocketOptions.SO_LINGER, 240);
  }

}
