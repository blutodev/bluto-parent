#基于docker images中的java tag为8-jdk的镜像
FROM jdk-8u171:20190517
#把当前项目下test8080-1.0.jar 改名为test.jar 添加到镜像中
ADD bluto-fc-0.0.1-SNAPSHOT.jar bluto-fc-0.0.1.jar
#指定端口,最好写与项目配置的端口
EXPOSE 8082
#在镜像中运行/test.jar包,这样在运行镜像的时候就已经启动好了test.jar
ENTRYPOINT [ "java",  "-jar", "/bluto-fc-0.0.1.jar" ]
