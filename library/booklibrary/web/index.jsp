<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="permission.jsp"%>
<html>
<head>
  <title>图书管理系统</title>
  <%@include file="resource.jsp"%>
  <style>
    .main-content{
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      height: 100%;
    }
    .main-top{
      height: 100px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      background-color: #8c679b;
    }
    .main-top-title{
      font-size:40px;
      width:300px;
      padding-left: 20px;
      color: #fff;
    }
    .main-top-menu{
      flex: 1;
      display: flex;
      justify-content: flex-end;
      padding-right: 20px;
      color: #fff;
    }
    .main-top-menu a{
      color:#fff;
    }
    .main-bottom{
      margin-top:1px;
      flex: 1;
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
    }
    .main-left-menu{
      width:220px;
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
    }
    .main-body{
      flex: 1;
      height: 100%;
      background-color: #eee;
      margin-left: 1px;
    }
    .menu-group{
      width:100%;
      display: flex;
      flex-direction: column;
    }
    .menu-title{
      background-color: #888;
      width: 100%;
      font-weight: bold;
      font-size:16px;
      padding:10px;
      box-sizing: border-box;
      color: #fff;
    }
    .menu-link{
      font-size:16px;
      padding: 10px;
      box-sizing: border-box;
      cursor: pointer;
    }
    .menu-link:hover{
      background-color: #eee;
    }
    .menu-link a{
      text-decoration: none;
      color:#000;
    }
  </style>
  <script>
    $(function(){
      $("div.menu-link").click(function(){
        $(this).find("a")[0].click();
      });
    })
  </script>
</head>
<body>
  <div class="main-content">
    <div class="main-top">
      <div class="main-top-title">图书管理系统</div>
      <div class="main-top-menu">
        欢迎您，${sessionScope.loginUser.name} ！
        <a href="change_pwd.jsp" target="page" style="margin-right: 10px;">修改密码</a>
        <a href="index?action=logout">退出登录</a>
      </div>
    </div>
    <div class="main-bottom">
      <div class="main-left-menu">

        <!-- 根据角色不同，显示不同的菜单-->
        <c:if test="${sessionScope.loginUser.isCardAdmin() or sessionScope.loginUser.isSystemAdmin()}">
          <div class="menu-group">
            <div class="menu-title">读者管理</div>
            <div class="menu-link"> <a href="card/create_card.jsp" target="page">办理借书证</a></div>
            <div class="menu-link"> <a href="card/change_card.jsp" target="page">变更借书证</a></div>
            <div class="menu-link"> <a href="card/loss_card.jsp" target="page">挂失借书证</a></div>
            <div class="menu-link"> <a href="card/restore_card.jsp" target="page">解除挂失</a></div>
            <div class="menu-link"> <a href="card/reapply_card.jsp" target="page">补办借书证</a></div>
            <div class="menu-link"> <a href="card/cancel_card.jsp" target="page">注销借书证</a></div>
            <div class="menu-link"> <a href="card/print_card.jsp" target="page">打印借书证</a></div>
            <div class="menu-link"> <a href="card/type_list.jsp" target="page">读者类型管理</a></div>
          </div>
        </c:if>
        <c:if test="${sessionScope.loginUser.isBookAdmin()}">
          <div class="menu-group">
            <div class="menu-title">图书管理</div>
            <div class="menu-link"> <a href="book/book_create.jsp" target="page">新书入库</a></div>
            <div class="menu-link"> <a href="book/book_list.jsp" target="page">图书信息维护</a></div>
          </div>
        </c:if>
        <c:if test="${sessionScope.loginUser.isBorrowingAdmin()}">
          <div class="menu-group">
            <div class="menu-title">借阅管理</div>
            <div class="menu-link"> <a href="borrow/borrow.jsp" target="page">借书</a></div>
            <div class="menu-link"> <a href="borrow/continue_borrow.jsp" target="page">续借</a></div>
            <div class="menu-link"> <a href="borrow/return.jsp" target="page">还书</a></div>
          </div>
        </c:if>
      </div>
      <div class="main-body">
        <iframe name="page" src="" width="100%" height="100%" scrolling="0" frameborder="0"></iframe>
      </div>
    </div>
  </div>
</body>
</html>
