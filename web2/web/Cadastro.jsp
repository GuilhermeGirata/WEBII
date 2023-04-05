<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page errorPage="/Erro.jsp"%>
<!DOCTYPE html>
<html lang="pt-br">

<head>
      <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" />
    <script src="./main.js" ></script>
    <title>Lavenderia LOL</title>
</head>
<body>
    <div class="columns is-centered">
        <div class="column m-auto is-two-thirds">
            <div class="box is-centered">
                <form 
                <c:if test="${(empty msg)? true : false}">action='<c:url value="/Cadastrar?acao=cadastrar"/>'</c:if>
                <c:if test="${(empty msg)? false : true}">action='<c:url value="/Cadastrar?acao=cadastrar&atualizar=true"/>'</c:if>
                    method="post">
                    <div class="field">
                        <h1 class="title is-10 is-center mb-3">
                        <c:if test="${(empty msg)? true : false}">Cadastro</c:if>
                        <c:if test="${(empty msg)? false : true}">Atualizar</c:if>
                        
                        <div class="control mb-4 mt-3">
                            <label>Nome</label>
                            <input class="input" name="nome" type="charset" placeholder="XXXXXX" maxlength="255" minlength="5"/>
                            <p class="has-text-danger"><c:out value="${nomemsg}"/></p>
                        </div>
                        

                            <c:if test="${(empty msg)?true:false}">
                                <div class="control mb-4">
                                    <label>CPF</label>
                                    <input class="input" name="cpf" type="charset" placeholder="XXX.XXX.XXX-XX" pattern="([0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2})" onkeypress="mascara(this, '###.###.###-##')" maxlength="14" minlength="14"/>
                                    <p class="has-text-danger"><c:out value="${cpfmsg}"/></p>
                                </div>
                                <div class="control mb-4">
                                    <label>Email</label>
                                    <input class="input" name="email" type="email" placeholder="XXXXX@XX.XX" maxlength="50" minlength="3"/>
                                    <p class="has-text-danger"><c:out value="${emailmsg}"/></p>
                                </div>

                            </c:if>

                        
                        
                        <div class="control mb-4">
                            <label>Telefone</label>
                            <input class="input" name="telefone" type="charset" placeholder="XX XXXXX-XXXX" pattern="([0-9]{2}[ ][0-9]{5}[-][0-9]{4})" onkeypress="mascara(this, '## #####-####')" maxlength="13" minlength="13"/>
                            <p class="has-text-danger"><c:out value="${telmsg}" /></p>
                        </div>
                        <div class="columns">
                            <div class="column is-12 is-offset-0">
                                <div class="columns">
                                    <div class="column">
                                        <div class="control">
                                            <label>CEP</label>
                                            <input name="cep" class="input" type="charset" placeholder="XXXXX-XXX" onkeypress="mascara(this, '#####-###')" maxlength="9" minlength="9">
                                            <p class="has-text-danger"><c:out value="${cepmsg}"/></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="columns">
                            <div class="column is-8 is-offset-0">
                                <div class="control">
                                    <label>Complemento</label>
                                    <input name="complemento" class="input" type="charset" placeholder="Complemento" />
                                    <p class="has-text-danger"><c:out value="${compmsg}"/></p>
                                </div>
                            </div>
                            <div class="column">
                                <div class="control">
                                    <label>Número</label>
                                    <input name="numero" class="input" type="number" placeholder="000" minlength="1" maxlength="7">
                                    <p class="has-text-danger"><c:out value="${nummsg}"/></p>
                                </div>
                            </div>
                        </div>
                        <div class="control is-flex mb-4">
                            <c:if test="${(empty msg)? true : false}">
                                <button class='button is-danger is-rounded m-auto' type='submit'>Cadastrar</button>
                            </c:if>
                            <c:if test="${(empty msg)? false : true}">
                                <button class='button is-warning is-rounded m-auto' type='submit'>Atualizar</button>
                            </c:if>
                        </div>
                            <c:if test="${(empty msg)?true:false}">
                                <a href="<c:url value="/" />">Voltar</a>  
                            </c:if>
                            <c:if test="${(empty msg)?false:true}">
                                <a href="<c:url value="/ClienteHome"/>">Voltar</a>   
                            </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </div>
</body>
</html>

