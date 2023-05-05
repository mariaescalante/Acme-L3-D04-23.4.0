<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.course.list.label.code" path="code" width="20%"/>
	<acme:list-column code="any.course.list.label.title" path="title" width="20%"/>
	<acme:list-column code="any.course.list.label.abstract$" path="abstract$" width="40%"/>	
	<acme:list-column code="any.course.list.label.price" path="price" width="20%"/>	
</acme:list>	

