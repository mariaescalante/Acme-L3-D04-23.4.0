<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.peep.list.moment" path="instantiationMoment" width="20%"/>
	<acme:list-column code="any.peep.list.title" path="title" width="60%"/>
	<acme:list-column code="any.peep.list.nickname" path="nickname" width="20%"/>
</acme:list>

<acme:button code="any.peep.button.create" action="/any/peep/create"/>	