


oss-cn-beijing.aliyuncs.com


由于springboot项目启动时会自动加载数据库，而在service_oss模块中，只需要完成上传操作，不需要加载数据库，解决方式有两种(需要解决，不然启动报错)
1、在service_oss模块的application.properties文件中加上数据库的配置
2、在service_oss模块的启动类中加入注解，使其不会自动加载数据库 @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

踩坑解决：后端实体类命名需要与数据库字段名保持一致，而需要上传到前端的实体类的属性名称，需要与前端命名保持一致，不然数据无法正常显示

由于我们在service的pom文件中引入了nacos，并且在vod模块中进行了配置，因此oss模块在启动时也会自动去加载
nacos，如果不在oss模块中也进行配置的话，oss模块启动时会报错

在写登录接口的时候使用jwt工具类报错，原因是JAXB API是java EE 的API，因此在java SE 9.0 中不再包含这个 Jar 包。
                       java 9 中引入了模块的概念，默认情况下，Java SE中将不再包含java EE 的Jar包
                       而在 java 6/7 / 8 时关于这个API 都是捆绑在一起的
由于jdk版本导致的错误，解决方法详见博客：https://www.cnblogs.com/it-tsz/p/11749651.html