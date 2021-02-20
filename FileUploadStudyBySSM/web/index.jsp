<%--
  Created by IntelliJ IDEA.
  User: nxt
  Date: 2021/2/20
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form method="post" enctype="multipart/form-data" action="<%=path%>/upload">
    <input type="file" name="files" multiple>
    <input type="submit" name="提交">
  </form>
  </body>
</html>
