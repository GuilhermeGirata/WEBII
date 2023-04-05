
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ page errorPage="/Erro.jsp"%>

<c:if test="${empty loginBean}">
    <c:set var="msg" value="Você não está logado" scope="request" />
    <c:set var="page" value="home" scope="request" />
    <jsp:forward page="/home" />
</c:if>

<c:if test="${empty loginBean.cpfCliente}">
    <c:set var="msg" value="Acesso Restrito Ao Cliente!" scope="request" />
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
  <title>Lavenderia LOL</title>
</head>

<body>
    
    <h:navbar />
    <div class="column">
        <div class="box" style="min-height: 748px;">
            <h1 class="titulo mb-5">Seus Pedidos</h1>
                        <br>
            <div class="box ml-2 mr-2 is-flex" >
                <a class="button is-info is-outlined is-small ml-2" href="<c:url value="/ClienteHome"/>">Todos</a>
                <a class="button emAberto is-outlined is-small ml-2" href="<c:url value="/ClienteHome?acao=pesquisa&status=AB"/>">Aberto</a>
                <a class="button cancelado is-outlined is-small ml-2" href="<c:url value="/ClienteHome?acao=pesquisa&status=CA"/>">Cancelado</a>
                <a class="button cancelado is-outlined is-small ml-2" href="<c:url value="/ClienteHome?acao=pesquisa&status=RJ"/>">Rejeitado</a>
                <a class="button recolhido is-outlined is-small ml-2" href="<c:url value="/ClienteHome?acao=pesquisa&status=RC"/>">Recolhido</a>
                <a class="button aguardandoPagamento is-outlined is-small ml-2" href="<c:url value="/ClienteHome?acao=pesquisa&status=AP"/>">A Pagar</a>
                <a class="button pago is-outlined is-small ml-2" href="<c:url value="/ClienteHome?acao=pesquisa&status=PG"/>">Pago</a>
                <a class="button finalizado is-outlined is-small ml-2" href="<c:url value="/ClienteHome?acao=pesquisa&status=FI"/>">Finalizado</a>
            
                <form action="<c:url value="/ClienteHome?acao=pesquisaText"/>" method="post" class="m-auto is-flex">
                    <p class="control has-icons-left m-auto">
                        <input class="input is-small" type="number" name="texto" placeholder="Pesquisar" autocomplete="off">
                        <span class="icon is-small is-left">
                            <i class="fa-solid fa-magnifying-glass"></i>
                        </span>
                    </p>
                    <input class="ml-1 button is-small is-success" type="submit" value="Pesquisar">
                </form>
            </div>
            
            <c:if test="${!empty msgPesq}">
                <h3 class="mb-3">Resultado Pesquisa Pedido: <c:out value="${msgPesq}"/></h3>
            </c:if>
            <br>
            <!--Lista de  dropdowns -->
            <div id="listaDropdowns">
                <c:forEach var="item" items="${lista}">
                    <h:dropdown pd="${item}" />
                </c:forEach>     
            </div>
        </div>
    </div>
</body>

</html>