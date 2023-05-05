<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.auditing-records.form.label.subject" path="subject"/>
	<acme:input-textbox code="auditor.auditing-records.form.label.assessment" path="assessment"/>
	<acme:input-moment code="auditor.auditing-records.form.label.startDate" path="startDate"/>
	<acme:input-moment code="auditor.auditing-records.form.label.endDate" path="endDate"/>
	<acme:input-double code="auditor.auditing-records.form.label.period" path="period" readonly="true"/>
	<acme:input-textbox code="auditor.auditing-records.form.label.mark" path="mark"/>
	<acme:input-url code="auditor.auditing-records.form.label.link" path="link"/>

	<jstl:choose>
		<jstl:when test="${(_command=='show'||_command=='update'|| _command=='delete') && draftMode == true}">
			<acme:submit code="auditor.auditing-records.form.button.update" action="/auditor/auditing-records/update"/>
			<acme:submit code="auditor.auditing-records.form.button.delete" action="/auditor/auditing-records/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && draftMode == true}">
			<acme:submit code="auditor.auditing-records.form.button.create" action="/auditor/auditing-records/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-correction' && draftMode == false}">
			<acme:input-checkbox code="auditor.auditing-records.form.label.confirmation" path="confirmation"/>
			<acme:submit code="auditor.auditing-records.form.button.create-correction" action="/auditor/auditing-records/create-correction?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>