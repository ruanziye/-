<%@ page import="com.study.library.service.ReaderTypeService" %>
<%@ page import="com.study.library.service.DeptService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../permission.jsp" %>
<html>
<head>
    <title>办理借书证</title>
    <%@include file="../resource.jsp" %>
    <script>

    </script>
</head>
<body>
<br/><br/>
<center>办理借书证</center>
<br/><br/>

<c:if test="${requestScope.result==null||requestScope.result=='1'}">

    <form action="${ctxpath}/card" method="post">
        <input type="hidden" name="action" value="searchBeforeCreate">
        <table style="width:600px" class="form-table" align="center">
            <tr>
                <td style="width:110px">读者类别(*)</td>
                <td>
                    <select name="rdType" required>
                        <option value="">请选择...</option>
                        <% request.setAttribute("readertypes", ReaderTypeService.list()); %>
                        <c:forEach items="${readertypes}" var="rdtype">
                            <option value="${rdtype.rdType}"  ${rdtype.rdType==param.rdType?"selected":""}>${rdtype.rdTypeName}</option>
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
                            <option value="${dept.dpName}" ${dept.dpName.equals(param.rdDept)?"selected":""}>${dept.dpName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>姓名(*)</td>
                <td>
                    <input type="text" name="rdName" value="${param.rdName}" required/>
                </td>
            </tr>

            <tr>
                <td class="form-table-btns" colspan="2">
                    <input type="submit" name="button" id="button" value="查询"></td>
            </tr>
        </table>
        <c:if test="${requestScope.result=='1'}">
            <center><font color="red">读者已存在</font></center>
        </c:if>
    </form>
</c:if>
<c:if test="${requestScope.result=='0'}">
    <form action="${ctxpath}/card" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="create">
        <table style="width:600px" class="form-table" align="center">
            <tr>
                <td style="width:110px">读者类别(*)</td>
                <td>
                    <select name="rdType" required>
                        <option value="">请选择...</option>
                        <% request.setAttribute("readertypes", ReaderTypeService.list()); %>
                        <c:forEach items="${readertypes}" var="rdtype">
                            <option value="${rdtype.rdType}" ${rdtype.rdType==param.rdType?"selected":""}>${rdtype.rdTypeName}</option>
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
                            <option value="${dept.dpName}"  ${dept.dpName.equals(param.rdDept)?"selected":""}>${dept.dpName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>姓名(*)</td>
                <td>
                    <input type="text" name="rdName" value="${param.rdName}" required/>
                </td>
            </tr>

            <tr>
                <td>性别(*)</td>
                <td><select name="rdSex" required>
                    <option value="男" ${param.rdSex == '男' ? 'selected' : ''}>男</option>
                    <option value="女" ${param.rdSex == '女' ? 'selected' : ''}>女</option>
                </select></td>
            </tr>

            <tr>
                <td>电话号码</td>
                <td><input type="text" name="rdPhone" value="${param.rdPhone}"/></td>
            </tr>
            <tr>
                <td>电子邮箱</td>
                <td><input type="email" name="rdEmail" value="${param.rdEmail}"/></td>
            </tr>
            <tr>
                <td>读者照片</td>
                <td><input type="file" name="rdPhoto"/> </td>
            </tr>

            <tr>
                <td class="form-table-btns" colspan="2">
                    <input type="submit" name="save" value="办理" style="margin-right:10px;">
                    <input type="button" name="cancel" value="取消" onclick="location.href='${ctxpath}/card/create_card.jsp'">
                </td>
            </tr>
        </table>
    </form>
</c:if>
</body>
</html>
