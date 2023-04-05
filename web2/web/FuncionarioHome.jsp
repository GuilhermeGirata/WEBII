
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

  <!-- https://fontawesome.com/icons/bars?s=solid&f=classic -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css">
  <link rel="stylesheet" href="./main.css">
  <script src="./main.js"></script>
  
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma-calendar@6.1.14/dist/css/bulma-calendar.min.css">
  <script src="./bulma-calendar.min.js" type="text/javascript"></script>
  <link href="./bulma-calendar.min.css" rel="stylesheet" type="text/css"/>
  <title>Lavenderia LOL</title>
</head>

<body>
    
    <h:navbar funcionario="."/>
    <div class="column">
        <div class="box" style="min-height: 748px;">
            <h1 class="titulo">Consultar Pedidos</h1>
            <br>
            <div class="box ml-2 mr-2 is-flex is-mobile" style="height: 4em;">
                <a class="button is-warning is-outlined is-small ml-2" href="<c:url value="/FuncionarioHome"/>">Em Aberto</a>
                <a class="button is-info is-outlined is-small ml-2" href="<c:url value="/FuncionarioHome?acao=todos"/>">Todos</a>
                <a class="button is-info is-outlined is-small ml-2" href="<c:url value="/FuncionarioHome?acao=hoje"/>">Hoje</a>
                
                <form action="./FuncionarioHome?acao=data" method="post" class="is-flex">                    
                    <label id="label1" class="is-small ml-3 mr-3">Data Início:</label>
                    <input class="ml-1 calend" type="date" id="dtinic" name="dataInicio">
                    
                    <label id="label2" class="is-small ml-3 mr-3">Data Fim:</label>
                    <input class="ml-1 calend" type="date" id="dtfim" name="dataFim">
                    
                    <button class="button is-success is-outlined is-small ml-5" type="submit">Filtrar</button>
                </form>
            </div>
            <p class="has-text-danger"><c:out value="${erro}"/></p>
            <br>
            <!--Lista de  dropdowns -->
            <div id="listaDropdowns">
                <c:forEach var="item" items="${lista}">
                    <h:dropdown pd="${item}" funcionario="."/>
                </c:forEach>     
            </div>
        </div>
    </div>
</body>
<script src="./calendarConfig.js"></script>
</html>