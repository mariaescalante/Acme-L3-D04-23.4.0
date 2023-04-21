<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form> 
	<acme:input-textbox code="any.course.form.label.code" path="code"/>	
	<acme:input-textbox code="any.course.form.label.title" path="title"/>
	<acme:input-textarea code="any.course.form.label.abstract$" path="abstract$"/>
	<acme:input-textbox code="any.course.form.label.theoreticalOrHandsOn" path="theoreticalOrHandsOn" readonly="true"/>
	<acme:input-money code="any.course.form.label.price" path="price"/>
	<acme:input-url code="any.course.form.label.link" path="link"/>
</acme:form>
	<acme:button code="any.course.form.button.practicum" action="/any/practicum/list?masterId=${id}"/>
	<acme:button code="any.course.form.button.audit" action="/any/audit/list?masterId=${id}"/>
