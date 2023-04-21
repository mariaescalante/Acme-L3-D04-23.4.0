<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<jstl:if test="${(_command=='show'||_command=='update'|| _command=='delete')}">
		<acme:input-moment code="administrator.banner.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>	
	</jstl:if>
	<acme:input-moment code="administrator.banner.form.label.start" path="start"/>
	<acme:input-moment code="administrator.banner.form.label.end" path="end"/>
	<acme:input-url code="administrator.banner.form.label.picture" path="picture"/>
	<acme:input-textarea code="administrator.banner.form.label.slogan" path="slogan"/>
	<acme:input-url code="administrator.banner.form.label.url" path="url"/>
	<jstl:choose>
		<jstl:when test="${(_command=='show'||_command=='update'|| _command=='delete')}">
			<acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
			<acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
		</jstl:when>
		<jstl:when test="${_command=='create'}">
			<acme:submit code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>