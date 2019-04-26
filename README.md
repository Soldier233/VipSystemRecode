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