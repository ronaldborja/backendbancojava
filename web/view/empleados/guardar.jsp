<%-- 
    Document   : agregar
    Created on : 7/04/2024, 12:29:18 p. m.
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
        <link rel="stylesheet" href="css/other_styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema Bancario</title>
    </head>
<body>
    <center>
        <h1>AÑADIR EMPLEADO</h1>

        <form action="<%=rutaRaiz%>/cempleado" method="POST">
            <table>
                <tr>
                    <th style="text-align: right;" class="encabezado">DNI:</th>
                    <td><input type="text" id="dni" name="dni" require placeholder="Digite la dirección"></td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">NOMBRE:</th>
                    <td><input type="text" id="nombre" name="nombre" require placeholder="Digite el nombre"></td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">DIRECCION:</th>
                    <td><input type="text" id="dir" name="dir" require placeholder="Digite la dirección"></td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">TELEFONO:</th>
                    <td><input type="text" id="cel" name="cel" require placeholder="Digite el telefono"></td>
                </tr>

                
                <tr>
                    <th style="text-align: right;" class="encabezado">FECHA NACIMIENTO:</th>
                    <td><input type="text" id="fn" name="fn" require placeholder="Digite la fecha de nac"></td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">SEXO:</th>
                    <td><input type="text" id="sex" name="sex" require placeholder="Digite el sexo"></td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">CODIGO SUCURSAL:</th>
                    <td><input type="text" id="suc_id" name="suc_id" require placeholder="Digite la sucursal a la que pertenece"></td>
                </tr>

                <tr>
                    <td>&nbsp</td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: center;" class="buttons">
                        <input type="submit" id="accion" name="accion" value="Guardar">
                        <input type="reset" id="clean" value="Limpiar">
                    </td>
                </tr>
            </table>
        </form>
        <span> <%= (msg != null) ? msg : ""%></span>
    </center>
</body>
</html>
