<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.user-story-project.form.label.project" path="projectCode" readonly = "true"/>			
	<acme:input-select code="manager.user-story-project.form.label.user-story" path="userStory" choices = "${userStories}"/>	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.user-story-project.form.button.create" action="/manager/user-story-project/create?masterId=${project.id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'delete'}">
			<acme:submit code="manager.user-story-project.form.button.delete" action="/manager/user-story-project/delete?masterId=${project.id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>

