<%@ page import="com.study.library.service.ReaderTypeService" %>
<%@ page import="com.study.library.service.BookService" %>
<%@ page import="cn.hutool.core.util.StrUtil" %>
<%@ page import="com.study.library.service.BorrowService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../permission.jsp" %>
<html>
<head>
    <title>借书</title>
    <%@include file="../resource.jsp" %>
    <style>
        .step-container {
            height: 30px;

            display: flex;
            justify-content: flex-start;
            align-items: center;
            width: 98%;
            margin: 0 auto;
            padding: 10px;
        }

        .step-item {
            background-color: #aaa;
            border-radius: 20px;
            color: #eee;
            margin-right: 15px;
            padding: 10px 20px;
        }

        .step-item-active {
            background-color: #1e9fff;
            color: #fff;
        }

        .step-body {
            padding: 20px;
        }

        .reader-info,.next-panel {
            background-color: #fff;
            padding:20px;
        }
        .reader-info label{
            color:#1e9fff;
            font-weight: bold;
            margin-right: 10px;
        }
        .next-panel{
            display: flex;
            justify-content: flex-end;
            align-items: center;
            position: fixed;
            bottom: 0;
            left:0;
            right: 0;
        }
    </style>

</head>
<body style="overflow-x: hidden">
<br/><br/>
<center>借书</center>
<br/>
<%
    String step=request.getParameter("step");
    if(StrUtil.isEmpty(step))
        step="1";
    request.setAttribute("step",step);
%>
<div class="step-container">
    <div class="step-item ${step=='1'?'step-item-active':''}">第一步、查询读者信息</div>
    <div class="step-item  ${step=='2'?'step-item-active':''}">第二步、选择借阅的图书</div>
</div>
<c:if test="${step=='1'}">
    <script>
        function searchReader() {
            var rdID = $('#rdID').val();
            if (rdID == '') {
                alert("请输入借书证号");
                return;
            }
            location.href = "${ctxpath}/borrow?action=searchReader&rdID=" + rdID;
        }
    </script>
    <div class="step-body">
        <div class="form-search">请输入借书证号：<input type="number" value="${param.rdID}" id="rdID" style="margin-right: 10px;"> <input
                type="button" value="查询" onclick="searchReader()"></div>
        <c:if test="${reader!=null}">
            <script>
                function checkIfStep2(){
                    var status="${reader.rdStatus}";
                    if(status!='有效'){
                        alert("借书证状态为"+status+"，不能借阅");
                        return;
                    }
                    var borrowedCount=${reader.rdBorrowQty};
                    var canBorrowCount=${ReaderTypeService.find(reader.rdType).canLendQty};
                    if(borrowedCount>=canBorrowCount){
                        alert("已借书数量超过可借数量，不能借阅");
                        return;
                    }
                    if($('#borrowList').find('td:contains(\'超期未归还\')').length>0){
                        alert("存在超期未归还的图书，不能借阅");
                        return;
                    }
                    location.href='${ctxpath}/borrow/borrow.jsp?rdID=${param.rdID}&step=2'
                }
            </script>
            <div class="reader-info">
                【姓名】<label>${reader.rdName}</label> 【读者类别】<label>${ReaderTypeService.find(reader.rdType).rdTypeName}</label> 【单位】<label>${reader.rdDept}</label>
                【手机】<label>${reader.rdPhone}</label> 【邮箱】<label>${reader.rdEmail}</label> 【注册时间】<label>${reader.rdDateReg}</label> 【借书证状态】<label>${reader.rdStatus}</label>
                【在借数量】<label>${reader.rdBorrowQty}</label>
            </div>
        </c:if>
        <c:if test="${borrowList!=null and borrowList.size()>0}">
            <table id="borrowList" class="list-table" align="center">
                <tr>
                    <th>图书编号</th>
                    <th>书名</th>
                    <th>出版日期</th>
                    <th>ISBN</th>
                    <th>分类号</th>
                    <th>借阅日期</th>
                    <th>应还日期</th>
                </tr>
                <c:forEach items="${borrowList}" var="item">
                    <tr>
                        <td>${item.book.bkCode}</td>
                        <td>${item.book.bkName}</td>
                        <td>${item.book.bkDatePress}</td>
                        <td>${item.book.bkISBN}</td>
                        <td>${item.book.bkCatalog}</td>
                        <td>${item.borrow.ldDateOut}</td>
                        <td>
                                ${item.borrow.ldDateRetPlan}
                                <c:if test="${BorrowService.isOverReturn(item.borrow)}">
                                    <font color="red">(超期未归还)</font>
                                </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${reader!=null}">
            <div class="next-panel">
                <button onclick="checkIfStep2()">选择借阅的图书</button>
            </div>
        </c:if>
    </div>
</c:if>
<c:if test="${step=='2'}">
    <script>
        function searchBook(){
            var bkCode=$('#bkCode').val();
            if(bkCode==''){
                alert("请输入图书书号");
                return;
            }
            location.href="${ctxpath}/borrow?action=searchBook&step=2&rdID=${param.rdID}&bkCode="+bkCode;
        }
        function confirmBorrow(){
            var bkStatus='${book.bkStatus}';
            if(bkStatus!='在馆'){
                alert('图书状态为'+bkStatus+"，不能借阅");
                return;
            }
            if(!confirm('确认是否借阅？')) return;
            location.href="${ctxpath}/borrow?action=borrow&step=2&rdID=${param.rdID}&bkCode=${param.bkCode}";
        }
    </script>
    <div class="step-body">
        <div class="form-search">请输入图书书号：<input type="number" value="${param.bkCode}" id="bkCode" style="margin-right: 10px;"> <input
                type="button" value="查询" onclick="searchBook()">
        </div>
        <c:if test="${book!=null}">
            <table style="width:600px" class="form-table" align="center">
                <tr>
                    <td style="width: 140px">图书名称</td>
                    <td>
                        ${book.bkName}
                    </td>
                </tr>
                <tr>
                    <td >作者</td>
                    <td>
                       ${book.bkAuthor}
                    </td>
                </tr>
                <tr>
                    <td >出版社</td>
                    <td>
                        ${book.bkPress}
                    </td>
                </tr>
                <tr>
                    <td >出版日期</td>
                    <td>
                      ${book.bkDatePress}
                    </td>
                </tr>
                <tr>
                    <td >ISBN书号</td>
                    <td>
                       ${book.bkISBN}
                    </td>
                </tr>

                <!-- 分类号 -->
                <tr>
                    <td >分类号</td>
                    <td>
                        ${book.bkCatalog}
                    </td>
                </tr>
                <!-- 语言 -->
                <tr>
                    <td >语言</td>
                    <td>
                        ${BookService.getLanguage(book.bkLanguage)}
                    </td>
                </tr>
                <!-- 页数 -->
                <tr>
                    <td >页数</td>
                    <td>
                      ${book.bkPages}
                    </td>
                </tr>
                <!-- 价格 -->
                <tr>
                    <td >价格(*)</td>
                    <td>
                        ${book.bkPrice}
                    </td>
                </tr>
                <!-- 入馆日期 -->
                <tr>
                    <td >入馆日期(*)</td>
                    <td>
                      ${book.bkDateIn}
                    </td>
                </tr>
                <!-- 内容简介 -->
                <tr>
                    <td >内容简介</td>
                    <td>
                       ${book.bkBrief}
                    </td>
                </tr>
                <!-- 图书封面照片 -->
                <!-- 注意：这里仅展示了一个文件上传按钮，实际上传和处理逻辑需要在后端实现 -->
                <tr>
                    <td >图书封面照片</td>
                    <td>
                        <c:if test="${book.bkCover != null and fn:length(book.bkCover)>0}">
                            <img src="${HttpUtils.getImageBase64(book.bkCover)}" style="max-width:64px"/>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td >图书状态</td>
                    <td>
                            ${book.bkStatus}
                    </td>
                </tr>
            </table>
        </c:if>
    </div>
    <div class="next-panel">
        <button onclick="location.href='${ctxpath}/borrow?action=searchReader&rdID=${param.rdID}'" style="margin-right: 10px;">返回上一步</button>
        <button onclick="confirmBorrow()">确认借阅</button>
    </div>
</c:if>
</body>
</html>
