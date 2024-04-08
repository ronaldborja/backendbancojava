<%-- 
    Document   : error
    Created on : 6/04/2024, 10:35:16 p. m.
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String msg = request.getParameter("msg"); 
    %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema Bancario</title>
    </head>
    <body>
        <h1><%=(msg != null) ? msg : ""%></h1>
    </body>
</html>
