
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-select code="lecturer.membership.form.label.course" path="course" choices="${courses}"/>
	<acme:input-select code="lecturer.membership.form.label.lecture" path="lecture" choices="${lectures}"/>
	
	<jstl:if test="${_command == 'show'||_command == 'delete'}">
		<acme:submit code="lecturer.membership.form.button.delete" action="/lecturer/membership/delete"/>
	</jstl:if>
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="lecturer.membership.form.button.create" action="/lecturer/membership/create"/>
	</jstl:if>
</acme:form>



