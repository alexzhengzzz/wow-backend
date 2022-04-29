# WOW backend project using springboot
* front end repo [Front-end](https://github.com/YunfanXu/wow-carRental)
## Tech Stack
### Framework
* springboot2.X (backend framework)
* maven (package dependency manager)
* mybatis (ORM framework)
  * mybatis-plus (plugin)

### Storage
* mysql8.0
* redis

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

# Git flow
![](https://aleximgzzzz.oss-cn-shanghai.aliyuncs.com/myblog/202204281212342.png)

# JWT token / redis / Spring AOP
* Authorization and Authentication
![](https://aleximgzzzz.oss-cn-shanghai.aliyuncs.com/myblog/202204290128932.png)