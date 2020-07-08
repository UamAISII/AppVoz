<%-- 
    Document   : speechToText
    Created on : Nov 17, 2019, 10:20:22 PM
    Author     : beatriz
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width">

        <title>Sistema de Consultas</title>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

        <link rel="stylesheet" href="index.css">

        <!--[if lt IE 9]>
          <script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->


    </head>

    <body>
        <center>

            <h1 class="titulo">Interfaz de Consulta</h1>
      
        
            <!--<button><img src="img/microfono.png" style="width:100px;height:100px"></button>-->
            <button>INICIAR CONSULTA</button>
        
          </center>


        <div>
          <!--  <p class="phrase">Frase...</p> -->
          <!-- <p class="result">Identificada o no?</p> -->
            <p class="output">Mensaje</p>
        </div>
         <!--<strong>Respuesta</strong>: -->
        <div id="consultaRespuesta"></div>
        <script src="index.js"></script> 
    </body>
</html>
