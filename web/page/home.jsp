<%@ page import="dao.AchievementDAO" %>
<%@ page import="dao.AchievementUserDAO" %>
<%@ page import="entity.Achievement" %>
<%@ page import="entity.AchievementUser" %>
<%@ page import="entity.User" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User target = (User) request.getAttribute("targetUser");
    boolean isSelf = (boolean) request.getAttribute("isSelf");
    List all = AchievementDAO.getInstance().getAllAchievements();
    List userAchievements = AchievementUserDAO.getInstance().getUserAchievements(target.getUsername());
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
    <link href="../css/home.css" rel="stylesheet">
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
            <table class="table table-condensed table-score">
                <thead>
                <tr>
                    <th><%=target.getWordsum()%>
                    </th>
                    <th><%=target.getScoresum()%>
                    </th>
                    <th><%=target.getScore()%>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Word</td>
                    <td>Achievements</td>
                    <td>Score</td>
                </tr>
                </tbody>
            </table>
            <div class="list-group">
                <div class="list-group-item">
                    <span class="glyphicon glyphicon-user"></span>
                    <span id="name"><B><%=target.getAlias()%></B></span>
                    <span class="badge badge-male" id="gender"><%=target.isGender() ? "♂" : "♀"%></span>
                </div>
                <%
                    if (isSelf) {
                        out.println("<div href=\"#\" class=\"list-group-item\">"
                                + "<span class=\"glyphicon glyphicon-envelope\"></span>"
                                + "<span id=\"email\">"
                                + target.getEmail()
                                + "</span></div>");
                    }
                %>
                <div href="#" class="list-group-item">
                    <span class="glyphicon glyphicon-time"></span>
                    <span id="registertime">
                        <%
                            Instant now = Instant.now();
                            Instant user = target.getRegistertime().toInstant();
                            Duration duration = Duration.between(user, now);
                            long time = duration.toDays();
                            if (time < 31) {
                                out.print(time + (time == 1 ? " day" : " days"));
                            } else if (time < 365) {
                                out.print(time / 30 + (time / 30 == 1 ? " month " : " months "));
                            } else {
                                out.print(time / 365 + (time / 365 == 1 ? " year " : " years"));
                            }
                        %>
                    </span>
                </div>
            </div>
            <button type="button" class="btn btn-danger btn-lg btn-block">View Leaderboard</button>
        </div>

        <div class="col-lg-9">
            <div class="bs-example">
                <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                    <li class="active"><a href="#achievement" role="tab" data-toggle="tab">My Achievements</a></li>
                    <li><a href="#allachievements" role="tab" data-toggle="tab">All Achievements<span
                            class="badge"><%=all.size()%></span></a>
                    </li>
                    <%
                        if (isSelf) {
                            out.println("<li><a href=\"#history\" role=\"tab\" data-toggle=\"tab\">My History<span class=\"badge\">4</span></a></li>");
                        }
                    %>
                </ul>
                <div id="myTabContent" class="tab-content">

                    <div class="tab-pane active" id="achievement">
                        <div class="alert alert-success" role="alert">There are the achievements you have got.</div>
                        <ul class="media-list my-achievements row">
                            <%
                                for (Iterator it = userAchievements.iterator(); it.hasNext(); ) {
                                    AchievementUser au = (AchievementUser) it.next();
                                    Achievement ach = AchievementDAO.getInstance().findById(au.getAchievement());
                                    out.println("<li class=\"media col-md-10 col-md-offset-1 row\">\n");
                                    out.println("<div class=\"ach-img col-md-2\">");
                                    out.println("<img data-src=\"holder.js/64x64\" src=\"../images/" + ach.getImage() + "\">");
                                    out.println("</div>");
                                    out.println("<div class=\"media-body col-md-7\">");
                                    out.println("<h4 class=\"media-heading\">" + ach.getName() + "</h4>");
                                    out.println("<p>" + ach.getDescription() + "</p>");
                                    out.println("</div>");
                                    out.println("<div class=\"date col-md-2\">");
                                    out.println("<span class=\"label label-success\">" + au.getTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "</span></div></li>");
                                }
                            %>
                        </ul>
                    </div>

                    <div class="tab-pane" id="allachievements">
                        <div class="alert alert-info" role="alert">There are all the achievements you can get.</div>
                        <div class="hidden panel panel-success">
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Type</th>
                                        <th>Name</th>
                                        <th>Description</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        for (Iterator it = all.iterator(); it.hasNext(); ) {
                                            Achievement a = (Achievement) it.next();
                                            out.println("<tr>");
                                            if (a.isHidden()) {
                                                out.println("<td>??????</td>");
                                                out.println("<td>??????????</td>");
                                                out.println("<td>??????????</td>");
                                            } else {
                                                out.println("<td>" + a.getType() + "</td>");
                                                out.println("<td>" + a.getName() + "</td>");
                                                out.println("<td>" + a.getDescription() + "</td>");
                                            }
                                            out.println("</tr>");
                                        }

                                    %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <ul class="media-list my-achievements row">
                            <%
                                for (Iterator it = all.iterator(); it.hasNext(); ) {
                                    Achievement ach = (Achievement) it.next();
                                    out.println("<li class=\"media col-md-10 col-md-offset-1 row\">\n");
                                    out.println("<div class=\"ach-img col-md-2\">");
                                    out.println("<img data-src=\"holder.js/64x64\" alt=\"64x64\" src=\"../images/");
                                    out.println(ach.isHidden() ? "hidden.png" : ach.getImage());
                                    out.println("\"></div>");
                                    out.println("<div class=\"media-body col-md-7\">");
                                    out.println("<h4 class=\"media-heading\">");
                                    out.println(ach.isHidden() ? "???????" : ach.getName());
                                    out.println("</h4><p>");
                                    out.println(ach.isHidden() ? "????????????????????" : ach.getDescription());
                                    out.println("</p></div>");
                                    out.println("<div class=\"date col-md-2\">");
                                    out.println("<span class=\"label label-info\">");
                                    out.println(ach.isHidden()?"?????":ach.getType());
                                    out.println("</span></div></li>");
                                }
                            %>
                        </ul>
                    </div>
                    <% if (isSelf) { %>
                    <jsp:include page="history.jsp"/>
                    <%}%>
                </div>
            </div>

        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>