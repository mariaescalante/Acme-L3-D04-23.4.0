<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditing-records.list.label.correction" path="correction" width="20%"/>
	<acme:list-column code="auditor.auditing-records.list.label.subject" path="subject" width="50%"/>	
	<acme:list-column code="auditor.auditing-records.list.label.assessment" path="assessment" width="30%"/>
</acme:list>

<acme:button test="${showCreate && exceptional}" code="auditor.auditing-records.list.button.create" action="/auditor/auditing-records/create?masterId=${id}"/>
<acme:button test="${showCreate && !exceptional}" code="auditor.auditing-records.list.button.create-exceptional" action="/auditor/auditing-records/create-correction?masterId=${id}"/>