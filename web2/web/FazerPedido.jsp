<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@page import="com.web2.model.beans.Roupa" %>
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
    <script src="./main.js" type="text/javascript"></script>
    
    <title>Lavenderia LOL</title>
</head>

<body>
   
    <h:navbar />
    
       
    <div class="columns">
        <div class="column">
            <div class="box">
                <h1 class="title is-5">Fazer Pedido</h1>
            </div>
            <div class="box">
                <form id="form" method="post" action='<c:url value="/ClienteHome?acao=fazerPedido" />'>
                    <div class="field">
                        <div class="columns">
                            <div class="column is-11 is-offset-0">
                                <label class="label">Peças para Lavar</label>
                            </div>
                        </div>
                        <!-- Lista das Peças para Lavar -->
                        <div class="pb-5" id="listaPeca">
                            
                            
                            <div class="columns is-mobile" id="peca1">
                                <div class="column is-8">
                                    <div>
                                        <label>Peça 1</label>
                                    </div>
                                    <div class="select is-fullwidth">
                                        <select name="id" id="id1" onchange="atualizaPreco('id1', 'qtde1')">
                                            <c:forEach var="item" items="${lista}">
                                                <option value="<c:out value="${item.id}"></c:out>"><c:out value="${item.nome}"></c:out></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="is-flex">
                                    <button class="button is-danger is-inverted mt-auto mb-3" type="button" onclick="qtdePecaRoupa('-', 'peca1')">-</button>
                                    <div class="m-auto">
                                        <label>QTDE</label>
                                        <input class="input" type="number" min="1" name="qtde" placeholder="XX" value="1" id="qtde1" onchange="atualizaPreco('id1', 'qtde1')" />
                                    </div>
                                    <button class="button is-danger is-inverted mt-auto mb-3" type="button" onclick="qtdePecaRoupa('+', 'peca1')">+</button>

                                </div>
                            </div>

                            
                        </div>
                    </div>
                </form>
                <div class="is-flex mt-5 pb-3">
                    <div class="is-flex m-auto">
                        <button class="button is-danger is-inverted has-background-light pl-6 pr-6 mr-3" type="button" onclick="addRemPecaRoupa('rem')">-</button>
                        <button class="button is-danger is-inverted has-background-light pl-6 pr-6 ml-3" type="button" onclick="addRemPecaRoupa('add')">+</button>
                    </div>
                </div>
            </div>

            <div class="is-flex mb-5">
                <div class="m-auto is-flex">

                    <div class="has-text-success mt-auto mb-auto  mr-5">Preço Total:</div>
                    <div class="mt-auto mb-auto mr-5">
                        <input class="input" type="charset" placeholder="R$XX.XX" id="precoTotal" disabled />
                    </div>

                    <div class="mt-auto mb-auto">Prazo Estimado: <span id="prazoRoupa">X</span> Dias</div>

                </div>
            </div>

            <div class="is-flex">

                <div class="m-auto is-8">

                    <a href="<c:url value="/ClienteHome"/>"><button class="button is-danger is-rounded pl-6 pr-6 mr-6">Cancelar</button></a>
                    <button class="button is-success is-rounded pl-6 pr-6 ml-6" form="form">Confirmar</button>
                </div>

            </div>

        </div>

    </div>
</body>
    <script src="./GetRoupaJson" type="text/javascript"></script>
</html>