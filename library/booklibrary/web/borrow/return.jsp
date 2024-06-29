<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../permission.jsp" %>
<%@ page import="com.study.library.service.BorrowService" %>
<%@ page import="com.study.library.service.ReaderTypeService" %>
<%@ page import="com.study.library.dto.BookBorrow" %>
<%@ page import="cn.hutool.core.date.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="cn.hutool.core.date.DateField" %>
<html>
<head>
    <title>还书</title>
    <%@include file="../resource.jsp" %>
    <style>
        .action-container {
            height: 30px;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            margin: 10px auto 10px auto;
            padding: 20px;
            background-color: #fff;
        }

        .action-item {
            background-color: #aaa;
            border-radius: 20px;
            color: #eee;
            margin-right: 15px;
            padding: 10px 20px;
            cursor: pointer;
        }

        .action-item-active, .action-item:hover {
            background-color: #1e9fff;
            color: #fff;
        }

        .action-body{
            padding:30px;
            display: none;
        }
        .action-body label{
            font-weight: bold;
            color: blue;
        }
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
        location.href='${ctxpath}/borrow?action=searchBorrowForReturn&bkCode='+bkCode;
      }
    </script>
</head>
<body>
<br/><br/>
<center>还书</center>
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
        let returnType=-1;
        function confirmReturn(){
            if(returnType==-1){
                alert("请选择归还方式");return;
            }
            var punishPrice="";
            if(returnType==1){
                var returnTxt=$("div.action-item:first-child").text();
                if(returnTxt=='超期归还'){
                    if($('#punishPrice1').val()==''){
                        alert("请输入实际罚款金额");
                        return;
                    }
                    punishPrice=parseFloat($('#punishPrice1').val());
                    var shouldPunishPrice=parseFloat($('#shouldPunishPrice1').text());
                    if(punishPrice>shouldPunishPrice){
                        alert("实际罚款金额不能大于应罚款金额");
                        return;
                    }
                }
            }else{
                if($('#punishPrice2').val()==''){
                    alert("请输入应罚款金额");
                    return;
                }
                punishPrice=parseFloat($('#punishPrice2').val());
                var shouldPunishPrice=parseFloat($('#shouldPunishPrice2').text());
                if(punishPrice>shouldPunishPrice){
                    alert("实际罚款金额不能大于应罚款金额");
                    return;
                }
            }
            if(!confirm('确认还书？')) return;
            $("input[name='returnType']").val(returnType);
            $("input[name='punishPrice']").val(punishPrice);
            $("form[name='returnForm']")[0].submit();
        }
        //选择归还方式
        function selectReturnType(t){
            returnType=t;
            $("div.action-body").hide();
            $("#action-body-"+t).fadeIn();
        }
        $(function(){
            $("div.action-container").find("div.action-item").click(function(){
               $("div.action-container").find("div.action-item").removeClass("action-item-active");
               $(this).addClass("action-item-active");
            });
        })
    </script>
    <form name="returnForm" action="${ctxpath}/borrow" method="post">
        <input type="hidden" name="action" value="returnBook">
        <input type="hidden" name="borrowID" value="${bookBorrow.borrow.borrowID}">
        <input type="hidden" name="bkCode" value="${bookBorrow.book.bkCode}">
        <input type="hidden" name="returnType" value="">
        <input type="hidden" name="punishPrice" value="">
    </form>
    <table id="borrowList" class="list-table" align="center" style="margin-top: 10px;">
        <tr>
            <th>图书编号</th>
            <th>书名</th>
            <th>出版日期</th>
            <th>ISBN</th>
            <th>分类号</th>
            <th>借阅日期</th>
            <th>应还日期</th>
            <th>已还书次数</th>
            <th>可还书次数</th>
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

    <div class="action-container">
        选择归还方式：
         <div class="action-item" onclick="selectReturnType(1)">${BorrowService.isOverReturn(bookBorrow.borrow)?'超期归还':'正常归还'}</div>
         <div class="action-item" onclick="selectReturnType(2)">遗失归还</div>
    </div>

    <div class="action-body" id="action-body-1">
        <!-- 超期归还 -->
       <c:if test="${BorrowService.isOverReturn(bookBorrow.borrow)}">
        【超期天数】<label>${BorrowService.getOverReturnDays(bookBorrow.borrow)}天</label> 【罚款率】<label>${ReaderTypeService.find(reader.getRdType()).punishRate}元/天</label>
           【应罚款金额】<label id="shouldPunishPrice1">${BorrowService.getShouldPunishAmount(bookBorrow.borrow,1)}元</label>  【实际罚款金额】<input type="number" step="0.01" id="punishPrice1"/>
       </c:if>
    </div>

    <div class="action-body" id="action-body-2">
        <!-- 超期归还 -->
        【遗失图书单价】<label>${bookBorrow.book.bkPrice}元</label> 【应罚款金额】<label id="shouldPunishPrice2">${BorrowService.getShouldPunishAmount(bookBorrow.borrow,2)}元</label>（图书单价*3倍）  【实际罚款金额】<input type="number" step="0.01" id="punishPrice2"/>
    </div>

    <div class="bottom-panel">
        <button onclick="confirmReturn()" style="margin-right: 10px;" >确认还书</button>
        <button onclick="location.href='${ctxpath}/borrow/return.jsp'">取消</button>
    </div>
</c:if>
</body>
</html>
