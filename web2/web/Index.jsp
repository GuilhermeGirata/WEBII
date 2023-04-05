
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page errorPage="/Erro.jsp"%>

<c:if test="${!empty loginBean}">
    <jsp:forward page="/home" />
</c:if>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
          <meta charset="UTF-8">
         <meta http-equiv="X-UA-Compatible" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">

        <link rel="stylesheet" href="https://unpkg.com/bulma@0.9.0/css/bulma.min.css" />
        <link rel="stylesheet" type="text/css" href="landing.css">
        <script src="./main.js"></script>
        <title>Página Principal</title>
    </head>
    <body >
        <section class="hero is-info is-fullheight" style="background-image: url('./background.jpg'); background-size:contain; background-position:center;">
            <div class="hero-head">
                <nav class="navbar" style="min-height:80px">
                    <div class="container">
                        <div class="navbar-brand">
                            <img src="logo2.png" width="200" height="430"/>
                            <span class="navbar-burger burger" data-target="navbarMenu">
                                <span></span>
                                <span></span>
                                <span></span>
                            </span>
                        </div>
                        <div id="navbarMenu" class="navbar-menu">
                            <div class="navbar-end">
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="<c:url value="/login?acao=gologin"/>">
                                        <span class="icon">
                                            <i class="fa fa-home"></i>
                                        </span>
                                        <span>Login</span>
                                    </a>
                                </span>
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="<c:url value="/Cadastrar"/>">
                                        <span class="icon">
                                            <i class="fa fa-user-plus"></i>
                                        </span>
                                        <span>Cadastro</span>
                                    </a>
                                </span>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>

            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-6 is-offset-3">
                        <h1 class="title">
                            A melhor lavanderia online do Mundo!
                        </h1>
                        <h2 class="subtitle">
                            Com a nossa rapidez na lavagem e na entrega da roupas, garantimos sua roupa lavada da melhor maneira possível!
                        </h2>
                        <p class="has-text-danger"><c:out value="${msg}"/></p>
                    </div>
                </div>
            </div>

        </section>
    </body>
</html>
