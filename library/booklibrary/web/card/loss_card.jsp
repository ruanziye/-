<%@ page import="com.study.library.service.ReaderTypeService" %>
<%@ page import="com.study.library.service.DeptService" %>
<%@ page import="com.study.library.service.ReaderService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../permission.jsp" %>
<html>
<head>
    <title>挂失借书证</title>
    <%@include file="../resource.jsp" %>
    <script>
        function checkform(){
            var rdID=$("input[name='rdID']").val()
            if(rdID!='') return true;
            var rdtype=$("select[name='rdType']").val();
            var rddept=$("select[name='rdDept']").val();
            var rdname=$("input[name='rdName']").val();
            if(rdtype==''||rddept==''||rdname==''){
                alert("若忘记借书证号，请填写读者类别、所在单位、姓名进行查询！");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<br/><br/>
<center>挂失借书证</center>
<br/><br/>

<c:if test="${requestScope.result==null||requestScope.result=='1'}">

    <form action="${ctxpath}/card" method="post" onsubmit="return checkform()">
        <input type="hidden" name="action" value="searchBeforeLoss">
        <table style="width:800px" class="form-table" align="center">
            <tr>
                <td style="width:110px">借书证号</td>
                <td>
                    <input type="number" name="rdID" value="${param.rdID}"/>
                    注：若输入借书证号，则优先用借书证查询
                </td>
            </tr>
            <tr>
                <td style="width:110px">读者类别</td>
                <td>
                    <select name="rdType">
                        <option value="">请选择...</option>
                        <% request.setAttribute("readertypes", ReaderTypeService.list()); %>
                        <c:forEach items="${readertypes}" var="rdtype">
                            <option value="${rdtype.rdType}"  ${rdtype.rdType==param.rdType?"selected":""}>${rdtype.rdTypeName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>所在单位</td>
                <td>
                    <select name="rdDept">
                        <option value="">请选择...</option>
                        <% request.setAttribute("depts", DeptService.list()); %>
                        <c:forEach items="${depts}" var="dept">
                            <option value="${dept.dpName}" ${dept.dpName.equals(param.rdDept)?"selected":""}>${dept.dpName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>姓名</td>
                <td>
                    <input type="text" name="rdName" value="${param.rdName}"/>
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
    <form action="${ctxpath}/card" method="post" onsubmit="return confirm('确认执行挂失操作？')">
        <input type="hidden" name="action" value="loss">
        <input type="hidden" name="rdID" value="${reader.rdID}">
        <table style="width:600px" class="form-table" align="center">
            <tr>
                <td style="width:110px">读者类别</td>
                <td>
                    ${ReaderTypeService.find(reader.rdType).rdTypeName}
                </td>
            </tr>
            <tr>
                <td>所在单位(*)</td>
                <td>
                    ${reader.rdDept}
                </td>
            </tr>
            <tr>
                <td>姓名(*)</td>
                <td>${reader.rdName}</td>
            </tr>

            <tr>
                <td>性别(*)</td>
                <td>${reader.rdSex}</td>
            </tr>

            <tr>
                <td>电话号码</td>
                <td>${reader.rdPhone}</td>
            </tr>
            <tr>
                <td>电子邮箱</td>
                <td>${reader.rdEmail}</td>
            </tr>
            <tr>
                <td>读者照片</td>
                <td>
                    <c:if test="${reader.rdPhoto != null}">
                        <img src="${HttpUtils.getImageBase64(reader.rdPhoto)}" style="max-width:64px"/>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>借书证状态</td>
                <td>${reader.rdStatus}</td>
            </tr>
            <tr>
                <td class="form-table-btns" colspan="2">
                    <input type="submit" name="save" value="挂失" style="margin-right:10px;" ${reader.rdStatus!='有效'?"disabled":""}>
                    <input type="button" name="cancel" value="取消" onclick="location.href='${ctxpath}/card/loss_card.jsp'">
                </td>
            </tr>
        </table>
        <c:if test="${reader.rdStatus!='有效'}">
            <center> <font color="red">非有效状态的借书证，无法进行挂失操作</font></center>
        </c:if>
    </form>
</c:if>
</body>
</html>
