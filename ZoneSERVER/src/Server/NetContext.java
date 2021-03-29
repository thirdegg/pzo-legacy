package Server;

import org.jboss.netty.channel.Channel;

public class NetContext {
    public Channel channel;
    public String message;

    public NetContext(Channel channel, String message) {
        this.channel = channel;
        this.message = message;
    }
}
