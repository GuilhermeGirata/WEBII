
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page isErrorPage="true"%>
    
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
        <div class="is-flex hero is-fullheight">
            <div class="column is-one-third m-auto">
                <div class="box is-centered">
                    <form>
                        <div class="field">
                            <div class="m-auto has-text-centered">
                                <i class="fa-solid fa-circle-xmark is-size-2 mb-2 has-text-danger"></i>                          
                                <h1 class="title is-10 has-text-weight-bold mt-2">UM ERRO OCORREU :'(</h1>   
                                <p class="has-text-danger has-text-weight-bold is-size-6">HOUVE UM ERRO: <c:out value="${msg}"/></p>
                                <br><!-- comment -->
                                <p class="has-text-danger has-text-weight-bold is-size-6">DETALHES 
                                    <c:if test="${!empty erro}">
                                        <c:out value="${erro}"/>
                                    </c:if>
                                    <c:if test="${!empty exception.message}">
                                        <c:out value="${exception.message}" />
                                    </c:if>
                                </p>
                            </div>
                            <div class="mt-5 is-flex">
                                <c:if test="${!empty page}">
                                    <a class="button is-success is-rounded ml-auto" href="<c:url value="${page}"/>">Voltar</a>
                                </c:if>
                                <c:if test="${empty page}">
                                    <a class="button is-success is-rounded ml-auto" href="<c:url value="/"/>">Voltar</a>  
                                </c:if>
                            </div>              
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>

</html>
