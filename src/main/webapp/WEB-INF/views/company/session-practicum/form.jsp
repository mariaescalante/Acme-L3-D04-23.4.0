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
	<acme:input-textbox code="company.session-practicum.form.label.title" path="title"/>
	<acme:input-textbox code="company.session-practicum.form.label.abstract-text" path="abstractText"/>
	<acme:input-moment code="company.session-practicum.form.label.start-date" path="startDate"/>
	<acme:input-moment code="company.session-practicum.form.label.end-date" path="endDate"/>
	<acme:input-url code="company.session-practicum.form.label.further-information-link" path="furtherInformationLink"/>
	
	<jstl:choose>
		<jstl:when test="${(_command=='show'||_command=='update'|| _command=='delete') && draftMode == true}">
			<acme:submit code="company.session-practicum.form.button.update" action="/company/session-practicum/update"/>
			<acme:submit code="company.session-practicum.form.button.delete" action="/company/session-practicum/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && draftMode == true}">
			<acme:submit code="company.session-practicum.form.button.create" action="/company/session-practicum/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-correction' && draftMode == false}">
			<acme:input-checkbox code="company.session-practicum.form.label.confirmation" path="confirmation"/>
			<acme:submit code="company.session-practicum.form.button.create-exceptional" action="/company/session-practicum/create-correction?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>