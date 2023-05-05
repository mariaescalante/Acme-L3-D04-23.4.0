
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.membership.list.label.course" path="course" width="50%"/>
	<acme:list-column code="lecturer.membership.list.label.lecture" path="lecture" width="50%"/>		
</acme:list>

<acme:button code="lecturer.membership.list.button.create" action="/lecturer/membership/create"/>