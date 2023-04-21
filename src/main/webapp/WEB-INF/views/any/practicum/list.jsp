<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="any.practicum.list.label.code" path="code" width="30%"/>
	<acme:list-column code="any.practicum.list.label.title" path="title" width="30%"/>
	<acme:list-column code="any.practicum.list.label.abstract$" path="abstract$" width="40%"/>	
</acme:list>	