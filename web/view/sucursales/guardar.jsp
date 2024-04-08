<%-- 
    Document   : guardar
    Created on : 6/04/2024, 9:33:09 p. m.
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    String msg = request.getParameter("msg");
    String rutaRaiz = request.getServletContext().getContextPath();
%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/otros.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema Bancario</title>
    </head>

<body>
    <center>
        <h1>AÑADIR SUCURSAL</h1>

    <form action="<%=rutaRaiz%>/csucursal" method="POST">
            <table>
                <tr>
                    <th style="text-align: right;" class="encabezado">DIRECCION:</th>
                    <td><input type="text" id="dir" name="dir" require placeholder="Digite la dirección"></td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">CODIGO POSTAL:</th>
                    <td><input type="text" id="cod" name="cod" require placeholder="Digite el codigo postal"></td>
                </tr>

                <tr>
                    <td>&nbsp</td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: center;" class="buttons">
                        <input type="submit" id="accion" name="accion" value="Guardar">&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="reset" id="clean" value="Limpiar">
                    </td>
                </tr>
            </table>
        </form>
        <span> <%= (msg != null) ? msg : ""%></span>
    </center>
</body>
</html>
