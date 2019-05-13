# VipSystemEx

[![](https://www.jitpack.io/v/com.gitee.Soldier233/VipSystemRecode.svg)](https://www.jitpack.io/#com.gitee.Soldier233/VipSystemRecode)

## Description
VipSystemEx can do you a favor to manage the Vip of your server. The plugin supports permanent, timed, switchable vip group.  
VipSystemEx use cached MySQL or SQLite with indexes as database, supporting enable/disable connection pool(default on), which ensures the compatibility and the efficiency.

## Commands

| Commands | Description | Permission |
| :-: | :-: | :-:|
|/vipsys me | Get your vip details | Null |
|/vipsys changevip | Change activated vip group | vipsys.changevip |
|/vipsys give [Player] [Group] [Time] | Give vip(Time format: xdxhxmxs, x days x hours x minutes x seconds) | vipsys.give |
|/vipsys remove [Player] | Remove vip | vipsys.remove |
|/vipsys reload | Reload the plugin | vipsys.reload |

## Instructions
### Fundamental Usage
#### Preparations
Install the plugin to server,and restart.    
You don't need to configure the default group.The plugin will set the default group automatically according to your permission plugin.      
Create a vip group named as vip for example.  
Note: If you have installed previous version of VipSystem, the data will be imported from the old version.  
#### Give Vip
Assuming that the player to be given vip is Test.You need to give Test a 30-day vip group.    
Use the command```/vipsys give Test vip 30d```  
The player can use ```/vipsys me``` to check himself vip detail.    
To remove vip, use```/vipsys remove Test```.  
#### Placeholder
If you need to use placeholder provided by VipSystemEx, you need to install plugin PlaceholderAPI.    
Use command```/papi ecloud download vipsystem```to load extension.  
Then you can use the placeholders.  
Below are the mappings.  

| Placeholder | Description |
| :-: | :-: | 
| %vipsystem_vip% | vip group name |
| %vipsystem_expire% | vip expire date |
| %vipsystem_previous% | group name before activating vip |

### Advanced Usage
#### Language
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
## 插件下载
该插件为免费插件，但是不提供jar的直接下载地址  
你可以clone/fork该项目并自行构建，只需安装maven后进入项目目录，输入```mvn install```即可自动完成构建。目标文件会生成在```/target```目录下  
如果你没有自行构建的能力，你可以进入[https://www.spigotmc.org/resources/vipsystem-premium.66740/](https://www.spigotmc.org/resources/vipsystem-premium.66740/)支付$3或￥20下载文件  
人民币支付请加群联系群主 下方有联系方式  
所有支付的款项都是对作者开发极大的支持!
## API
你可以通过```VipSystemAPI.getInstance()```来获取```VipSystemAPI```的实例  
更多内容待补充
## 联系作者
如果你想提交bug，你可以发送issue或加群563012939联系群主获取帮助  
你同样也可以通过email联系 Email: [i@zhanshi123.me](mailto:i@zhanshi123.me) 