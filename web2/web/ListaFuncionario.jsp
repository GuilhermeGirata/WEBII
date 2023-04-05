
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ page errorPage="/Erro.jsp"%>

<c:if test="${empty loginBean}">
    <c:set var="msg" value="Você não está logado" scope="request" />
    <c:set var="page" value="home" scope="request" />
    <jsp:forward page="/home" />
</c:if>
<c:if test="${empty loginBean.idFuncionario}">
    <c:set var="msg" value="Acesso Restrito Ao Funcionario!" scope="request" />
    <c:set var="page" value="home" scope="request" />
    <jsp:forward page="/home" />
</c:if>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
  <link rel="stylesheet" href="./main.css">
  <title>Lavenderia LOL</title>
</head>

<body>

        <h:navbar funcionario="."/>
        <div class="column">
            <div class="box">
                <p class="title is-5">Funcionários</p>
            </div>

            <div class="box">
                <!-- Comeco lista funcionario-->
                <div class="content">

                <c:forEach var="item" items="${lista}">
                    <div class="columns is-flex is-vcentered has-background-light m-2"> 
                        <div class="column is-10">
                            <p><c:out value="${item.nome}"/> - <c:out value="${item.email}"/></p>
                        </div>
                        <div class="column">
                            <a class="button is-warning is-rounded is-centered" href="<c:url value="/Funcionario?acao=editar&id=${item.id}"/>">Editar</a>
                        </div>
                    </div>
                </c:forEach>

                </div>
                <!-- Fim lista Funcionario -->
            </div>
        </div>
    </div>
</body>

</html>