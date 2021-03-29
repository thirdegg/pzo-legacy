package Server;

import Base.MessageSystem;
import Frontend.MsgUserSignal;
import org.jboss.netty.channel.*;
import MapObjects.Units.Player;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class NettyServerHandler extends SimpleChannelUpstreamHandler {
    public static Map<Integer, Player> sessionIdToUserSession = new HashMap<>();
    public static Map<Integer, Channel> chanels = new HashMap<>();
    MessageSystem ms;
    private boolean _debug;

    public NettyServerHandler(MessageSystem ms) {
        _debug = true;
        this.ms = ms;
    }

    public static void sendMsgClient(String s, int id) {
        if(s.length()<=0)return;
       if(s.charAt(s.length()-1)!='\0')s = s + "\0";
        Channel ch = chanels.get(id);
        if (ch != null) ch.write(s);
    }
    public static void sendMsgAllClients(String s) {
        s = s + "\0";
        for(Channel ch:chanels.values()){
        ch.write(s);}
    }
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            //log(e.toString());
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        // Send greeting for a new connection.
        //e.getChannel().write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        //e.getChannel().write("It is " + new Date() + " now.\r\n");
        //log(e.toString());
        //ctx.getChannel().getConfig().setConnectTimeoutMillis(300000);
        int idchanel = e.getChannel().getId();
        chanels.put(idchanel, e.getChannel());
        Player pl = new Player(idchanel);
        sessionIdToUserSession.put(idchanel, pl);
        log("Client connected from " + ctx.getChannel().getRemoteAddress() + " (" + ctx.getChannel().getId() + ")");
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        Player pl=sessionIdToUserSession.get(e.getChannel().getId());
        if (pl.autorized) {
            ms.sendMessage(new MsgRemoveUser(null, ms.getAddressService().getAddressGameMechanics(),pl));
        }
        chanels.remove(pl.idchanel);
        sessionIdToUserSession.remove(pl.idchanel);
        log("Connection closed from " + ctx.getChannel().getRemoteAddress() + " (" + ctx.getChannel().getId() + ")");
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        String ss = null;
        try {
            ss = new String(e.getMessage().toString().getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            return;
        }
        if (ss.charAt(0) == '1')
            ms.sendMessage(new MsgUserSignal(null, ms.getAddressService().getAddressGameMechanics(), e.getChannel().getId(), ss));
        else
            ms.sendMessage(new MsgToSocketF(null, ms.getAddressService().getAddressFrontend(), e.getChannel().getId(), ss));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        log("Error (" + ctx.getChannel().getRemoteAddress() + "): " + e.getCause() + " (" + ctx.getChannel().getId() + ")");
        e.getChannel().close();
    }

    private void log(String txt) {
        if (_debug) {
            System.out.print("NettyServerHandler: " + txt + "\n");
        }
    }
}
