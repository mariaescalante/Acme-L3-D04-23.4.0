<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form> 
	<acme:input-textbox code="any.audit.form.label.code" path="code"/>	
	<acme:input-textbox code="any.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textarea code="any.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="any.audit.form.label.weakPoints" path="weakPoints"/>
	<acme:input-textarea code="any.audit.form.label.mark" path="mark"/>
</acme:form>