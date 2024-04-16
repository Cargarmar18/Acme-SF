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
	<acme:input-textbox code="manager.project.form.label.code" path="code"/>
	<acme:input-textbox code="manager.project.form.label.title" path="title"/>
	<acme:input-textarea code="manager.project.form.label.$abstract" path="$abstract"/>
	<acme:input-textbox code="manager.project.form.label.indication" path="indication"/>
	<acme:input-integer code="manager.project.form.label.cost" path="cost"/>			
	<acme:input-url code="manager.project.form.label.link" path="link" placeholder="example@acme.com"/>
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show'}">
			<acme:button code="manager.project.form.button.user-stories" action="/manager/user-story/list?masterId=${id}"/>		
		</jstl:when>	
	</jstl:choose>	
</acme:form>

