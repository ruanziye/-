<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <%@include file="resource.jsp"%>
    <script>
      if(parent!=window)
        parent.location.href=window.location.href;
    </script>
</head>
<body>
<br/><br/>
<center>图书管理系统</center><br/><br/>
<form action="index" method="post">
  <input type="hidden" name="action" value="login">
<table width="381" border="1" align="center" cellpadding="5" cellspacing="1">
  <tr>
    <td width="110" height="40" align="right" valign="middle">用户号 </td>
    <td width="331" height="40">
      <input type="text" value="${param.id}" name="id" id="id" required>
    </td>
  </tr>
  <tr>
    <td height="40" align="right" valign="middle">密码</td>
    <td height="40">
      <input type="password" name="pwd" id="pwd" required>
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
