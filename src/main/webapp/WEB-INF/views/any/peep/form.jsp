<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>


	<acme:input-moment code="any.peep.form.label.moment" path="instantiationMoment" readonly= "true"/>
	<acme:input-textbox code="any.peep.form.label.title" path="title"/>
	<acme:input-textbox code="any.peep.form.label.nickname" path="nickname"/>	
	<acme:input-textarea code="any.peep.form.label.message" path="message"/>
	<acme:input-email code="any.peep.form.label.email" path="email"/>
	<acme:input-url code="any.peep.form.label.url" path="url"/>
	
	<acme:submit test="${_command == 'create'}" code="any.peep.button.publish" action="/any/peep/create"/>	
	
</acme:form>