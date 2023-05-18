<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="auditor.audit.form.label.code" path="code"/>	
	<acme:input-textbox code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="auditor.audit.form.label.weakPoints" path="weakPoints"/>
	<jstl:if test="${_command == 'show'}">
		<acme:input-double code="auditor.audit.form.label.mark" path="mark" readonly="true"/>
	</jstl:if>
	<acme:input-select code="auditor.audit.form.label.course" path="course" choices="${courses}"/>



	<jstl:choose>	
		<jstl:when test="${_command == 'show' && draftMode == false}">

		<acme:button code="auditor.audit.form.button.auditing-records.list" action="/auditor/auditing-records/list?id=${id}"/>
		</jstl:when>	 
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'delete'||_command == 'publish') && draftMode == true}">
			<acme:submit code="auditor.audit.form.button.update" action="/auditor/audit/update"/>
			<acme:submit code="auditor.audit.form.button.delete" action="/auditor/audit/delete"/>
			<acme:submit code="auditor.audit.form.button.publish" action="/auditor/audit/publish"/>
			<acme:button code="auditor.audit.form.button.auditing-records.list" action="/auditor/auditing-records/list?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit.form.button.create" action="/auditor/audit/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>