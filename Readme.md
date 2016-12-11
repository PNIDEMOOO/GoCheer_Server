# 枸杞后端 GoCheer_server
> 枸杞的后端逻辑

[TOC]
## 接口
### `/checkUsername`用户名查重  
检测用户名是否已存在。
调用方法：

	gocheer.donggu.me/checkUsername?username=<待检测用户名>

使用 GET 方法，返回的为json的`false`或`true`。其中`false`表示用户名已存在，`true`表示用户名不存在可以注册。  

### `/userInfo`获取用户基本信息
获取当前登录的用户的信息。  
#### 调用方法：
	gocheer.donggu.me/userInfo

使用 GET 方法，返回 json。  

#### 样例  
##### 查询当前登录用户信息（已登录）
``` json
{
  "user": {
    "score": 9,
    "gender": false,
    "wordsum": 9,
    "registerTime": "2016-12-11 14:15:01",
    "alias": "陈导",
    "scoresum": 0,
    "email": "hhhhhh@hh.com",
    "username": "陈冉冉"
  }
}
```

##### 查询当前登录用户信息（未登录）
``` json
{
  "user": null
}
```

##### 查询指定用户信息
用于给用户查看其它用户的信息。得到的json没有邮箱（除非是本人）。  

	gocheer.donggu.me/userInfo?username=<targetusername>
``` json
{
  "user": {
    "score": 0,
    "gender": false,
    "wordsum": 0,
    "registerTime": "2016-12-11 14:01:21",
    "alias": "胖妞",
    "scoresum": 0,
    "username": "张鸿羽"
  }
}
```

## 后端架构
### ORM层 `/src/entity`
使用 **Hibernate** 生成。位于`/src/Entity`内。  

### DAO层 `/src/dao`
**DAO**(Data Access Object)，封装了数据库的操作。  

- BaseDAO<E> 基类DAO  
  用泛型实现的DAO。本来想把单例也做上，嫌麻烦就放到子类去了。  
  完成了session等Hibernate的逻辑封装，考虑线程安全所以每个操作都单独开session。  
  实现了CRUD和sql语句查询什么的。  

- 子类DAO  
  单例模式，继承自对应实体类的`BaseDAO`，如`UserDAO`就继承自`BaseDAO<User>`。进行了一些函数重载方便直接进行操作。  
- 示例代码  
    - 新建记录  

			RecordDAO.getInstance().save(new Record("angry","江泽民"));
    
    - 查询记录

			User user = UserDAO.getInstance().findById("he");	//若不存在则返回null

### Controller层 `/src/webAPP`
各种servlet

### 视图层 `/web`
各种jsp

## 依赖
- JDK 1.8
- json-simple-1.1.1
- Hibernate 3.x
- (JDBC) mysql-connector-java-5.1.40-bin
- Tomcat 9.0.0.M11