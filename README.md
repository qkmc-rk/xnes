# xnes
> 这是一个毕业设计作品：
### 校内二手交易生活平台
> 非常简单，使用springboot搭建，thymeleaf模板，又有一些前后端分离的味道在里面

## 20190320
* 修复bug连接mysql 时区问题
* 修复所有的springboot下daoimpl中jdbcTemplate注入问题

## 20190321
- 修复邮件发送失败的问题
- 修改邮件内容
- 删除victory.jpg,增加victory.png
- 修改主页中心区域字体
- 修复个人中心BUG
- 修复主页昵称不显示问题
- 修复登录注销时出现的BUG
- 修复usercertify页面无法上传文件的BUG
- 修复管理员登录注销等众多BUG

## 20190322
- 修复发布页面显示BUG
- 修复所有页面用户头像与用户名显示问题
- 赏金 -> 金额
- 修复一大堆不知名的BUGS.....
- 修复我的发布页面当内容为空时模板渲染错误的问题
- 修复性别导致的个人信息修改失败的BUG
- 修复我的发布页面的各种BUG...
- 修复管理员的两个显示页面的BUG..

## 20190323
- 新增文件上传为七牛云上传，配置文件在classpath下的qiniuyun.properties

## 20190324
- 修改发布上传文件的大小和超时时间，大小限制为5M，超时限制为60s.优化上传文件速度体验