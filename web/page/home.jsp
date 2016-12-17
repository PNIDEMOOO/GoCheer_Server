<%@ page import="dao.AchievementDAO" %>
<%@ page import="dao.AchievementUserDAO" %>
<%@ page import="entity.Achievement" %>
<%@ page import="entity.AchievementUser" %>
<%@ page import="entity.User" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User target = (User)request.getAttribute("targetUser");
    boolean isSelf = (boolean)request.getAttribute("isSelf");
%>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>GoCheer: Home</title>

    <!--设置网页图标-->
    <link rel="shortcut icon" href="../images/logo.png" type=”image/x-icon”/>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/header.css" rel="stylesheet">
    <link href="../css/footer.css" rel="stylesheet">
    <link href="../css/login-state.css" rel="stylesheet">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body>

<jsp:include page="../header.jsp"/>
<div class="container">
    <div class="row">

        <div class="col-lg-3">
            <a class="thumbnail">
                <img data-src="holder.js/100%x230" src="../images/logo.png">
            </a>
            <div class="list-group">
                <a href="#" class="list-group-item">
                    <span class="glyphicon glyphicon-user"></span><B><%=target.getAlias()%></B>
                </a>
                <a href="#" class="list-group-item">
                    <span class="glyphicon glyphicon-time"></span>
                    <%
                        Instant now = Instant.now();
                        Instant user = target.getRegistertime().toInstant();
                        Duration duration = Duration.between(user,now);
                        long time = duration.toDays();
                        if(time<31){
                            out.print(time+(time==1?" day":" days"));
                        }
                        else if (time<365){
                            out.print(time/30 + (time/30==1?" month ":" months "));
                        }
                        else{
                            out.print(time/365 + (time/365==1?" year ":" years"));
                        }
                    %></a>
                <a href="#" class="list-group-item">
                    <span class="glyphicon glyphicon-star"></span><%=target.getScore()%> points
                </a>
                <a href="#" class="list-group-item">
                    <span class="glyphicon glyphicon-star"></span><%=target.getScoresum()%> Achievements
                </a>
                <a href="#" class="list-group-item">
                    <span class="glyphicon glyphicon-star"></span><%=target.getWordsum() %> words searched
                </a>
                <%
                    if(isSelf){
                        out.println("<a href=\"#\" class=\"list-group-item\">"
                                + "<span class=\"glyphicon glyphicon-star\"></span>"
                                + target.getEmail()
                                +"</a>");
                    }
                %>
            </div>
            <button type="button" class="btn btn-danger btn-lg btn-block">View Leaderboard</button>
        </div>

        <div class="col-lg-9">
            <div class="bs-example">
                <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                    <li class="active"><a href="#achievement" role="tab" data-toggle="tab">Achievement <span class="badge">4</span></a></li>
                    <li><a href="#list" role="tab" data-toggle="tab">List <span class="badge">4</span></a></li>
                    <li><a href="#history" role="tab" data-toggle="tab">History <span class="badge">4</span></a></li>
                </ul>
                <div id="myTabContent" class="tab-content">

                    <div class="tab-pane active" id="achievement">
                        <div class="alert alert-success" role="alert">There are the achievements you have got.</div>
                        <div class="panel panel-success">
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>Time</th>
                                        <th>Name</th>
                                        <th>Description</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        ArrayList<AchievementUser> userAchievement = AchievementUserDAO.getInstance().getUserAchievements(target.getUsername());
                                        for(AchievementUser au: userAchievement){
                                            Achievement ach = AchievementDAO.getInstance()
                                                    .findById(au.getAchievement());
                                            out.println("<tr>");
                                            out.println("<th>" + au.getTime() + "</th>");
                                            out.println("<th>" + ach.getName() + "</th>");
                                            out.println("<th>" + ach.getDescription() + "</th>");
                                            out.println("</tr>");
                                        }
                                    %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="tab-pane" id="list">
                        <div class="alert alert-info" role="alert">There are all the achievements you can get.</div>
                        <div class="panel panel-success">
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Description</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>content</td>
                                        <td>ddddddd</td>
                                    </tr>
                                    <tr>
                                        <td>content</td>
                                        <td>ddddddd</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="tab-pane" id="history">
                        <div class="alert alert-warning" role="alert">There are all the words you have checked.</div>
                    </div>

                </div>
            </div>

        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>