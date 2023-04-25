# 一、技术栈规约
| 类别 |	技术选型  |版本 |	备注 |
|--|--|--|--|
| JDK |  Oracle| 	8|低于这版本的需要升级 |
|IDE | IDEA| 	| |
| 构建工具	 | Redis		| | |
| 分布式缓存 |  Oracle| 	8|低于这版本的需要升级 |
| 配置管理 | Nacos	| 	| |
| Spring体系| SpringBoot | 	2.3.12.RELEASE|更好适配SpingCloud |
| Spring体系 |  SpringCloud| Hoxton.SR12|低于这版本的需要升级 |
| Spring体系 	 | SpringCloudAlibaba| 2.2.8.RELEASE|低于这版本的需要升级 |
| 连接池 |  	Druid| 	| |
|ORM |  	MybatisPlus		| 	| |
| 安全|  	SpringSecurity		| 	| |

# 二、命名规范

 - 命名使用英文词组合,严禁使用中文拼音或拼音首字母组合命名
 - group、package包名前缀为com.sicnu
 - 包名第三位为系统分类名,如com.sicnu.admin
 - 常量名都要大写,单词间以下划线隔开
 - 其他命名遵循驼峰命名
1.类名：首字母大写的UpperCamelCase，如UserService
2.方法名、变量名：首字母小写的lowerCamelCase，如phoneNumber
 - 特定标识命名
1.领域模型增加类型后缀标识，如xxVO、xxDTO
2.基类/抽象类使用Base/Abstract等前缀标识
3.Controller、Service、Mapper统一添加到对应分层目录
4.接口实现类后面增加Impl标识
# 三、注释规范
 - 所有类的注释都要使用Javadoc规范，使用/** 内容 */格式不得使用 // xxx格式
![在这里插入图片描述](https://img-blog.csdnimg.cn/1058b7c3929e480f841c5c4a4bdc1e83.png)

 - 所有方法的注释都要使用Javadoc规范，同时需要特别注明参数、返回值,如果是存在抛出异常也需特别说明
![在这里插入图片描述](https://img-blog.csdnimg.cn/afce09f4615043b5aed92a326c3acda7.png)

 - 需要暂时不使用的类可以使用@deprecated表示已经弃用,使用@see来指向替代这个方法的方法
 - 方法内部注释时，如果使用 // 应另起一行不要在这行末尾
![在这里插入图片描述](https://img-blog.csdnimg.cn/47cad35d90114823a4d49083e884a9df.png)

 - 代码修改时，注释也要进行修改
# 四、异常与日志
 - 调用外部服务等可能产生异常的代码块，用try/catch代码块捕获异常并在catch中记录跟踪日志及业务逻辑处理
 - 禁止吞掉异常信息
 - 对trace/debug/info级别的日志输出，必须使用条件输出形式或者使用占位符的方式。
 - 谨慎记录日志
# 五、逻辑代码规范
 - 废弃无用的代码一律删除，查看历史代码可通过git操作
 - 接口类的方法无需采用public修饰
 - 常用字符串统一放在常量类中
 - If/else/while后面必须跟上大括号即使只有一行代码
 - 单个代码不要过长，如果过长应尽量拆分
 - 避免空指针异常
 - 能使用lambda尽量使用
 - 运算符的左右应加上一个空格
# 六、MySQL数据库与ORM
 - 表名、字段名必须使用小写字母或数字，禁止出现数字开头，禁止两个下划线中间只 出现数字
 - 多表之间如果有联系禁止使用外键
 - 字段允许适当冗余，以提高查询性能
 - 如果存入数据长度大于5000应使用text而不是varchar
 - 可以适当建立索引提高查询效率，但不应过多
 - 避免SQL语句导致索引失效
 - 不要使用count(列名)来代替count(*)
 - 使用#{}来代替${}，避免造成SQL注入
 - 更新数据时候，update_time也需要更新
 - 事务不要乱用，避免造成效率降低
# 七、结果检查
 - 使用单元测试Junit，不能直接在该类中创建main()方法测试
 - IDEA中安装代码质量检查插件
![在这里插入图片描述](https://img-blog.csdnimg.cn/0d4610c735d64807a63e809285d3c909.png)

 - 上传代码时候处理掉错误
# 八、安全规范
 - 用户敏感数据禁止直接展示，应脱敏处理
 - 用户传入的参数都需要进行校验
 - 发帖、评论时应注意敏感词过滤等
 - 数据库保存的密码应是加密过后的密码
 - 避免跨域问题
 - 禁止侵犯他人资源
 - 拥护国家，遵守国家相关规定
# 注：未提及的一些规范，按照《阿里巴巴开发规范》
