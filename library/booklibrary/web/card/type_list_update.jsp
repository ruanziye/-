<%@ page import="com.study.library.service.ReaderTypeService" %>
<%@ page import="com.study.library.service.DeptService" %>
<%@ page import="com.study.library.service.ReaderService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.library.model.ReaderType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../permission.jsp" %>
<html>
<head>
    <title>修改读者类型</title>
    <%@include file="../resource.jsp" %>
    <script>

    </script>
</head>
<body>
<br/><br/>
<center>修改读者类型</center>
<br/><br/>
<%
    Integer rdType=Integer.valueOf(request.getParameter("rdType"));
    request.setAttribute("readertype",ReaderTypeService.find(rdType));
%>
<form action="${ctxpath}/card" method="post">
    <input type="hidden" name="action" value="updateReaderType">
    <table style="width:800px" class="form-table" align="center">
        <tr>
            <td style="width:140px">读者类别编号(*)</td>
            <td>
                <input type="number" name="rdType" value="${readertype.rdType}" required readonly/>
            </td>
        </tr>
        <tr>
            <td>读者类别名称(*)</td>
            <td>
                <input type="text" name="rdTypeName" value="${readertype.rdTypeName}" required/>
            </td>
        </tr>
        <tr>
            <td >可借书数量(*)</td>
            <td>
                <input type="number" name="canLendQty" value="${readertype.canLendQty}" required/>
            </td>
        </tr>
        <tr>
            <td >可借书天数(*)</td>
            <td>
                <input type="number" name="canLendDay" value="${readertype.canLendDay}" required/>
            </td>
        </tr>
        <tr>
            <td >可续借的次数(*)</td>
            <td>
                <input type="number" name="canContinueTimes" value="${readertype.canContinueTimes}" required/>
            </td>
        </tr>
        <tr>
            <td >罚款率(元/天)(*)</td>
            <td>
                <input type="number" name="punishRate" step="0.01" value="${readertype.punishRate}" required/>
            </td>
        </tr>
        <tr>
            <td >证书有效期(*)</td>
            <td>
                <input type="number" name="dateValid" value="${readertype.dateValid}" required/>
            </td>
        </tr>
        <tr>
            <td class="form-table-btns" colspan="2">
                <input type="submit" name="button1" id="button1" value="修改" style="margin-right: 10px;">
                <input type="button" name="button2" id="button2" value="取消" onclick="location.href='type_list.jsp'">
            </td>
        </tr>
    </table>
</form>
</table>
</body>
</html>