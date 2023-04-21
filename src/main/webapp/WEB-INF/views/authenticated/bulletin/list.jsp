<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="any.bulletin.list.label.instantiationMoment" path="instantiationMoment" width="30%"/>
	<acme:list-column code="any.bulletin.list.label.title" path="title" width="30%"/>
	<acme:list-column code="any.bulletin.list.label.message" path="message" width="40%"/>	
</acme:list>	