<%--
  Created by IntelliJ IDEA.
  User: nxt
  Date: 2021/2/19
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%String path = request.getContextPath();%>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form method="post" enctype="multipart/form-data" action="<%=path%>/upload">
    用户名：<input type="text" name="username">
    <p><input type="file" name="file1"/><p>
    <p><input type="file" name="file2"><p>
    <input type="submit" value="上传">
    <input type="reset" value="重置">
  </form>
  </body>
</html>
