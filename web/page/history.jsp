<%@ page import="dao.RecordDAO" %>
<%@ page import="entity.Record" %>
<%@ page import="entity.User" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Donggu
  Date: 2016/12/18
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User target = (User) request.getAttribute("targetUser");
    List history = RecordDAO.getInstance().getUserHistory(target.getUsername());
%>
<html>
<div class="tab-pane" id="history">
    <div class="alert alert-warning" role="alert">There are all the words you have checked.</div>
    <ul class="media-list">
        <%
            LocalDate date = null;
            for (Iterator it = history.iterator(); it.hasNext();) {
                Record record = (Record) it.next();
                if (date == null || !date.equals(record.getDatetime().toLocalDateTime().toLocalDate())) {
                    if(date!=null){
                        out.println("</div></li>");
                    }
                    date = record.getDatetime().toLocalDateTime().toLocalDate();
                    out.println("<li class=\"media\">");
                    out.println("<div class=\"pull-left\">");
                    out.println("<span class=\"label label-warning\">");
                    out.println(date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
                    out.println("</span></div><div class=\"media-body\">");
                }
                out.println("<span class=\"badge badge-lg\">"+record.getWord()+"</span>");
            }
            out.println("</div></li>");
        %>
    </ul>
</div>
</html>
