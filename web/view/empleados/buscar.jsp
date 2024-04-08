<%-- 
    Document   : buscar
    Created on : 7/04/2024, 1:38:48 p. m.
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Empleado"%>
<% 
    String rutaRaiz = request.getServletContext().getContextPath();
    String msg = request.getParameter("msg");
    Empleado e = (Empleado)session.getAttribute("empleado.buscar"); 
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema Bancario</title>
        <link rel="stylesheet" href="css/other_styles.css">
    </head>
    
<body>
    <center>
        <h1>BUSCAR EMPLEADO</h1>

        <form action="<%=rutaRaiz%>/cempleado" method="POST">
            <table>
                <tr>
                    <th style="text-align: right;" class="encabezado">DNI:</th>
                    <td><input type="text" id="dni" name="dni" 
                    value="<%=(e != null) ? e.getDni(): ""%>" required placeholder="DNI del empleado"></td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">NOMBRE:</th>
                    <td><input type="text" id="nombre" name="nombre"
                    value="<%=(e != null) ? e.getNombre(): ""%>"</td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">DIRECCION:</th>
                    <td><input type="text" id="dir" name="dir"
                    value="<%=(e != null) ? e.getDireccion(): ""%>"
                </td> 
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">TELEFONO:</th>
                    <td><input type="text" id="cel" name="cel"
                    value="<%=(e != null) ? e.getTelefono(): ""%>"</td>
                </tr>

                
                <tr>
                    <th style="text-align: right;" class="encabezado">FECHA NACIMIENTO:</th>
                    <td><input type="text" id="fn" name="fn"
                    value="<%=(e != null) ? e.getFechanacimiento(): ""%>"</td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">SEXO:</th>
                    <td><input type="text" id="sex" name="sex"
                    value="<%=(e != null) ? e.getSexo(): ""%>"</td>
                </tr>

                <tr>
                    <th style="text-align: right;" class="encabezado">SUCURSAL:</th>
                    <td><input type="text" id="suc_id" name="suc_id"
                               value="<%=(e != null) ? e.getSucursalid().getId(): ""%>"</td>
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
