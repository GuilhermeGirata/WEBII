
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt'  prefix='fmt' %>
<%@ page errorPage="/Erro.jsp"%>

<c:if test="${empty loginBean}">
    <c:set var="msg" value="Você não está logado" scope="request" />
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
  <title>Lavenderia LOL</title>
</head>

<body>

    <h:navbar funcionario="${funcionario}"/>
    <div class="columns">
        <div class="column">
            <div class="box">
                <p class="title is-5">Consulta de Pedido</p>
                <p class="title is-6">ID: <c:out value="${pd.id}"/></p>
                <p>Cliente: <c:out value="${pd.nomeCliente}"/></p>
                <p>CEP: <c:out value="${pd.cepCliente}"/></p>
                <p>Endereço: ${(pd.endereco.cep == 'ERRO')? 'API Indísponível': pd.endereco}.</p>
                <p>N°: <c:out value="${pd.numCliente}"/></p>
                <p>Complemento: <c:out value="${pd.compCliente}"/></p>
            </div>
            <div class="box" >
                <p class="title is-5">Lista de Roupas</p>
                <div class="content m-3">
                    <ol class="p-3">
                        <c:forEach var="item2" items="${pd.roupas}">
                            <li class="mb-3">PEÇA: <c:out value="${item2.nome}"/> - QTDE <c:out value="${item2.qtde}"/></li>
                        </c:forEach>
                    </ol>
                </div>
            </div>

            <div class="box" >
                <form>
                    <div class="field">
                        <label class="label mb-5">Preço total: R$ <c:out value="${pd.preco}"/></label>
                        
                        <p>Data do Pedido:
                            <fmt:parseDate value="${pd.dataAbertura}" pattern="yyyy-MM-dd" var="date3" type="date"/>
                            <fmt:formatDate pattern="dd/MM/yyyy" value="${date3}"/>
                        </p>
                        <p>Prazo de Entrega:
                            <fmt:parseDate value="${pd.dataPrazo}" pattern="yyyy-MM-dd" var="date2" type="date"/>
                            <fmt:formatDate pattern="dd/MM/yyyy" value="${date2}"/>
                        </p>
                        <c:if test="${(empty pd.dataPagamento)? false: true}">
                            <p class="has-text-success">Data do Pagamento:
                                <fmt:parseDate value="${pd.dataPagamento}" pattern="yyyy-MM-dd" var="date1" type="date"/>
                                <fmt:formatDate pattern="dd/MM/yyyy" value="${date1}"/>
                            </p>
                        </c:if>
                        <div class="is-flex mt-3">
                              
                             <c:if test="${(empty funcionario)?true:false}">
                                 <c:if test="${pd.status == 'AB'}">
                                     <a class="button is-danger is-rounded m-auto" href="<c:url value="/ClienteHome?acao=pedido&id=${pd.id}&status=CA"/>">Cancelar Pedido</a>
                                 </c:if>
                                 <c:if test="${pd.status == 'AP'}">
                                     <a class="button is-success is-rounded m-auto" href="<c:url value="/ClienteHome?acao=pedido&id=${pd.id}&status=PG"/>">Confirmar pagamento</a>
                                 </c:if>
                                     <c:if test="${pd.status != 'AB' && pd.status != 'AP'}">
                                         <c:choose>
                                             <c:when test="${pd.status == 'CA'}">
                                                 <p class="cancelado">CANCELADO</p>
                                             </c:when>

                                             <c:when test="${pd.status == 'RC'}">
                                                 <p class="recolhido">RECOLHIDO</p>
                                             </c:when>
                                             <c:when test="${pd.status == 'PG'}">
                                                 <p class="pago">PAGO</p>
                                             </c:when>

                                             <c:when test="${pd.status == 'FI'}">
                                                 <p class="finalizado">FINALIZADO</p>
                                             </c:when>
                                             <c:otherwise>
                                                 <p class="cancelado">REJEITADO</p>
                                             </c:otherwise>
                                         </c:choose>
                                     </c:if>
                             </c:if>
                             <!-- Se for a visão do Funcionário -->
                             <c:if test="${(empty funcionario)?false:true}">

                                 <c:if test="${pd.status == 'AB'}">
                                     <a class="button is-danger is-rounded m-auto mr-1" href="./FuncionarioHome?acao=pedido&id=${pd.id}&status=RJ">Rejeitar Pedido</a>
                                     <a class="recolhido button is-warning is-rounded m-auto ml-1" href="./FuncionarioHome?acao=pedido&id=${pd.id}&status=RC">Recolher Pedido</a>
                                 </c:if>

                                 <c:if test="${pd.status == 'RC'}">
                                     <a class="aguardandoPagamento button is-warning is-rounded m-auto" href="./FuncionarioHome?acao=pedido&id=${pd.id}&status=AP">Requisitar Pagamento</a>
                                 </c:if>
                                     
                                 <c:if test="${pd.status == 'PG'}">
                                     <a class="button is-success is-rounded m-auto" href="./FuncionarioHome?acao=pedido&id=${pd.id}&status=FI">Finalizar Pedido</a>
                                 </c:if>
                                     <c:if test="${pd.status != 'RC' && pd.status != 'PG' && pd.status != 'AB'}">
                                         <c:choose>
                                         <c:when test="${pd.status == 'CA'}">
                                             <p class="cancelado">CANCELADO</p>
                                         </c:when>
                                         <c:when test="${pd.status == 'AP'}">
                                             <p class="aguardandoPagamento">AGUARDANDO PAGAMENTO</p>
                                         </c:when>
                                         <c:when test="${pd.status == 'FI'}">
                                             <p class="finalizado">FINALIZADO</p>
                                         </c:when>
                                         <c:otherwise>
                                             <p class="cancelado">REJEITADO</p>
                                         </c:otherwise>    
                                         </c:choose>
                                     </c:if>
                             </c:if>        

                        </div>
                    </div>
                </form>
            </div>
            
        </div>
    </div>
</body>

</html>