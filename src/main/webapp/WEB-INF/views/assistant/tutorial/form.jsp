<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorial.form.label.code" path="code"/>
	<acme:input-textbox code="assistant.tutorial.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.tutorial.form.label.abstract" path="abstract$"/>
	<acme:input-textarea code="assistant.tutorial.form.label.goal" path="goal"/>
	<acme:input-textbox code="assistant.tutorial.form.label.totalTime" path="totalTime" readonly="true"/>
	<acme:input-select code="assistant.tutorial.form.label.course" path="course" choices="${courses}"/>

	<jstl:choose>	 
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'delete'||_command == 'publish') && draftMode == false}">
			<acme:button code="assistant.tutorial.form.button.sessions" action="/assistant/session/list?masterId=${id}"/>
		</jstl:when>	
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'delete'||_command == 'publish') && draftMode == true}">
			<acme:submit code="assistant.tutorial.form.button.update" action="/assistant/tutorial/update"/>
			<acme:submit code="assistant.tutorial.form.button.delete" action="/assistant/tutorial/delete"/>
			<acme:submit code="assistant.tutorial.form.button.publish" action="/assistant/tutorial/publish"/>
			<acme:button code="assistant.tutorial.form.button.sessions" action="/assistant/session/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorial.form.button.create" action="/assistant/tutorial/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>	