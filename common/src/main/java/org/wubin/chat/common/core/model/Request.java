 package org.wubin.chat.common.core.model;

 /**
  * 消息对象
 * @author wubin
 * @date 2019/03/18
 */
public class Request {

    /**
     * 模块号
     */
    private short module;
   
    /**
     * 命令号
     */
    private short cmd;
    
    /**
     * 数据
     */
    private byte[] data;

    public short getModule() {
        return module;
    }

    public void setModule(short module) {
        this.module = module;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
