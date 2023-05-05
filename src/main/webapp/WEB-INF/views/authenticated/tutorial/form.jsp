<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.tutorial.form.label.code" path="code"/>
	<acme:input-textbox code="any.tutorial.form.label.title" path="title"/>
	<acme:input-textarea code="any.tutorial.form.label.abstract" path="abstract$"/>
	<acme:input-textarea code="any.tutorial.form.label.goal" path="goal"/>
	<acme:input-textbox code="any.tutorial.form.label.totalTime" path="totalTime" readonly="true"/>
	<acme:input-select code="any.tutorial.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="any.tutorial.form.label.assistantSupervisor" path="assistantSupervisor"/>
		<acme:input-textbox code="any.tutorial.form.label.assistantResume" path="assistantResume"/>
	
</acme:form>	