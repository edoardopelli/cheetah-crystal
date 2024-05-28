#FROM --platform=linux/x86_64 public.ecr.aws/amazoncorretto/amazoncorretto:21.0.2-al2023-headful
FROM --platform=linux/x86_64 amazoncorretto:21.0.2-al2023-headful

ARG version

COPY target/crystal-$version.war /crystal.war

RUN mkdir /home/appserver && mkdir /home/crypto && yum update -y && \
	yum install -y --skip-broken curl 
	
	#telnet net-tools 


#COPY pri.key /home/crypto/pri.key
#COPY pub.key /home/crypto/pub.key


EXPOSE 8080

CMD ["java","-Xms256m", "-Xmx2048m", "-XX:MaxMetaspaceSize=256M","-XX:MetaspaceSize=32M","-Duser.timezone=UTC","-jar","/crystal.war"]
