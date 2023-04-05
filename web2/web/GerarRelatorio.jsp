
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <script src="./bulma-calendar.min.js" type="text/javascript"></script>
        <link href="./bulma-calendar.min.css" rel="stylesheet" type="text/css"/>
        <title>Lavenderia LOL</title>
    </head>

    <body>

        <h:navbar funcionario="."/>

        <div class="columns mt-4">
            <div class="column">
                <form action="<c:url value="/Relatorio"/>">    

                    <div class="columns is-mobile">
                        <div class="column is-6 is-offset-0">
                            <div class="card box p-1">
                                <div class="card-content is-flex">
                                    <div>
                                        <p class="title">
                                            Clientes
                                        </p>
                                        <p>Todos os Clientes</p>
                                    </div>
                                    <div class="mt-auto mb-auto ml-auto mr-0">
                                        <button class="button is-rounded is-success" type="submit" name="acao" value="clienteAll">Gerar</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="column is-6 is-offset-0">
                            <div class="card box p-1">
                                <div class="card-content is-flex">
                                    <div>
                                        <p class="title">
                                            Clientes Fiéis
                                        </p>
                                        <p>Os 5 Clientes Mais Fiéis</p>
                                    </div>
                                    <div class="mt-auto mb-auto ml-auto mr-0">
                                        <button class="button is-rounded is-success"  type="submit" name="acao" value="clienteFiel">Gerar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="is-flex mb-5 is-mobile">
                        <div class="m-auto is-flex">
                            <div class="mr-4">
                                <label class="label">Data Inicial</label>
                                <input class="input is-medium" type="date" name="dataInicio" placeholder="XX/XX/XXXX" />
                            </div>
                            <div class="ml-4">
                                <label class="label">Data Final</label>
                                <input class="input is-medium" name="dataFim" type="date" placeholder="XX/XX/XXXX" />
                            </div>
                        </div>
                    </div>

                    <div class="is-flex mb-2">
                        <p class="has-text-danger m-auto">${erro}</p>
                    </div>



                    <div class="is-flex mb-6">
                        <div class="m-auto">
                            <button class="button is-danger is-light"  type="submit" name="acao" value="todos">Solicitar todos os Relatórios</button>
                        </div>
                    </div>


                    <div>
                        <div class="columns mb-5 box p-2">
                            <div class="ml-6 column">
                                <p><strong>Tipo</strong></p>Receita
                            </div>
                            <div class="ml-2 column"><button class="button is-link is-light" type="submit" name="acao" value="receita">Solicitar PDF</button></div>
                        </div>

                        <div class="columns mb-5 box p-2">
                            <div class="ml-6 column">
                                <p><strong>Tipo</strong></p>Pedidos
                            </div>
                            <div class="ml-2 column"><button class="button is-link is-light" type="submit" name="acao" value="pedido">Solicitar PDF</button></div>
                        </div>
                    </div>
                </form> 
            </div>   
        </div>
    </body>
<script src="./calendarConfig.js"></script>
</html>