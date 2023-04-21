<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form> 
	<acme:input-textbox code="any.bulletin.form.label.instantiationMoment" path="instantiationMoment"/>	
	<acme:input-textbox code="any.bulletin.form.label.title" path="title"/>
	<acme:input-textarea code="any.bulletin.form.label.message" path="message"/>
	<acme:input-textarea code="any.bulletin.form.label.flag" path="flag"/>
	<acme:input-textarea code="any.bulletin.form.label.url" path="url"/>
</acme:form>