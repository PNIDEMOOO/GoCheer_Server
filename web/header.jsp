<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    String src = "../images/logo.png";
%>
<html lang="zh-cn">
<body>
<!--这是页头-->
<div class="header">
    <a href="/index.jsp">
        <span><img src="<%=request.getRequestURI().contains("home")?"../":""%>images/logo.png"> </span>
    </a>
</div>
</body>
</html>