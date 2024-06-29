<%@ page import="com.study.library.util.HttpUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<%=request.getContextPath() %>/css/style.css?t=<%=System.currentTimeMillis()%>" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script defer='defer'>
    let err="<%=HttpUtils.getMessage(request)%>";
    $(function(){
        if(err!='' && err!='null'){
            window.setTimeout(()=>{
                alert(err)
            },200)
        }
    })
</script>