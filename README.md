# World of Wheels 

<!-- [START badges] -->
![Springboot](https://img.shields.io/badge/Springboot-2.7.0--SNAPSHOT-brightgreen)![Mybatis](https://img.shields.io/badge/Mybatis-3.5.2-yellow)![](https://img.shields.io/badge/Mybatis--plus-3.5.2-red)![Kafka: 2.8.4 (shields.io)](https://img.shields.io/badge/Kafka-2.8.4-orange)![Redis: 6.2.6 (shields.io)](https://img.shields.io/badge/Redis-6.2.6-red)![Mysql: 8.0.29 (shields.io)](https://img.shields.io/badge/Mysql-8.0.29-blue)![JDK: 11 (shields.io)](https://img.shields.io/badge/JDK-11-9cf)![Docker: compose (shields.io)](https://img.shields.io/badge/Docker-compose-informational)

<!-- [END badges] -->

[Front-End](https://github.com/YunfanXu/wow-carRental)

> World of Wheels is a modern website which provides customers with excellent rental car services ! 

## Getting Started

### Installation Using Docker-Compose file

To use our backend of website. 

```bash
sh deploy.sh
```
Remember to make sure that the docker is running, maven and java is preinstalled in the server.



## Backend layer ( MVC)
1. Controller
3. Business 
    * deliver **VO**
    * receive **DTO**
5. Service
6. Mapper 
    * **entity/DO**

![](https://aleximgzzzz.oss-cn-shanghai.aliyuncs.com/myblog/202204281102198.png)

## DB Model(tmp)
* logical model
![](https://aleximgzzzz.oss-cn-shanghai.aliyuncs.com/lc-python/202204181551813.png)
* relational model
![](https://aleximgzzzz.oss-cn-shanghai.aliyuncs.com/lc-python/202204181555253.png)

## Git flow

![](https://aleximgzzzz.oss-cn-shanghai.aliyuncs.com/myblog/202204281212342.png)



## JWT token / redis / Spring AOP

JWT token format:
  * request header or request param
      * Authorization: Bearer {token}

### Authorization and Authentication Flow
* summary
![](https://aleximgzzzz.oss-cn-shanghai.aliyuncs.com/myblog/202204290128932.png)
* aop checker
![](https://aleximgzzzz.oss-cn-shanghai.aliyuncs.com/myblog/202204291919768.png)

## Features

### Safety

* JWT token
* MD5 Encryption(password)

### Tools

* postman (API testing)

### Mock data

* javaFaker lib
* UUID

### API doc

* swagger2





