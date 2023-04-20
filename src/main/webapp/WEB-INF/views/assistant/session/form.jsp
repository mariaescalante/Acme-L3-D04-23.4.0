<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="assistant.session.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.session.form.label.abstract" path="abstract$"/>
	<acme:input-select code="assistant.session.form.label.indication" path="indication" choices="${indications}"/>
	<acme:input-moment code="assistant.session.form.label.startTime" path="startTime"/>
	<acme:input-moment code="assistant.session.form.label.endTime" path="endTime"/>
	<acme:input-url code="assistant.session.form.label.link" path="link"/>

	<jstl:choose>
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'delete'||_command == 'publish') && draftMode==true}">
			<acme:submit code="assistant.session.form.button.update" action="/assistant/session/update"/>
			<acme:submit code="assistant.session.form.button.delete" action="/assistant/session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.session.form.button.create" action="/assistant/session/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>	