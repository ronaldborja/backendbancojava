<%-- 
    Document   : listar_todo
    Created on : 7/04/2024, 11:25:11 a. m.
    Author     : usuario
--%>

<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="repository.SucursalCrud"%>
<%@page import="services.SucursalService"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Sucursal"%>

<% 
    //List<Sucursal> listado  = (List<Sucursal>) request.getSession().getAttribute("sucursal.all");  
    String msg = null; 

    //if (listado == null || listado.isEmpty()) {
      //  msg = "No se encontraron sucursales en el sistema"; 
    //}
%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema bancario</title>
    </head>
<body>
    <center>
        <h1>REPORTE DE SUCURSALES</h1>
        <hr>
        <%
            if (msg != null) {
                out.print(msg);
            } else {
        %>
            <fieldset style="width: 30%">
                <legend>Información sucursales: </legend>
                <table>
                    <tr>
                        <th>#</th>
                        <th>CODIGO POSTAL</th>
                        <th>DIRECCION</th>
                    </tr>

                    <%
                        EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
                        SucursalCrud sucursalCrud = new SucursalCrud(conBD); //Objeto para accede a métodos CRUD  
                        List<Sucursal> sucs = sucursalCrud.findSucursalEntities();
                        int i = 0; 
                        for(Sucursal suc: sucs) {
                            i = i+1; 
                    %> 
                        <tr style="text-align: left;">
                            <td><%=i%></td>
                            <td><%=suc.getCodigoPostal()%></td>
                            <td><%=suc.getDireccion()%></td>
                        </tr>
                    <%
                    }
                    %>
                </table>
            </fieldset>
        <%
        }
        %>

    </center>
</html>
