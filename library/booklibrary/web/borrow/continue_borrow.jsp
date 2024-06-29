<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../permission.jsp" %>
<%@ page import="com.study.library.service.BorrowService" %>
<%@ page import="com.study.library.service.ReaderTypeService" %>
<html>
<head>
    <title>续借</title>
    <%@include file="../resource.jsp" %>
    <style>
        .reader-info,.bottom-panel {
            background-color: #fff;
            padding:20px;
        }
        .reader-info label{
            color:#1e9fff;
            font-weight: bold;
            margin-right: 10px;
        }
        .bottom-panel{
            display: flex;
            justify-content: flex-end;
            align-items: center;
            position: fixed;
            bottom: 0;
            left:0;
            right: 0;
        }
        body{
            overflow-x: hidden;
        }
    </style>
    <script>
      function searchBorrow(){
        var bkCode=$('#bkCode').val();
        if(bkCode==''){
          alert("请输入图书书号");
          return;
        }
        location.href='${ctxpath}/borrow?action=searchBorrow&bkCode='+bkCode;
      }
    </script>
</head>
<body>
<br/><br/>
<center>续借</center>
<br/>
<div class="form-search">请输入图书书号：<input type="text" value="${param.bkCode}" id="bkCode"
                                               style="margin-right: 10px;"> <input
        type="button" value="查询" onclick="searchBorrow()"></div>
<c:if test="${reader!=null}">
    <div class="reader-info">
        【姓名】<label>${reader.rdName}</label> 【读者类别】<label>${ReaderTypeService.find(reader.rdType).rdTypeName}</label> 【单位】<label>${reader.rdDept}</label>
        【手机】<label>${reader.rdPhone}</label> 【邮箱】<label>${reader.rdEmail}</label> 【注册时间】<label>${reader.rdDateReg}</label> 【借书证状态】<label>${reader.rdStatus}</label>
        【在借数量】<label>${reader.rdBorrowQty}</label>
    </div>
</c:if>
<c:if test="${bookBorrow!=null}">
    <script>
        function confirmContinueBorrow(){
            var rdStatus='${reader.rdStatus}';
            if(rdStatus!='有效'){
                alert('借书证状态为'+rdStatus+'，不能续借');
                return;
            }
            var lendTimes=parseInt($('#continueTimes').text(),10);
            var canLendTimes=parseInt($('#canLendTimes').text(),10);
            if(lendTimes>=canLendTimes){
                alert('该图书续借次数已达上限，不能续借');
                return;
            }
            if($('#borrowList').find('td:contains(\'超期未归还\')').length>0){
                alert("该图书超期未归还，不能续借");
                return;
            }
            if(!confirm('确认续借？')) return;
            location.href="${ctxpath}/borrow?action=continueBorrow&borrowID=${bookBorrow.borrow.borrowID}&bkCode=${bookBorrow.book.bkCode}";
        }
    </script>
    <table id="borrowList" class="list-table" align="center">
        <tr>
            <th>图书编号</th>
            <th>书名</th>
            <th>出版日期</th>
            <th>ISBN</th>
            <th>分类号</th>
            <th>借阅日期</th>
            <th>应还日期</th>
            <th>已续借次数</th>
            <th>可续借次数</th>
        </tr>
        <tr>
            <td>${bookBorrow.book.bkCode}</td>
            <td>${bookBorrow.book.bkName}</td>
            <td>${bookBorrow.book.bkDatePress}</td>
            <td>${bookBorrow.book.bkISBN}</td>
            <td>${bookBorrow.book.bkCatalog}</td>
            <td>${bookBorrow.borrow.ldDateOut}</td>
            <td>
                    ${bookBorrow.borrow.ldDateRetPlan}
                <c:if test="${BorrowService.isOverReturn(bookBorrow.borrow)}">
                    <font color="red">(超期未归还)</font>
                </c:if>
            </td>
            <td id="continueTimes">${bookBorrow.borrow.ldContinueTimes}</td>
            <td id="canLendTimes">${ReaderTypeService.find(reader.getRdType()).canLendQty}</td>
        </tr>
    </table>
    <div class="bottom-panel">
        <button onclick="confirmContinueBorrow()" style="margin-right: 10px;" >确认续借</button>
        <button onclick="location.href='${ctxpath}/borrow/continue_borrow.jsp'">取消</button>
    </div>
</c:if>
</body>
</html>
