 package org.wubin.chat.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.wubin.chat.common.core.model.Request;
import org.wubin.chat.common.core.model.Response;
import org.wubin.chat.common.core.session.Session;
import org.wubin.chat.common.core.session.SessionImpl;
import org.wubin.chat.common.module.ModuleId;
import org.wubin.chat.server.scanner.Invoker;
import org.wubin.chat.server.scanner.InvokerHolder;

/**
 * @author wubin
 * @date 2019/03/19
 */
public class ServerHandler extends SimpleChannelHandler {

    /**
     * 接收消息
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        Request request = (Request)e.getMessage();
        handlerMessage(new SessionImpl(ctx.getChannel()), request);
    }
    
    /**
     * 消息处理
     * @param session
     * @param request
     */
    private void handlerMessage(Session session, Request request) {
        
        Response response = new Response(request);
        
        System.out.println("module:" + request.getModule() + "   " + "cmd:" + request.getCmd());
        
        // 获取命令执行器
        Invoker invoker = InvokerHolder.getInvoker(request.getModule(), request.getCmd());
        if(invoker != null) {
            // 假如是玩家模块传入channel参数，否则传入playerId参数
            if(request.getModule() == ModuleId.PLAYER) {
                invoker.invoke(session, request.getData());
            } else {
                Object attachment = session.getAttachment();
                if(attachment != null) {
//                    Player player = (Player)attachment;
                }
            }
        }
    }
    
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
         super.channelDisconnected(ctx, e);
    }
}
