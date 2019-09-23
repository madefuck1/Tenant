# Stone 石头 V2.1.0 - 互联网 SaaS 敏捷开发框架 ，一款免费开源的JAVA互联网云快速开发平台。

#### 如果对您有帮助，您可以点右上角 "Star" 支持一下 ，谢谢！

### 介紹
基于SpringBoot2.1的 SaaS 权限管理系统，包含租户管理、数据源管理 和 子系统管理，可根据需要，为不同租户分配不同的子系统和数据源，蓝本是 ruoyi 易读易懂、界面简洁美观，没有任何重度依赖；
核心技术采用Spring Boot、MyBatis、Druid、Shiro、JWT、Thymeleaf、Lombok、Fastjson。

#### 框架说明

1、导入项目之前请先安装 lombok 插件，方法自行百度；

2、自创数据库级别动态数据源：默认一个平台库（主库），用于保存用户、角色、权限 和 系统等基础数据，其中系统管理包含系统的应用信息和数据源信息，框架可以根据系统标识自动加载对应的数据源信息；

举个简单的栗子： 有个项目包含系统管理（部门、用户、角色、权限 和 系统）、CMS和CRM三个小系统，虽然功能不复杂，但由于使用率超高，导致数据量很大。这时候就可以使用动态数据源分别配置三个库，平台库、CMS库和CRM库。<br>

数据库级别动态数据源比常规多数据源更具灵活性，并且可以做到统一管理；对事务处理也没有任何入侵性。

觉得本项目不错，麻烦点个 Star哦。

####  V-1.0.0 已实现功能

1、用户管理：用户是系统操作者，该功能主要完成系统用户配置；

2、部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限；

3、岗位管理：配置系统用户所属担任职务；

4、菜单管理：配置系统菜单，操作权限，按钮权限标识等；

5、角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分；

6、字典管理：对系统中经常使用的一些较为固定的数据进行维护；

7、参数管理：对系统动态配置常用参数；

8、通知公告：系统通知公告信息发布维护；

9、操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询；

10、登录日志：系统登录日志记录查询包含登录异常；

11、在线用户：当前系统中活跃用户状态监控；

12、定时任务：在线（添加、修改、删除)任务调度包含执行结果日志；

13、系统接口：根据业务代码自动生成相关的API接口文档；

14、服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息；

15、连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈；

####  技术视频分享 （链接: https://pan.baidu.com/s/12rMXHU8CVlb1UqBdcb-Dng 提取码: ri6v ）

1、Java 基础视频
2、设计模式讲解
3、系统架构视频
4、SpringCloud 视频
5、MongoDB


####  V-2.0.0 租户模式，支持一套系统给多个客户使用（已完成）：

1、子系统管理（配置构成平台的系统/模块）；

2、租户管理（管理配置租户信息）；

2、数据路由组件，可根据租户以及子系统，将数据的增删查路由到不同的数据库；


####  V-2.1.0 集成JWT，支持小程序或者App鉴权（已完成，专业版功能，需要可以联系我）：

1、后台无改动，登录 及 权限仍是交由Shiro控制；

2、加入 JWT，并做了轻封装，支撑小程序、App 及 前后端分离 鉴权；
   演示环境体验地址：http://113.108.163.210:8849/doc.html
   未登录（没有获取到Token），系统提示：没有找到名为Authorization的header；
   已登录（获取到Token），可正常访问系统中所有的RestController。


####  V-3.0.0 前后端分离版

1、 技术架构改造，计划往Spring Cloud Alibaba方向发展；

2、 集成ES作为数据查询中心；

#### 相关资料及数据库脚本请加QQ群：531346979， 点击加入 <a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=ccf29a49380e1f1ab94e78e6c818658749ffa73a39aa823822319195fa64172d"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="Stone快速开发平台" title="Stone快速开发平台"></a>


#### 授权
使用 AGPLv3 开源，请务必遵守 AGPLv3 的相关条款


####  体验地址：http://113.108.163.210:8849/login

账号密码：admin / admin123    、   test / 123456        

####  更新不易，请喝咖啡

![WAI](https://gitee.com/justime/stone/raw/master/wxzs.jpg)


####  推荐另外一个项目：https://gitee.com/justime/Movie-ElasticSearch-RHLC

对ES客户端进行了轻封装，极大的降低了使用ES搜索引擎的门槛