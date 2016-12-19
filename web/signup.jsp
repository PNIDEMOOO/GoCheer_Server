<%--
  Created by IntelliJ IDEA.
  User: orient
  Date: 2016/12/8
  Time: 下午1:57
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>GoCheer: Sign up</title>

    <!--设置网页图标-->
    <link rel="shortcut icon" href="images/logo.png" type=”image/x-icon”/>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/footer.css" rel="stylesheet">
    <link href="css/header.css" rel="stylesheet">
    <link href="css/signup.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="jumbotron">
    <div class="container">
        <form class="form-horizontal" role="form" action="/signup" method="post">
            <div class="form-group">
                <label for="inputUsername3" class="col-md-4 control-label">Username</label>
                <div class="col-md-4">
                    <input name="username" type="username" class="form-control" id="inputUsername3"
                           placeholder="Username">
                </div>
            </div>

            <div class="form-group">
                <label for="inputPassword3" class="col-md-4 control-label">Password</label>
                <div class="col-md-4">
                    <input name="password" type="password" class="form-control" id="inputPassword3"
                           placeholder="Password">
                </div>
            </div>

            <div class="form-group">
                <label for="inputConfirm3" class="col-md-4 control-label">Confirm Password</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" id="inputConfirm3" placeholder="Confirm Password"
                           onblur="check()">
                    <span id="warning">   </span>
                </div>
            </div>


            <div class="form-group">
                <label for="inputConfirm3" class="col-md-4 control-label">Gender</label>
                <div class="col-md-4">
                    <label class="radio-inline">
                        <input type="radio" name="gender" id="inlineRadio1" value="1"> Male
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="gender" id="inlineRadio2" value="0"> Female
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label for="inputNickname3" class="col-md-4 control-label">Nickname</label>
                <div class="col-md-4">
                    <input name="alias" type="name" class="form-control" id="inputNickname3" placeholder="Nickname">
                </div>
            </div>
            <div class="form-group">
                <label for="inputEmail3" class="col-md-4 control-label">Email</label>
                <div class="col-md-4">
                    <input name="email" type="email" class="form-control" id="inputEmail3" placeholder="Email">
                </div>
            </div>

            <div class="sign-up">
                <span><button type="submit" class="btn btn-default">Sign up</button></span>
            </div>
        </form>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
<script>
    function check() {
        if (document.getElementById("inputPassword3").value !=
                document.getElementById("inputConfirm3").value) {
            document.getElementById("warning").innerHTML = "   两次密码的输入不一致";
        } else {
            document.getElementById("warning").innerHTML = "   ";
        }
    }
</script>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="js/header.js"></script>
</html>