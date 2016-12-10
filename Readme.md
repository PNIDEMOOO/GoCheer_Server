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

#### 样例输出  
##### 已登录，返回用户信息
``` json
{
  "user": {
    "score": 0,
    "gender": true,
    "registertime": "2016-12-03 19:43:02.0",
    "wordsum": 0,
    "alias": "ly",
    "scoresum": 0,
    "email": "lengyan@mail.com",
    "username": "lengyan"
  }
}
```

##### 未登录，返回空
``` json
{
  "user": null
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

### Model层 `/src/model`
还没东西

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