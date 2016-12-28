# 枸杞后端 GoCheer_server
> 枸杞的后端逻辑

## 接口
### `/checkUsername`用户名查重  
检测用户名是否已存在。
调用方法：

	gocheer.donggu.me/checkUsername?username=<待检测用户名>

使用 GET 方法，返回的为json的`false`或`true`。其中`false`表示用户名已存在，`true`表示用户名不存在可以注册。  

### `/userInfo`获取用户基本信息
#### 调用方法：
	gocheer.donggu.me/userInfo?username=<username>

使用 GET 方法，返回 json。后面的参数可以不加，则默认为查询当前登录用户。  

#### 样例  
##### 查询当前登录用户信息（已登录）
	gocheer.donggu.me/userInfo
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
	gocheer.donggu.me/userInfo
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

### `/newRecord`添加查询记录
#### 调用方式
	gocheer.donggu.me/newRecord?word=<word>
使用 GET 方法，word为划词查询的内容。大小写有没有空格什么的都可以。  

#### 具体说明
该接口把word全部转化为小写，然后使用正则匹配分析；如果word中仅含有一个单词则为当前用户添加新的查询记录；如果是个长句就不会添加到历史记录中。  
无论如何只要有词就会为用户加分、加划词次数，并检查是否获得新成就。新获得的成就会以json返回。  

#### 样例代码
##### 获得新成就
可能获得不止一个新成就；所以成就会是个数组。

``` json
{
  "achievement": [
    {
      "image": "default.png",
      "hidden": false,
      "name": "Begin",
      "bonus": 5,
      "description": "Enjoy with GoCheer!",
      "id": 1
    }
  ]
}
```

##### 没有新成就
``` json
{
  "achievement": null
}
```

##### 还没登录
``` json
{
  "error": 1,
  "message": "Haven't log in"
}
```

##### word中不含单词
``` json
{
  "error": 2,
  "message": "No avaliable words."
}
```

### `/Login`登录
    gocheer.donggu.me/Login
    form-data:
        username: <username>
        password: <password>
        extension: <extension>
使用 POST 方法，如果是来自插件的请求则`extension`值为`"true"`，其它则不需要这个键。若来自插件，登录成功会返回true，失败为false。  

### `/logout`注销
    gocheer.donggu.me/logout
    form-data:
        extension: <extension>

使用 POST 方法，如果是来自插件的请求则`extension`值为`"true"`，其它则不需要这个键。若来自插件则什么都不会返回。  

### `/update`修改用户信息
    gocheer.donggu.me/update
    form-data:
        alias: <new alias>
        password: <new password>
        email: <new email>
        gender: <new gender>

使用 POST 方法，表单某些项如果没有填的话可以不发没关系。将当前登录用户的相关信息修改，并刷新当前页面。如果未登录的话会跳转登录页。`gender`男为`true`，女为`false`。  

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
- jersey-bundle-1.19.1  
