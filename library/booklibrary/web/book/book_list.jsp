<%@ page import="java.util.List" %>
<%@ page import="com.study.library.model.ReaderType" %>
<%@ page import="com.study.library.dto.PageRequest" %>
<%@ page import="cn.hutool.core.util.StrUtil" %>
<%@ page import="com.study.library.dto.PageResponse" %>
<%@ page import="com.study.library.service.*" %>
<%@ page import="com.study.library.model.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../permission.jsp" %>
<html>
<head>
    <title>图书信息维护</title>
    <%@include file="../resource.jsp" %>
    <script>
        function doSearch(){

        }
    </script>
</head>
<body>
<br/><br/>
<center>图书信息维护</center>
<br/><br/>
<%
    PageRequest pageRequest=new PageRequest();
    if(StrUtil.isNotEmpty(request.getParameter("page")))
        pageRequest.setPage(Integer.parseInt(request.getParameter("page")));
    if(StrUtil.isNotEmpty(request.getParameter("size")))
        pageRequest.setSize(Integer.parseInt(request.getParameter("size")));
    if(StrUtil.isNotEmpty(request.getParameter("keyword")))
        pageRequest.getFilter().put("keyword",request.getParameter("keyword"));
    PageResponse<Book> pageResponse= BookService.list(pageRequest);
    request.setAttribute("pageRequest",pageRequest);
    request.setAttribute("pageResponse",pageResponse);
%>
<form name="searchForm" action="book_list.jsp" method="post">
    <input type="hidden" name="page" value="${pageRequest.page}">
    <input type="hidden" name="size" value="${pageRequest.size}">
    <input type="hidden" name="keyword" value="${param.keyword}">
</form>
<div class="form-search">
    关键词：<input type="text" id="keyword" value="${param.keyword}" placeholder="图书编号、图书名称、ISBN、分类号" style="width:300px;margin-right: 8px;"/>
    <input type="button" name="submit" value="查询" style="margin-right: 8px;" onclick="doSearch()">
</div>
<table class="list-table" align="center">
    <tr>
        <th>图书编号</th>
        <th>书名</th>
        <th>作者</th>
        <th>出版社</th>
        <th>出版日期</th>
        <th>ISBN</th>
        <th>分类号</th>
        <th>语言</th>
        <th>页数</th>
        <th>价格</th>
        <th>入馆日期</th>
        <th>图书状态</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${pageResponse.list}" var="item">
        <tr>
            <td>${item.bkCode}</td>
            <td>${item.bkName}</td>
            <td>${item.bkAuthor}</td>
            <td>${item.bkPress}</td>
            <td>${item.bkDatePress}</td>
            <td>${item.bkISBN}</td>
            <td>${item.bkCatalog}</td>
            <td>${BookService.getLanguage(item.bkLanguage)}</td>
            <td>${item.bkPages}</td>
            <td>${item.bkPrice}</td>
            <td>${item.bkDateIn}</td>
            <td>${item.bkStatus}</td>
            <td>
                <a href="book_update.jsp?bkID=${item.bkID}">修改</a>
                <a href="${ctxpath}/book?action=delete&bkID=${item.bkID}" onclick="return confirm('确认是否删除?')">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
