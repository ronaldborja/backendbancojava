<%-- 
    Document   : buscar
    Created on : 7/04/2024, 10:31:30 a. m.
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Sucursal"%>
<% 
    String rutaRaiz = request.getServletContext().getContextPath();
    String msg = request.getParameter("msg");
    Sucursal suc = (Sucursal)session.getAttribute("sucursal.buscar"); 
%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/other_styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema Bancario</title>
    </head>

<body>
    <center>
        <h1>BUSCAR SUCURSAL</h1>

        <form action="<%=rutaRaiz%>/csucursal" method="POST">
            <table>
                <tr>
                    <th style="text-align: right;" class="encabezado">Id:</th>
                    <td><input type="number" id="id" name="id" 
                        value="<%=(suc != null) ? suc.getId(): ""%>" required placeholder="Codigo sucursal"></td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">CODIGO POSTAL:</th>
                    <td><input type="text" id="cod" name="cod"
                               value="<%=(suc != null) ? suc.getCodigoPostal(): ""%>"></td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">DIRECCION:</th>
                    <td><input type="text" id="dir" name="dir"
                               value="<%=(suc != null) ? suc.getDireccion(): ""%>"></td>
                </tr>

                <tr>
                    <td>&nbsp</td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: right;" class="buttons">
                        <input type="submit" id="accion" name="accion" value="Buscar">
                        <input type="reset" id="clean" value="Limpiar">
                        <input type="submit" id="editar" name="accion" value="Editar">
                        <input type="submit" id="eliminar" name="accion" value="Eliminar">

                    </td>
                </tr>
            </table>
        </form>
        <span><%= (msg != null) ? msg : ""%></span>
    </center>
    <script>
        //habilitarBotones();
        //confirmarOperacion();
    </script>
</body>
</html>
</html>

