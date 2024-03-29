## EasyIM简介

之前工作中研究过nchan消息推送及fastdfs分布式文件存储，由此一直想做一个web端的im,由于自己是写后端的，前端没有很好的框架，偶然间发现了layim，觉得很不错，然后就试着结合nchan及FastDFS,做一款web端的IM。nchan主要做消息服务，用FastDFS搭建文件存储服务存储聊天过程中的文件存储

EasyIM也借助了若依后台系统，在此基础上做了一些调整,希望对正在了解学习web IM的同学有所帮助;

由于LayIM授权问题，没有将LayIM代码放在里面


## IM主要功能
1.  查看聊天记录
2.  添加好友
3.  添加好友分组
4.  创建群组
5.  好友私聊
6.  好友群聊
7.  离线消息(群聊、私聊)
8.  发送文件 （图片、音频、视频、文件）
9.  上下线通知
10. 查找好友及群组
11. 删除好友
12. 退出群组
13. 好友在线状态
14. 接收消息
15. 修改备注（好友备注/群备注）
16. 消息撤回/删除(需要改动layim源码)

## 内置功能(由诺依管理系统提供)
1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 在线构建器：拖动表单元素生成相应的HTML代码。
17. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 演示图

  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/login.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/register.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/registersuccess.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/main.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/addfriend.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/allfriend.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/avatar.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/changeSkin.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/chat.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/deletemessage.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/findfriend.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/findgroup.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/friend.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/groupchat.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/groupchatjoin.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/groupmanage.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/groupmember.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/groupnew.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/insertcode.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/joingroup.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/message.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/message_input.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/offline_message.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/offlinemessage-group.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/offlinemessage.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/sessions.png)
  
  ![image](https://github.com/liunian-robert/EasyIM/blob/master/demo-picture/typemanage.png)