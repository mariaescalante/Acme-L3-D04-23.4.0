<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form> 
	<acme:input-textbox code="company.practicum.form.label.code" path="code"/>	
	<acme:input-textbox code="company.practicum.form.label.title" path="title"/>
	<acme:input-textarea code="company.practicum.form.label.abstract$" path="abstract$"/>
	<acme:input-textarea code="company.practicum.form.label.goals" path="goals"/>
	<acme:input-double code="company.practicum.form.label.estimatedTotalTime" path="estimatedTotalTime"/>
	<acme:input-select code="company.practicum.form.label.course" path="course" choices="${courses}"/>
	


	<jstl:choose>	
		<jstl:when test="${_command == 'show' && draftMode == false}">
		
		<acme:button code="company.practicum.form.button.session-practicum.list" action="/company/session-practicum/list?id=${id}"/>
		</jstl:when>
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'delete'||_command == 'publish') && draftMode == true}">
			<acme:submit code="company.practicum.form.button.update" action="/company/practicum/update"/>
			<acme:submit code="company.practicum.form.button.delete" action="/company/practicum/delete"/>
			<acme:submit code="company.practicum.form.button.publish" action="/company/practicum/publish"/>
			<acme:button code="company.practicum.form.button.session-practicum.list" action="/company/session-practicum/list?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicum.form.button.create" action="/company/practicum/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>