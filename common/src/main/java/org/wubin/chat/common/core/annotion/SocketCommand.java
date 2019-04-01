 package org.wubin.chat.common.core.annotion;

 /**
  * 请求命令
 * @author wubin
 * @date 2019/03/21
 */
public @interface SocketCommand {

    /**
     * 请求的命令号
     * @return
     */
    short cmd();
}
