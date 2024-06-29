<%@ page import="com.study.library.model.Book" %>
<%@ page import="com.study.library.service.BookService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../permission.jsp" %>
<html>
<head>
    <title>修改图书信息</title>
    <%@include file="../resource.jsp" %>
    <script>

    </script>
</head>
<body>
<br/><br/>
<center>修改图书信息</center>
<br/><br/>
<%
    String bkID=request.getParameter("bkID");
    Book book=BookService.find(Integer.valueOf(bkID));
    request.setAttribute("book",book);
%>
<form action="${ctxpath}/book" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="bkID" value="${book.bkID}">
    <table style="width:600px" class="form-table" align="center">
        <tr>
            <td style="width:130px">图书编号(*)</td>
            <td>
                <input type="text" name="bkCode" value="${book.bkCode}" readonly required/>
            </td>
        </tr>
        <tr>
            <td >图书名称(*)</td>
            <td>
                <input type="text" name="bkName" value="${book.bkName}" required/>
            </td>
        </tr>
        <tr>
            <td >作者</td>
            <td>
                <input type="text" name="bkAuthor" value="${book.bkAuthor}" />
            </td>
        </tr>
        <tr>
            <td >出版社</td>
            <td>
                <input type="text" name="bkPress" value="${book.bkPress}" />
            </td>
        </tr>
        <tr>
            <td >出版日期</td>
            <td>
                <input type="date" name="bkDatePress" value="${book.bkDatePress}" />
            </td>
        </tr>
        <tr>
            <td >ISBN书号</td>
            <td>
                <input type="text" name="bkISBN" value="${book.bkISBN}" />
            </td>
        </tr>

        <!-- 分类号 -->
        <tr>
            <td >分类号</td>
            <td>
                <input type="text" name="bkCatalog" value="${book.bkCatalog}" />
            </td>
        </tr>
        <!-- 语言 -->
        <tr>
            <td >语言</td>
            <td>
                <select name="bkLanguage">
                    <option value="0" ${book.bkLanguage == 0 ? 'selected' : ''}>中文</option>
                    <option value="1" ${book.bkLanguage == 1 ? 'selected' : ''}>英文</option>
                    <option value="2" ${book.bkLanguage == 2 ? 'selected' : ''}>日文</option>
                    <option value="3" ${book.bkLanguage == 3 ? 'selected' : ''}>俄文</option>
                    <option value="4" ${book.bkLanguage == 4 ? 'selected' : ''}>德文</option>
                    <option value="5" ${book.bkLanguage == 5 ? 'selected' : ''}>法文</option>
                </select>
            </td>
        </tr>
        <!-- 页数 -->
        <tr>
            <td >页数</td>
            <td>
                <input type="number" name="bkPages" value="${book.bkPages}" />
            </td>
        </tr>
        <!-- 价格 -->
        <tr>
            <td >价格(*)</td>
            <td>
                <input type="number" step="0.01" name="bkPrice" value="${book.bkPrice}" required/>
            </td>
        </tr>
        <!-- 入馆日期 -->
        <tr>
            <td >入馆日期(*)</td>
            <td>
                <input type="date" name="bkDateIn" value="${book.bkDateIn}" required/>
            </td>
        </tr>
        <!-- 内容简介 -->
        <tr>
            <td >内容简介</td>
            <td>
                <textarea name="bkBrief" style="width: 100%;height: 100px;">${book.bkBrief}</textarea>
            </td>
        </tr>
        <!-- 图书封面照片 -->
        <!-- 注意：这里仅展示了一个文件上传按钮，实际上传和处理逻辑需要在后端实现 -->
        <tr>
            <td >图书封面照片</td>
            <td>
                <input type="file" name="bkCover" accept="image/*" />
                <c:if test="${book.bkCover != null and fn:length(book.bkCover)>0}">
                    <img src="${HttpUtils.getImageBase64(book.bkCover)}" style="max-width:64px"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="form-table-btns" colspan="2">
                <input type="submit" name="button" id="button" value="修改" style="margin-right: 10px;">
                <input type="button" name="button2" id="button2" value="取消" onclick="location.href='book_list.jsp'">
            </td>

        </tr>
    </table>

</form>
</body>
</html>
