<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.session.list.label.title" path="title" width="30%"/>
	<acme:list-column code="assistant.session.list.label.abstract" path="abstract$" width="40%"/>
	<acme:list-column code="assistant.session.list.label.indication" path="indication" width="30%"/>
</acme:list>

<jstl:if test="${_command == 'list' && showCreate==true}">
	<acme:button code="assistant.session.list.button.create" action="/assistant/session/create?masterId=${masterId}"/>
</jstl:if>