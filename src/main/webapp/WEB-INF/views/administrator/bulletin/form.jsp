<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${_command=='show'}">
		<acme:input-moment code="any.bulletin.form.label.instantiationMoment" path="instantiationMoment"/>	
	</jstl:if>	
	<acme:input-textbox code="any.bulletin.form.label.title" path="title"/>
	<acme:input-textarea code="any.bulletin.form.label.message" path="message"/>
	<acme:input-checkbox code="any.bulletin.form.label.flag" path="flag"/>
	<acme:input-url code="any.bulletin.form.label.url" path="url"/>
	<acme:submit code="administrator.bulletin.form.button.create" action="/administrator/bulletin/create"/>
</acme:form>