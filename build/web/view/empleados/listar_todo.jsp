<%-- 
    Document   : listar_todo
    Created on : 7/04/2024, 1:53:18 p. m.
    Author     : usuario
--%>

<%@page import="models.Empleado"%>
<%@page import="java.util.List"%>
<%@page import="repository.EmpleadoCrud"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Sistema Bancario</title>
    </head>
    
    <body>
    <center>
        <h1>REPORTE DE EMPLEADOS</h1>
        <hr>
        <%
            if (msg != null) {
                out.print(msg);
            } else {
        %> 

            <fieldset style="width: 70%">
                <legend>Información empleados: </legend>
                <table>
                    <tr>
                        <th>#</th>
                        <th>DNI</th>
                        <th>NOMBRE</th>
                        <th>DIRECCION</th>
                        <th>TELEFONO</th>
                        <th>FECHA NACIMIENTO</th>
                        <th>SEXO</th>
                        <th>SUCURSAL</th>
                    </tr>

                    <%
                        EntityManagerFactory conBD = Persistence.createEntityManagerFactory("sistemabancarioPU");
                        EmpleadoCrud empleadoCrud = new EmpleadoCrud(conBD); //Objeto para accede a métodos CRUD  
                        List<Empleado> emps = empleadoCrud.findEmpleadoEntities();
                        int i = 0; 
                        for(Empleado emp: emps) {
                            i = i+1; 
                    %> 
                        <tr style="text-align: left;">
                            <td><%=i%></td>
                            <td><%=emp.getDni()%></td>
                            <td><%=emp.getNombre()%></td>
                            <td><%=emp.getDireccion()%></td>
                            <td><%= emp.getTelefono() %></td>
                            <td><%= emp.getFechanacimiento() %></td>
                            <td><%= emp.getSexo() %></td>
                            <td><%= emp.getSucursalid().getDireccion() %></td>
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
    
</body>
    
</html>
