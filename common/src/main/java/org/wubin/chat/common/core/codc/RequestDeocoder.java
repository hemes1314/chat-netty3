 package org.wubin.chat.common.core.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.wubin.chat.common.core.model.Request;

/**
 * 数据包解码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头    |  模块号      |  命令号    |   长度     |   数据       |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * 包头4字节
 * 模块号2字节 
 * 命令号2字节
 * 长度4字节(数据部分占有字节数量)
 * 
 * @author -wubin-
 *
 */
public class RequestDeocoder extends FrameDecoder {

    /**
     * 数据包基本长度
     */
    public static int BASE_LENGTH = 4 + 2 + 2 + 4;
    
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        
        if(buffer.readableBytes() >= BASE_LENGTH) {
            
            // 防止socket字节流攻击(数据包长度很大)，大于指定字节则跳过读取，再过来数据后则需要添加包头标识
            if(buffer.readableBytes() > 2048) {
                buffer.skipBytes(buffer.readableBytes());
            }
            
            // 第一个可读数据包的起始位置
            int beginIndex;
            
            while(true) {
                // 包头开始游标点
                beginIndex = buffer.readerIndex();
                // 标记其实读游标位置，
                buffer.markReaderIndex();// 把当前的readerIndex赋值到markReaderIndex中。
                if(buffer.readInt() == ConstantValue.HEADER_FLAG) {
                    break;
                }
                // 未读到包头标识略过一个字节,继续读取
                buffer.resetReaderIndex();// 重设readerIndex，把markIndex赋值到readerIndex, (buffer.readerIndex(markReaderIndex))
                buffer.readByte();
                
                // 不满足
                if(buffer.readableBytes() < BASE_LENGTH) {
                    return null;
                }
            }
            
            // 读取命令号
            short module = buffer.readShort();
            short cmd = buffer.readShort();
            
            // 读取数据长度
            int length = buffer.readInt();
            if(length < 0) {
                channel.close();
            }
            
            // 数据包还没到齐, 
            if(buffer.readableBytes() < length) {
                 buffer.readerIndex(beginIndex);// 到指定读指针位置
                 return null;
            }
            
            byte[] data = new byte[length];
            buffer.readBytes(data);
            
            Request message = new Request();
            message.setModule(module);
            message.setCmd(cmd);
            message.setData(data);
            return message;
        }
        return null;
    }
}
