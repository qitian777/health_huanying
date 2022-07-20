# 在线体验

本项目为健康体检的前后台，前台为手机适配页面。在线演示地址：

前台：http://1.15.155.101:86/pages/index.html

后台：http://1.15.155.101:82/pages/main.html

# 功能简介

前台提供了体检套餐展示，用户可通过该界面进入体检订单页面，在填写手机、手机验证码、身份信息及预约日期后可提交订单，若该手机未注册会自动注册为会员。

后台实现了体检套餐及体检项目的增删改查功能。管理人员可在预约设置页面通过上传表格或手动设置每天体检人数上限，该页面也会展示每天已预约人数。统计分析项目提供最近会员注册数量统计图、套餐预约占比饼图和运营数据统计，运营数据统计支持excel和pdf两种文件格式下载导出。

# 模块功能

- health_huanying：父工程
- health_common：通用模块
- health_mobile：前台前端
- health_backend：后台前端
- health_eureka：spring-cloud服务器
- health_provider：前后台的后端，生产者
- health_jobs：定时清理垃圾图片

# 技术框架

本项目以springboot为基础整合了多种框架，主要如下：

security：后台权限管理

mybatis-plus：代码生成，数据库交互

easyexcel：excel表格相关操作

itextpdf：pdf文件操作

quartz：定时任务

redis：验证码存储

spring-cloud：模块功能分割

七牛云存储：体检套餐图片云存储

阿里云短信：手机短信登录

