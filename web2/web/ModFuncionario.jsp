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
    <script src="./main.js"></script>
    <title>Lavenderia LOL</title>
</head>

<body>
    <h:navbar funcionario="."/>
    <div class="columns">
        <div class="column">
            <div class="box">
                <p class="title is-5">${(!empty up)?"Cadastrar":"Editar"} - Funcionário</p>
            </div>
            <div class="box pb-6">
                <form id="form1" 
                       <c:if test="${(empty up)? true : false}">action='<c:url value="/Funcionario?acao=inserirBD"/>'</c:if>
                        <c:if test="${(empty up)? false : true}">action='<c:url value="/Funcionario?acao=updateBD"/>'</c:if>
                method="post">
                    <div class="field">
                        <div class="columns">
                            <div class="column is-11 is-offset-0">
                                <h1 class="title is-5">Funcionário</h1>
                            </div>
                        <c:if test="${(!empty up)?true:false}">                                
                                <a class="button is-danger is-rounded" href="<c:url value="/Funcionario?acao=excluir&id=${fc.id}"/>">Excluir</a>
                            </c:if>
                        </div>
                        <div class="columns">
                            <input type="text" name="id" value="<c:out value="${fc.id}"/>" style="display: none">
                            <div class="column">
                                <label>Nome</label>
                                <input class="input" type="charset" name="nome" minlength="3" maxlength="255" value="<c:out value="${fc.nome}"/>" />
                                <p class="has-text-danger"><c:out value="${nomeMsg}"/></p>
                            </div>
                        </div>
                        <div class="columns">
                            <div class="column is-6 is-offset-0">
                                <label>Senha</label>
                                <input class="input" type="charset" name="senha" minlength="4" maxlength="255" value="<c:out value="${fc.senha}"/>"/>
                                <p class="has-text-danger"><c:out value="${senhaMsg}" /></p>
                            </div>
                            <div class="column">
                                <label>Email</label>
                                <div class="control">
                                    <input class="input" type="email" name="email" minlength="3" maxlength="255" value="<c:out value="${fc.email}"/>"/>
                                    <p class="has-text-danger"><c:out value="${emailMsg}"/></p>
                                </div>
                            </div>
                        </div>
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