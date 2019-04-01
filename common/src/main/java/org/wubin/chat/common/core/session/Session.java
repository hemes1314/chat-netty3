 package org.wubin.chat.common.core.session;

 /**
  * 会话抽象接口
 * @author wubin
 * @date 2019/03/19
 */
public interface Session {

    /**
     * 会话绑定对象
     * @return
     */
    Object getAttachment();
    
    /**
     * 绑定对象
     * @param attachment
     */
    void setAttachment(Object attachment);
    
    /**
     * 移除绑定对象
     */
    void removeAttachment();
    
    /**
     * 向会话中写入消息
     * @param message
     */
    void write(Object message);
    
    /**
     * 判断会话是否在连接中
     * @return
     */
    boolean isConnected();
    
    /**
     * 关闭
     */
    void close();
}
