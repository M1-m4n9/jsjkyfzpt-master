# 计算机考研辅助平台

#### 介绍
计算机考研辅助平台的后端代码仓库

查看前端代码请点击[计算机考研辅助平台的前端代码仓库](https://gitee.com/hhy-cn/kaoyan.git)

查看后端管理系统的前端界面请点击[后端管理系统的前台](https://gitee.com/isleeboer/jsjkyfzpt-admin.git)

#### 仓库结构

├─doc (文档类)

│  ├─design (设计文档)

│  │  └─sql   (数据库设计)   

│  └─week_report     (周报和监督报告)

├─environments (环境类) 

├─work （每周作业）

├─ky-admin (后台管理系统) 

├─ky-college （院校信息系统） 

├─ky-common  （系统共用的资源集合）

├─ky-doubt （疑点解答(论坛) 系统）

├─ky-gateway  (网关)

├─ky-person  （个人中心系统）

├─ky-policy （政策法规系统）

├─ky-study （学习资源系统）

│  LICENSE

│  README.md 

│  pom.xml
     
#### 🎯 重要功能逻辑图

##### 文件上传

- 普通方式
    
    由用户将文件传送到服务器,再由服务器上传到oss

![输入图片说明](https://foruda.gitee.com/images/1663640752729523712/6d5c1b26_9863753.png "屏幕截图")

- 获取签名后直传 （本系统）
  
  用户向服务器请求上传policy，服务器返回policy和签名，然后用户直接上传到oss

![输入图片说明](https://foruda.gitee.com/images/1663641298120614689/35251e52_9863753.png "屏幕截图")  


##### ky-policy实现redis分页缓存功能
 
- 使用zset + hash完成 大大提高了速率
   
  使用zset保存id和时间戳(得分),不用list,set的原因就是因为不能排序

![在这里插入图片描述](https://img-blog.csdnimg.cn/43726ce4d7f749ccbea34a0451ae4297.png)
   
  使用hash来保存每个数据，field为id，value为对象字符串

![在这里插入图片描述](https://img-blog.csdnimg.cn/e4736912e3f14c3e9c8f185ff2a98470.png)

- 分页查询逻辑 
  
   每次通过score排名来在zset选取ids再去hash里面寻找
        
     
