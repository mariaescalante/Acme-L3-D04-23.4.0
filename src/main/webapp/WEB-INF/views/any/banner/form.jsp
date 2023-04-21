<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-moment code="administrator.banner.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>	
	<acme:input-moment code="administrator.banner.form.label.start" path="start"/>
	<acme:input-moment code="administrator.banner.form.label.end" path="end"/>
	<acme:input-url code="administrator.banner.form.label.picture" path="picture"/>
	<acme:input-textarea code="administrator.banner.form.label.slogan" path="slogan"/>
	<acme:input-url code="administrator.banner.form.label.url" path="url"/>
</acme:form>