

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt'  prefix='fmt' %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="pd" required="true" type="com.web2.model.beans.Pedido"%>
<%@attribute name="funcionario"%>

<!-- Começo dropdown -->
<div class="has-background-light m-2 dropdown-lol is-mobile mb-5" id="drop1" onclick="openDrop(this)">
    <!-- Cabecalho-->
    <div class="columns is-mobile">
        <div class="column ml-4 is-narrow">
            <i class="fa-sharp fa-solid fa-chevron-up"></i>
        </div>
        <div class="column is-4">
            <p class="has-text-weight-bold">Pedido #<c:out value="${pd.id}"/></p>
        </div>
        <div class="column is-4 is-offset-3 is-flex">
            <p class="is-Success">Status: </p>
            <c:choose>
                <c:when test="${pd.status == 'AB'}">
                    <p class=" emAberto">EM ABERTO</p>
                </c:when>
                    
                <c:when test="${pd.status == 'CA'}">
                    <p class="cancelado">CANCELADO</p>
                </c:when>
                    
                <c:when test="${pd.status == 'RC'}">
                    <p class="recolhido">RECOLHIDO</p>
                </c:when>
                    
                <c:when test="${pd.status == 'AP'}">
                    <p class="aguardandoPagamento">AGUARDANDO PAGAMENTO</p>
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
                    
            
        </div>
    </div>
    <!-- Fim Cabecalho -->
    <div class="dropdown-lol-oculto">
        <div class="content m-3">
            <ol class="p-3">
                <c:forEach var="item2" items="${pd.roupas}">
                    <li class="mb-3">PEÇA: <c:out value="${item2.nome}" /> - QTDE <c:out value="${item2.qtde}"/></li>
                </c:forEach>
            </ol>
        </div>
        <div class="columns">
            <div class="column is-5 ml-5 is-flex">
                <p class="has-text-weight-bold">Previsão de Termino:
                    <p class="ml-2">
                        <fmt:parseDate value="${pd.dataPrazo}" pattern="yyyy-MM-dd" var="date2" type="date"/>
                        <fmt:formatDate pattern="dd/MM/yyyy" value="${date2}"/>
                    </p>
                </p>
                
                <p class="has-text-weight-bold ml-4">Data de Abertura:
                    <p class="ml-2">
                        <fmt:parseDate value="${pd.dataAbertura}" pattern="yyyy-MM-dd" var="date3" type="date"/>
                        <fmt:formatDate pattern="dd/MM/yyyy" value="${date3}"/>
                    </p>
                </p>
                
            </div>
            <div class="column is-5 is-offset-2 is-flex">
                <c:if test="${(empty funcionario)?true:false}">           
                    <a class="button is-warning is-rounded is-centered ml-4" href="<c:url value="/ClienteHome?acao=consulta&id=${pd.id}" />">Detalhes</a>
                </c:if>
                <c:if test="${(empty funcionario)?false:true}">    
                    <c:if test="${pd.getStatus() == 'AB'}">
                        <a class="button is-light is-rounded is-centered ml-2" href="<c:url value="/FuncionarioHome?acao=pedido&id=${pd.id}&status=RC&pg=main"/>">Recolher</a>
                    </c:if>
                    <c:if test="${pd.getStatus() == 'RC'}">
                        <a class="button is-light is-rounded is-centered ml-2" href="<c:url value="/FuncionarioHome?acao=pedido&id=${pd.id}&status=AP&pg=main"/>">Confirmar Lavagem</a>
                    </c:if>
                    <c:if test="${pd.getStatus() == 'PG'}">
                        <a class="button is-light is-rounded is-centered ml-2" href="<c:url value="/FuncionarioHome?acao=pedido&id=${pd.id}&status=FI&pg=main"/>">Finalizar Pedido</a>
                    </c:if>
                    <a class="button is-warning is-rounded is-centered ml-4" href="<c:url value="/FuncionarioHome?acao=consulta&id=${pd.id}"/>">Detalhes</a>
                </c:if>
            </div>
        </div>
    </div>
</div>
<!-- fim dropdown -->