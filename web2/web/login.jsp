<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page errorPage="/Erro.jsp"%>

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
                    <form action="<c:url value="/login?acao=login"/>" method="post">
                        <div class="field">
                            <h1 class="title is-10 mb-6">Login</h1>
                            <div class="control mb-5">
                                <label>Email</label>
                                <input class="input" name="login" minlenght="1" type="email" placeholder="XXXXXX@XXXX.XX" />
                            </div>

                            <div class="control mb-5">
                                <label>Senha</label>
                                <input class="input" name="senha" minlenght="1" type="password" placeholder="**********" />
                            </div>
                            <div class="control is-flex mb-4">
                                <button class="button is-success is-rounded m-auto" type="submit">Login</button>
                            </div>
                            <div class="control is-flex mb-4">
                                <p class="has-text-danger"><c:out value="${msg}"/></p>
                            </div>
                            <a href="<c:url value="/home"/>">Voltar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>

</html>