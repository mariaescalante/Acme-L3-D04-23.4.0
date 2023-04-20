<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="assistant.tutorial.list.label.title" path="title"  width="40%"/>
	<acme:list-column code="assistant.tutorial.list.label.goal" path="goal" width="60%" />
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="assistant.tutorial.list.button.create" action="/assistant/tutorial/create"/>
</jstl:if>
