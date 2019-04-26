# VipSystem
 
[![](https://www.jitpack.io/v/com.gitee.Soldier233/VipSystemRecode.svg)](https://www.jitpack.io/#com.gitee.Soldier233/VipSystemRecode)

## 简介
VipSystem可以帮你自主管理服务器的vip，支持限时，永久，可切换的vip。本插件使用带索引的SQLite或MySQL进行数据存储，支持自定义开启/关闭连接池，保证了兼容性和高效性。

## 命令

| 命令 | 用途 | 权限 |
| :-: | :-: | :-:|
|/vipsys me | 查看你的vip情况 | 无 |
|/vipsys changevip | 切换已开通的Vip | visys.changevip |
|/vipsys give [玩家名] [Vip组名] [时间] | 给予玩家Vip(时间格式为xdxhxmxs，x天 x小时 x分 x秒) | vipsys.give |
|/vipsys remove [玩家名] | 移除玩家的Vip | vipsys.remove |
|/vipsys reload | 重载插件 | vipsys.reload |

## 用法介绍
### 基础用法
#### 准备工作
将插件安装至服务器，重启  
如果你需要使用中文版的插件，请找到```/plugins/VipSystem/config.yml```  
将文件中的```lang: en```改为```lang: zh_CN```  
并在服务器输入命令```/vipsys reload```以重载  
你不需要额外配置默认组，插件会自动根据权限组插件返回默认组  
创建好对应的vip组待命，例如创建一个组名为vip的组  
#### 发放Vip
设玩家名为 Test 需要发放给Test 30天的vip权限组  
则输入命令```/vipsys give Test vip 30d```  
玩家可以使用```/vipsys me```来查看自己的vip情况  
若需要移除vip，只需管理员输入```/vipsys remove Test```即可移除  
#### 使用占位符
如果你需要使用VipSystem提供的占位符，你需要安装PlaceholderAPI插件  
并下载 [VipSystemPlaceholderExtension](https://static.zhanshi123.me/vipsystem/VipSystemPlaceholderExtension.jar) 并安装到 ```/plugins/PlaceholderAPI/expansions/```中  
输入命令```/papi reload```以载入占位符支持  
然后你就可以使用占位符了，占位符映射如下  

| 占位符 | 用途 |
| :-: | :-: | 
| %vipsystem_vip% | vip组名 |
| %vipsystem_expire% | 到期时间 |
| %vipsystem_previous% | 开通之前的组名 |

### 进阶用法
#### 语言设置
插件内置了英语和中文，你可以将配置文件中的```lang```项目的值改为```zh_CN```或```en```来使用插件内置的两种语言  
你也可以自定义语言，将```lang```的值改为语言名之后，重载插件，插件会在```messages```目录下多生成一个以英文为模板的语言文件，你可以自主修改并重载生效语言文件
#### UUID模式
如果你的服务器是正版服务器  
建议你在配置文件中启用UUID模式```uuid: true```  
#### 数据库
插件默认使用HikariCP连接池连接SQLite数据库，如果没有特殊需要，你无需改动该部分配置文件  
如果你的服务端无法使用默认配置文件启动，并且报错中有hikarcp字样，请修改```usePool```的值为```false```  
如果你需要使用MySQL，你可以设置```useMySQL```的值为```true```并在下方配置数据库地址等信息
#### 全局Vip
如果你使用GroupManager作为权限组插件，无特殊需要，不需要更改此部分配置  
如果你使用PermissionsEx作为权限组插件，并且默认配置下vip无法生效到全部世界，请将```isGlobal```的值设置为```false```，如果仍无法生效到全部世界，请在```worlds```下手动添加你所有的世界名，例如
```
worlds:
  - world
  - world_nether
  - world_the_end
```
#### 自定义到期时间格式
如果需要更改日期格式，可以参考[https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html](https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html)的占位符进行修改
#### 自定义命令
你可以自定义vip开通/到期时的命令，下面是默认的配置文件
```
customCommands:
  vip:
    activate:
      - 'say {0} has activated vip!'
      - 'give {0} minecraft:diamond'
    expire:
      - 'say {0} is no longer a vip'
  svip:
    activate:
      - 'say {0} has activated svip!'
      - 'give {0} minecraft:diamond'
    expire:
      - 'say {0} is no longer a svip'
```
意思是，在vip开通时，执行下方命令，发送消息并给玩家一个钻石
```
say {0} has activated vip!
give {0} minecraft:diamond
```
在vip到期时，执行下方命令，发送消息
```
say {0} is no longer a vip
```
svip组同理，如果你有更多的vip种类，可以按照上方格式自行添加  
如果不需要使用该功能，可以直接整项删除
### 插件下载
改插件为免费插件，但是不提供jar的直接下载地址  
你可以clone/fork该项目并自行构建，只需安装maven后进入项目目录，输入```mvn install```即可自动完成构建。目标文件会生成在```/target```目录下  
如果你没有自行构建的能力，你可以进入[https://www.spigotmc.org/resources/vipsystem-premium.66740/](https://www.spigotmc.org/resources/vipsystem-premium.66740/)支付$3或￥20下载文件  
所有支付的款项都是对作者开发极大的支持!
### API
你可以通过```VipSystemAPI.getInstance()```来获取```VipSystemAPI```的实例  
更多内容待补充
### 联系作者
如果你想提交bug，你可以发送issue或加群563012939联系群主获取帮助  
你同样也可以通过email联系 Email: [i@zhanshi123.me](mailto:i@zhanshi123.me)