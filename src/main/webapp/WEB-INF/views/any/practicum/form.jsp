
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form> 
	<acme:input-textbox code="any.practicum.form.label.code" path="code"/>	
	<acme:input-textbox code="any.practicum.form.label.title" path="title"/>
	<acme:input-textarea code="any.practicum.form.label.abstract$" path="abstract$"/>
	<acme:input-textarea code="any.practicum.form.label.goal" path="goals"/>
	<acme:input-double code="any.practicum.form.label.estimatedTotalTime" path="estimatedTotalTime"/>
	<acme:input-textbox code="any.practicum.form.label.company" path="company"/>
</acme:form>