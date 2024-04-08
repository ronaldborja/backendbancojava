<%-- 
    Document   : index
    Created on : 6/04/2024, 10:09:03 p. m.
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 

<head>
    <meta name="viewport" content="with=device-with, initial-scale=1.0">
    <title>Sistema Bancario</title>
    <link rel="stylesheet" href="/backendbanco/view/css/style_index.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@200&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>

<body> 
    <section class="header">
            <ul class="nav">
                    <li><a href="">CLIENTE NATURAL</a>
                        <ul class="clientes">
                            <li><a href="../backendbanco/view/naturales/agregar.php">Añadir Cliente Natural</a>
                            <li><a href="../backendbanco/view/naturales/buscar.php">Buscar Cliente Natural</a>
                            <li><a href="../backendbanco/view/naturales/buscar.php">Actualizar Cliente Natural</a>
                            <li><a href="../backendbanco/view/naturales/buscar.php">Eliminar Cliente Natural</a>
                            <li><a href="../backendbanco/view/naturales/listar_todo.php">Listar Clientes Naturales</a>
                        </ul>
                    </li>

                    <li><a href="">ORGANIZACIONES</a>
                        <ul class="organizacion">
                            <li><a href="../backendbanco/view/organizacion/agregar.php">Añadir Organizacion</a>
                            <li><a href="../backendbanco/view/organizacion/buscar.php">Buscar Organizacion </a>
                            <li><a href="../backendbanco/view/organizacion/buscar.php">Actualizar Organizacion </a>
                            <li><a href="../backendbanco/view/organizacion/buscar.php">Eliminar Organizacion </a>
                            <li><a href="../backendbanco/view/organizacion/listar_todo.php">Listar Organizaciones</a>
                        </ul>
                    </li>


                    <li><a href="">CUENTAS</a>
                        <ul class="cuenta">
                            <li><a href="../backendbanco/view/cuentas/agregar.php">Añadir Cuenta</a>
                            <li><a href="../backendbanco/view/cuentas/buscar.php">Buscar Cuenta</a>
                            <li><a href="../backendbanco/view/cuentas/buscar.php">Actualizar Cuenta</a>
                            <li><a href="../backendbanco/view/cuentas/buscar.php">Eliminar Cuenta</a>
                            <li><a href="../backendbanco/view/cuentas/listar_todo.php">Listar Cuentas</a>
                        </ul>
                    </li>

                    <li><a href="">EMPLEADOS</a>
                        <ul class="empleado">
                            <li><a href="view/empleados/guardar.jsp">Añadir Empleado</a>
                            <li><a href="view/empleados/buscar.jsp">Buscar Empleado</a>
                            <li><a href="view/empleados/buscar.jsp">Actualizar Empleado</a>
                            <li><a href="view/empleados/buscar.jsp">Eliminar Empleado</a>
                            <li><a href="view/empleados/listar_todo.jsp">Listar Empleados</a>
                        </ul>
                
                    </li>
                    <li><a href="">SUCURSALES</a>
                    <ul class="sucursal">
                            <li><a href="view/sucursales/guardar.jsp">Añadir Sucursal</a>
                            <li><a href="view/sucursales/buscar.jsp">Buscar Sucursal</a>
                            <li><a href="view/sucursales/buscar.jsp">Actualizar Sucursal</a>
                            <li><a href="view/sucursales/buscar.jsp">Eliminar Sucursal</a>
                            <li><a href="view/sucursales/listar_todo.jsp">Listar Sucursales</a>
                        </ul>
                    </li>

                    <li><a href="">INICIAR SESION</a></li>
                    <li><a href="">INICIO</a></li>
                    
            </ul>

    <div class="text-box">
        <h1>Sistema Bancario </h1>
        <p>Maneja tu dinero de forma facil y segura</p>
        <a href="../view/cuentas/agregar.php" class="conecta-boton">Crea tu cuenta</a>
    </div>
    </section>  