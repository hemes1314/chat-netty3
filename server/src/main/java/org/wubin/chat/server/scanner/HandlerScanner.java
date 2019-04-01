 package org.wubin.chat.server.scanner;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.wubin.chat.common.core.annotion.SocketCommand;
import org.wubin.chat.common.core.annotion.SocketModule;

/**
 * @author wubin
 * @date 2019/03/19
 */
@Component
public class HandlerScanner implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
         return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<? extends Object> clazz = bean.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        
        if(interfaces != null && interfaces.length > 0) {
            // 扫描类的所有借口父类
            for(Class<?> interFace : interfaces) {
                
                // 判断是否为handler接口类
                SocketModule socketModule = interFace.getAnnotation(SocketModule.class);
                if(socketModule == null) {
                    continue;
                }
                
                Method[] methods = interFace.getMethods();
                if(methods != null && methods.length > 0) {
                    
                    for(Method method : methods) {
                        
                        SocketCommand socketCommand = method.getAnnotation(SocketCommand.class);
                        if(socketCommand == null) {
                            continue;
                        }
                        
                        final short module = socketModule.module();
                        final short cmd = socketCommand.cmd();
                        
                        if(InvokerHolder.getInvoker(module, cmd) == null) {
                            InvokerHolder.addInvoker(module, cmd, Invoker.valueOf(method, bean));
                        } else {
                            System.out.println("重复命令："+module+" "+"cmd:"+cmd);
                        }
                    }
                }
            }
        }
        return null;
    }
}
