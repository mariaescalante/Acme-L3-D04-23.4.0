
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.lecture.form.label.abstract" path="abstract$"/>
	<acme:input-integer code="lecturer.lecture.form.label.time" path="time"/>
	<acme:input-textarea code="lecturer.lecture.form.label.body" path="body"/>
	<acme:input-url code="lecturer.lecture.form.label.link" path="link"/>
	<acme:input-select code="lecturer.lecture.form.label.theoreticalOrHandsOn" path="theoreticalOrHandsOn" choices="${theoreticalOrHandsOn2}"/>
	
	<jstl:if test="${(_command == 'show'||_command == 'update'||_command == 'delete'||_command == 'publish') && draftMode == true}">
		<acme:submit code="lecturer.lecture.form.button.update" action="/lecturer/lecture/update"/>
		<acme:submit code="lecturer.lecture.form.button.publish" action="/lecturer/lecture/publish"/>
		<acme:submit code="lecturer.lecture.form.button.delete" action="/lecturer/lecture/delete"/>
	</jstl:if>
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="lecturer.lecture.form.button.create" action="/lecturer/lecture/create"/>
	</jstl:if>
</acme:form>



