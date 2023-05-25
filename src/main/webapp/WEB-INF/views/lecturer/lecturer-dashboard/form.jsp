
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-integer code="lecturer.dashboard.form.label.totalTheoryLectures" path="totalTheoryLectures" readonly = "true"/>
	<acme:input-integer code="lecturer.dashboard.form.label.totalHandsOnLectures" path="totalHandsOnLectures" readonly = "true"/>
	<acme:input-double code="lecturer.dashboard.form.label.averageLectureLearningTime" path="averageLectureLearningTime" readonly = "true"/>
	<acme:input-integer code="lecturer.dashboard.form.label.lectureLearningTimeMinimum" path="lectureLearningTimeMinimum" readonly = "true"/>
	<acme:input-integer code="lecturer.dashboard.form.label.lectureLearningTimeMaximum" path="lectureLearningTimeMaximum" readonly = "true"/>
	<acme:input-double code="lecturer.dashboard.form.label.lectureLearningTimeDeviation" path="lectureLearningTimeDeviation" readonly = "true"/>
	<acme:input-double code="lecturer.dashboard.form.label.averageCourseLearningTime" path="averageCourseLearningTime" readonly = "true"/>
	<acme:input-integer code="lecturer.dashboard.form.label.courseLearningTimeMinimum" path="courseLearningTimeMinimum" readonly = "true"/>
	<acme:input-integer code="lecturer.dashboard.form.label.courseLearningTimeMaximum" path="courseLearningTimeMaximum" readonly = "true"/>
	<acme:input-double code="lecturer.dashboard.form.label.courseLearningTimeDeviation" path="courseLearningTimeDeviation" readonly = "true"/>
</acme:form>



