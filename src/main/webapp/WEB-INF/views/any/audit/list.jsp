<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="any.audit.list.label.code" path="code" width="30%"/>
	<acme:list-column code="any.audit.list.label.conclusion" path="conclusion" width="30%"/>
	<acme:list-column code="any.audit.list.label.strongPoints" path="strongPoints" width="40%"/>	
</acme:list>	