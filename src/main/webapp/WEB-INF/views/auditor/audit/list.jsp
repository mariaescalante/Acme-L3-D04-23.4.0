<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.label.code" path="code" width="20%"/>
	<acme:list-column code="auditor.audit.list.label.conclusion" path="conclusion" width="20%"/>
	<acme:list-column code="auditor.audit.list.label.strongPoints" path="strongPoints" width="40%"/>	
</acme:list>

<acme:button code="auditor.audit.list.button.create" action="/auditor/audit/create"/>