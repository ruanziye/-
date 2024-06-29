<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="permission.jsp"%>
<html>
<head>
    <title>修改登录密码</title>
    <%@include file="resource.jsp"%>
  <script>
    function checkform(){
      var pwd1=$('#newPwd').val()
      var pwd2=$('#newPwd2').val();
      if(pwd1!=pwd2){
        alert("两次密码输入不一致！");return false;
      }
      return true;
    }
  </script>
</head>
<body>
<br/><br/>
<center>修改密码</center><br/><br/>
<form action="index" method="post" onsubmit="return checkform()">
  <input type="hidden" name="action" value="changePwd">
<table width="600" border="1" align="center" cellpadding="5" cellspacing="1">
  <tr>
    <td width="110" height="40" align="right" valign="middle">原密码 </td>
    <td width="331" height="40">
      <input type="password"  name="oldPwd" id="oldPwd" required>
    </td>
  </tr>
  <tr>
    <td height="40" align="right" valign="middle">新密码</td>
    <td height="40">
      <input type="password" name="newPwd" id="newPwd" required>
    </td>
  </tr>
  <tr>
    <td height="40" align="right" valign="middle">再次输入新密码</td>
    <td height="40">
      <input type="password" name="newPwd2" id="newPwd2" required>
    </td>
  </tr>
  <tr>
    <td height="40" colspan="2" align="center">
      <input type="submit" name="button" id="button" value="提交"></td>
  </tr>
</table>
</form>
</body>
</html>
