<%@ page import="com.study.library.service.ReaderTypeService" %>
<%@ page import="com.study.library.service.DeptService" %>
<%@ page import="com.study.library.service.ReaderService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../permission.jsp" %>
<html>
<head>
    <title>打印借书证</title>
    <%@include file="../resource.jsp" %>
    <style>
        @media print {
            .no-print{
                display: none;
            }
        }
    </style>
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
<c:if test="${reader==null}">
<center>打印借书证</center>
</c:if>
<br/><br/>

<c:if test="${requestScope.result==null||requestScope.result=='1'}">

    <form action="${ctxpath}/card" method="post" onSubmit="return checkform()">
        <input type="hidden" name="action" value="searchBeforePrint">
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
    <style>
        .print-table{
            border: 1px solid #888888;
            padding:20px;
        }
        .print-table td {
            padding:10px;
            font-size: 20px;
        }
        .print-table tr td:first-child{
            font-weight: bold;
            text-align: right;
            padding-right: 50px!important;
        }
    </style>
    <table class="print-table" style="width:500px;background-color: #fff" class="form-table" align="center">
       <tr>
           <td colspan="${reader.rdPhoto != null?'3':'2'}" style="font-size: 25px;text-align: center;padding-right: 0px!important;height: 80px;margin-bottom: 20px;letter-spacing: 10px">借书证</td>
       </tr>
        <tr>
            <td style="max-width:200px">借书证号</td>
            <td>
                    ${reader.rdID}
            </td>
            <c:if test="${reader.rdPhoto != null}">
                <td width="125" rowspan="5" align="center" valign="top">
                        <img src="${HttpUtils.getImageBase64(reader.rdPhoto)}" style="max-width:128px"/>
                </td>
            </c:if>
        </tr>
        <tr>
            <td>姓名</td>
            <td >${reader.rdName}</td>
        </tr>
        <tr>
            <td>性别</td>
            <td>${reader.rdSex}</td>
        </tr>
        <tr>
            <td>读者类别</td>
            <td>
                    ${readertype.rdTypeName}
            </td>
        </tr>
        <tr>
            <td>单位</td>
            <td>
                    ${reader.rdDept}
            </td>
        </tr>
        <tr>
            <td>状态</td>
            <td>${reader.rdStatus}</td>
        </tr>
        <tr>
            <td>有效期</td>
            <td>
                    <c:if test="${readertype.dateValid==0}">
                        永久
                    </c:if>
                    <c:if test="${readertype.dateValid>0}">
                        ${readertype.dateValid}年
                    </c:if>
            </td>
        </tr>
    </table>
    <div style="padding:20px;text-align: center" class="no-print">
        <input type="button" name="save" value="打印" style="margin-right:10px;" onClick="window.print();">
        <input type="button" name="cancel" value="取消" onClick="location.href='${ctxpath}/card/print_card.jsp'">
  </div>
</c:if>
</body>
</html>
