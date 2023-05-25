<%--
- menu.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.S2" action="https://youtube.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.S5" action="https://www.realbetisbalompie.es/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.S3" action="http://www.lichess.org/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.S4" action="http://games.crossfit.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.S1" action="http://www.wuxiaworld.com/"/>
            <acme:menu-suboption code="master.menu.any.list-peep" action="/any/peep/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.bulletin-create" action="/administrator/bulletin/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.banner" action="/administrator/banner/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list.offer" action="/administrator/offer/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
            <acme:menu-suboption code="master.menu.authenticated.money-exchange" action="/authenticated/money-exchange/perform"/>
            <acme:menu-separator/>
        	<acme:menu-suboption code="master.menu.authenticated.list.offer" action="/authenticated/offer/list" access="isAuthenticated()"/>
            <acme:menu-separator/>
            <acme:menu-suboption code="master.menu.authenticated.list-bulletin" action="/authenticated/bulletin/list" access="isAuthenticated()"/>
            <acme:menu-separator/>
            <acme:menu-suboption code="master.menu.authenticated.list-tutorial" action="/authenticated/tutorial/list" access="isAuthenticated()"/>
        </acme:menu-option>
        
        <acme:menu-option code="master.menu.any">
            <acme:menu-suboption code="master.menu.any.list-course" action="/any/course/list"/>
            <acme:menu-separator/>
            <acme:menu-suboption code="master.menu.any.list-peep" action="/any/peep/list"/>
        </acme:menu-option>
        
		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.lecturer" access="hasRole('Lecturer')">
				
			<acme:menu-suboption code="master.menu.lecturer.course.list-all" action="/lecturer/course/list-all"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.lecturer.course.list-mine" action="/lecturer/course/list-mine"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.lecturer.lecture.list-mine" action="/lecturer/lecture/list-mine"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.lecturer.membership.list-mine" action="/lecturer/membership/list-mine"/>
		</acme:menu-option>
	
		<acme:menu-option code="master.menu.company" access="hasRole('Company')">
			<acme:menu-suboption code="master.menu.company.practicum.list" action="/company/practicum/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.audit.list" action="/auditor/audit/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.student" access="hasRole('Student')">
			<acme:menu-suboption code="master.menu.student.enrolment.list" action="/student/enrolment/list"/>
			<acme:menu-suboption code="master.menu.student.course.list" action="/student/course/list"/>
			<acme:menu-separator/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.assistant" access="hasRole('Assistant')">
			<acme:menu-suboption code="master.menu.assistant.tutorial.list" action="/assistant/tutorial/list"/>
			<acme:menu-separator/>		
		</acme:menu-option>
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>
			
		
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-lecturer" action="/authenticated/lecturer/create" access="!hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.user-account.lecturer" action="/authenticated/lecturer/update" access="hasRole('Lecturer')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-company" action="/authenticated/company/create" access="!hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.company" action="/authenticated/company/update" access="hasRole('Company')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-student" action="/authenticated/student/create" access="!hasRole('Student')"/>
			<acme:menu-suboption code="master.menu.user-account.student" action="/authenticated/student/update" access="hasRole('Student')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-assistant" action="/authenticated/assistant/create" access="!hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.assistant" action="/authenticated/assistant/update" access="hasRole('Assistant')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

