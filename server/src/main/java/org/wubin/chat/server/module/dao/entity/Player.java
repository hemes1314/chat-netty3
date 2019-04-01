 package org.wubin.chat.server.module.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
  * 玩家实体对象
 * @author wubin
 * @date 2019/03/22
 */
 @Entity
 @Table(name = "player")
public class Player {

     @Id
     @GeneratedValue
     private long playerId;
     
     /**
      * 玩家名
      */
     private String playerName;
    
     private String password;
}
