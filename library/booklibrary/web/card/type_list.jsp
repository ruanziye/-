<%@ page import="com.study.library.service.ReaderTypeService" %>
<%@ page import="com.study.library.service.DeptService" %>
<%@ page import="com.study.library.service.ReaderService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.library.model.ReaderType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../permission.jsp" %>
<html>
<head>
    <title>读者类型管理</title>
    <%@include file="../resource.jsp" %>
    <script>

    </script>
</head>
<body>
<br/><br/>
<center>读者类型管理</center>
<br/><br/>
<%
    List<ReaderType> list=ReaderTypeService.list(request.getParameter("rdName"));
    request.setAttribute("list",list);
%>
<div class="form-search" style="width:800px;margin:0 auto;">
    名称：<input type="text" id="rdName" value="${param.rdName}" style="margin-right: 8px;"/>
    <input type="button" name="submit" value="查询" style="margin-right: 8px;" onclick="location.href='type_list.jsp?rdName='+$('#rdName').val()">
    <input type="button" name="add" value="添加" onclick="location.href='type_list_add.jsp'">
</div>
<table class="list-table" align="center" style="width: 800px;">
    <tr>
        <th>编号</th>
        <th>类别名称</th>
        <th>可借书数量</th>
        <th>可借书天数</th>
        <th>可续借次数</th>
        <th>罚款率(元/天)</th>
        <th>证书有效期(年)</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${list}" var="item">
        <tr>
            <td>${item.rdType}</td>
            <td>${item.rdTypeName}</td>
            <td>${item.canLendQty}</td>
            <td>${item.canLendDay}</td>
            <td>${item.canContinueTimes}</td>
            <td>${item.punishRate}</td>
            <td>${item.dateValid==0?"永久":item.dateValid}</td>
            <td>
                <a href="type_list_update.jsp?rdType=${item.rdType}">修改</a>
                <a href="${ctxpath}/card?action=deleteReaderType&rdType=${item.rdType}" onclick="return confirm('确认是否删除?')">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
