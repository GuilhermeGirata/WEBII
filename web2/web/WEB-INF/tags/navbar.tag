
<%@tag description="navbar ou topbar do site" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="funcionario"%>

<%-- any content can be specified here e.g.: --%>
<nav class="navbar" role="navigation" aria-label="main navigation" style="height:84px">
    <div class="navbar-brand">
        
        <img src="./logo.png" width="200" height="100"/>
        

        <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
        </a>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
        <div class="navbar-start">
            <a class="navbar-item" 
               <c:if test="${(empty funcionario)?true:false}">href="<c:url value="/ClienteHome"/>"</c:if>
               <c:if test="${(!empty funcionario)?true:false}">href="<c:url value="/FuncionarioHome"/>"</c:if>
            >
                Pagina Inicial
            </a>
            
            <c:if test="${(empty funcionario)?true:false}">
                <a class="navbar-item" href="<c:url value="/ClienteHome?acao=carregaRoupas"/>">
                    Fazer Pedido
                </a>
            </c:if>
            
            <c:if test="${(!empty funcionario)?true:false}">
                <a class="navbar-item" href="<c:url value="/Roupa" />">Lista de Peças</a>
                <a class="navbar-item" href="<c:url value="/Funcionario" />" >Lista de Funcionários</a>
                <div class="navbar-item has-dropdown is-hoverable">
                    <a class="navbar-link">
                        Mais
                    </a>
                    <div class="navbar-dropdown">
                        <a class="navbar-item" href="<c:url value="/Roupa?acao=inserir"/>">
                            Inserir Nova Roupa
                        </a>
                        <a class="navbar-item" href="<c:url value="/Funcionario?acao=inserir"/>">
                            Inserir Novo Funcionário
                        </a>
                        <a class="navbar-item" href="<c:url value="/Relatorio"/>">
                            Gerar Relatórios
                        </a>
                    </div>
                </div>
            </c:if>
        </div>

        <div class="navbar-end">
            <div class="navbar-item">
                <div class="buttons">
                    <a class="button is-danger" href="<c:url value="/Logout"/>">
                        <strong>Logout</strong>
                    </a>
                </div>
            </div>
        </div>
    </div>
</nav>

