<%--
  Created by IntelliJ IDEA.
  User: Donggu
  Date: 2016/12/20
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div class="tab-pane" id="settings">
    <form class="form-horizontal" role="form" action="../update" method="post">
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
                    <input type="radio" name="gender" id="inlineRadio1" value="true"> Male
                </label>
                <label class="radio-inline">
                    <input type="radio" name="gender" id="inlineRadio2" value="false"> Female
                </label>
            </div>
        </div>
        <div class="form-group">
            <label for="inputNickname3" class="col-md-4 control-label">Alias</label>
            <div class="col-md-4">
                <input name="alias" type="name" class="form-control" id="inputNickname3" placeholder="Alias">
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-md-4 control-label">Email</label>
            <div class="col-md-4">
                <input name="email" type="email" class="form-control" id="inputEmail3" placeholder="Email">
            </div>
        </div>

        <div class="update">
            <span><button type="submit" class="btn btn-default" id="btn-update">Update</button></span>
        </div>
    </form>
</div>
<script>
    function check() {
        if (document.getElementById("inputPassword3").value !=
                document.getElementById("inputConfirm3").value) {
            document.getElementById("warning").innerHTML = "   The two passwords do not match.";
            document.getElementById("btn-update").setAttribute("disabled","disabled");
        } else {
            document.getElementById("warning").innerHTML = "   ";
            document.getElementById("btn-update").removeAttribute("disabled");
        }
    }
</script>
</body>
</html>
