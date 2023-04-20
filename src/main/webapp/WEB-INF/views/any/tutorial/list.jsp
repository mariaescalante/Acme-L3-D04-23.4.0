<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="any.tutorial.list.label.title" path="title"  width="40%"/>
	<acme:list-column code="any.tutorial.list.label.goal" path="goal" width="60%" />
</acme:list>