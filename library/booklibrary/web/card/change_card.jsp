<%@ page import="com.study.library.service.ReaderTypeService" %>
<%@ page import="com.study.library.service.DeptService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../permission.jsp" %>
<html>
<head>
    <title>变更借书证</title>
    <%@include file="../resource.jsp" %>
    <script>

    </script>
</head>
<body>
<br/><br/>
<center>变更借书证</center>
<br/><br/>

<c:if test="${requestScope.result==null||requestScope.result=='1'}">

    <form action="${ctxpath}/card" method="post">
        <input type="hidden" name="action" value="searchBeforeChange">
        <table style="width:600px" class="form-table" align="center">
            <tr>
                <td style="width:110px">借书证号(*)</td>
                <td>
                    <input type="number" name="rdID" value="${param.rdID}" required/>
                </td>
            </tr>
            <tr>
                <td class="form-table-btns" colspan="2">
                    <input type="submit" name="button" id="button" value="查询"></td>
            </tr>
        </table>
        <c:if test="${requestScope.result=='1'}">
            <center><font color="red">借书证不存在</font></center>
        </c:if>
    </form>
</c:if>
<c:if test="${reader!=null}">
    <form action="${ctxpath}/card" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="change">
        <input type="hidden" name="rdID" value="${reader.rdID}">
        <table style="width:600px" class="form-table" align="center">
            <tr>
                <td style="width:110px">读者类别(*)</td>
                <td>
                    <select name="rdType" required>
                        <option value="">请选择...</option>
                        <% request.setAttribute("readertypes", ReaderTypeService.list()); %>
                        <c:forEach items="${readertypes}" var="rdtype">
                            <option value="${rdtype.rdType}" ${rdtype.rdType==reader.rdType?"selected":""}>${rdtype.rdTypeName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>所在单位(*)</td>
                <td>
                    <select name="rdDept" required>
                        <option value="">请选择...</option>
                        <% request.setAttribute("depts", DeptService.list()); %>
                        <c:forEach items="${depts}" var="dept">
                            <option value="${dept.dpName}"  ${dept.dpName.equals(reader.rdDept)?"selected":""}>${dept.dpName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>姓名(*)</td>
                <td>
                    <input type="text" name="rdName" value="${reader.rdName}" required/>
                </td>
            </tr>

            <tr>
                <td>性别(*)</td>
                <td><select name="rdSex" required>
                    <option value="男" ${reader.rdSex == '男' ? 'selected' : ''}>男</option>
                    <option value="女" ${reader.rdSex == '女' ? 'selected' : ''}>女</option>
                </select></td>
            </tr>

            <tr>
                <td>电话号码</td>
                <td><input type="text" name="rdPhone" value="${reader.rdPhone}"/></td>
            </tr>
            <tr>
                <td>电子邮箱</td>
                <td><input type="email" name="rdEmail" value="${reader.rdEmail}"/></td>
            </tr>
            <tr>
                <td>读者照片</td>
                <td>
                    <input type="file" name="rdPhoto"/>
                    <c:if test="${reader.rdPhoto != null}">
                        <img src="${HttpUtils.getImageBase64(reader.rdPhoto)}" style="max-width:64px"/>
                    </c:if>
                </td>
            </tr>

            <tr>
                <td class="form-table-btns" colspan="2">
                    <input type="submit" name="save" value="变更" style="margin-right:10px;">
                    <input type="button" name="cancel" value="取消" onclick="location.href='${ctxpath}/card/change_card.jsp'">
                </td>
            </tr>
        </table>
    </form>
</c:if>
</body>
</html>
