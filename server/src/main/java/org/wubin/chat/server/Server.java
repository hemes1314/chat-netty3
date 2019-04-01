 package org.wubin.chat.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.wubin.chat.common.core.codc.RequestDeocoder;
import org.wubin.chat.common.core.codc.ResponseEncoder;

/**
 * @author wubin
 * @date 2019/03/14
 */
@Component
public class Server implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        
        // 服务类
        ServerBootstrap bootstrap = new ServerBootstrap();
        
        // boss线程监听端口， worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        
        // 设置niosocket工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));
        
        // 设置管道的工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            
            @Override
            public ChannelPipeline getPipeline() throws Exception {

                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new RequestDeocoder());
                pipeline.addLast("encoder", new ResponseEncoder());
                pipeline.addLast("helloHandler", new ServerHandler());
                return pipeline;
            }
        });
    }

}
