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
        <script src="./main.js" type="text/javascript"></script>
        <title>Lavenderia LOL</title>
    </head>

    <body>
        <h:navbar funcionario="."/>
        <div class="columns">
            <div class="column">
                <div class="box">
                    <p class="title is-5">${(!empty up)?"Cadastrar":"Editar"} - Peças de Roupa</p>
                </div>
                <div class="box mb-5 pb-6">
                    <form id="form1" 
                          <c:if test="${(empty up)? true : false}">action='<c:url value="/Roupa?acao=inserirBD"/>'</c:if>
                          <c:if test="${(empty up)? false : true}">action='<c:url value="/Roupa?acao=updateBD"/>'</c:if>      
                              method="post">
                              <div class="field">
                                  <div class="columns">
                                      <div class="column is-11 is-offset-0">
                                          <h1 class="title is-5">Peça de Roupa</h1>
                                      </div>
                                  <c:if test="${(!empty up)?true:false}">                                
                                      <a class="button is-danger is-rounded" href="<c:url value="/Roupa?acao=excluir&id=${rp.id}"/>">Excluir</a>
                                  </c:if>
                              </div>
                              <div class="columns">

                                  <div class="column">
                                      <label>Nome da Peça</label>
                                      <input type="text" name="id" value="<c:out value="${rp.id}"/>" style="display:none">
                                      <input class="input" name="nome" minlength="3" maxlength="255" type="charset" value="<c:out value="${rp.nome}"/>"/>
                                      <p class="has-text-danger"><c:out value="${nomeMsg}"/></p>
                                  </div>
                              </div>
                              <div class="columns">
                                  <div class="column is-8 is-offset-0">
                                      <label>Preço</label>
                                      <input class="input" step="0.01" name="preco" minlength="3" maxlength="255" type="number" placeholder="R$XX.XX" value="<c:out value="${rp.preco}"/>" />
                                      <p class="has-text-danger"><c:out value="${precoMsg}"/></p>
                                  </div>
                                  <div class="is-flex">
                                      <button class="button is-danger is-inverted mt-auto mb-3" type="button" onclick="manPeca('-')">-</button>
                                      <div class="m-auto">
                                          <label>Tempo de Lavagem (Em dias)</label>
                                          <input name="qtde" class="input" max="30" type="number" min="1" placeholder="XX" value="${(!empty up)? rp.limiteDias : "1"}" id="manPecaId" />
                                          <p class="has-text-danger"><c:out value="${qtdeMsg}"/></p>
                                      </div>
                                      <button class="button is-danger is-inverted mt-auto mb-3" type="button" onclick="manPeca('+')">+</button>
                                  </div>
                              </div>
                              <a href="<c:url value="/FuncionarioHome"/>">Voltar</a>
                          </div>
                    </form>
                </div>
                <div class="is-flex">
                    <button class="button is-success is-rounded pl-6 pr-6 m-auto" form="form1" type="submit">Salvar</button>
                </div>
            </div>
        </div>
    </body>

</html>