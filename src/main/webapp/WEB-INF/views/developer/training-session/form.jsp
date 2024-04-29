<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training-session.form.label.code" path="code" readonly="false" placeholder="TS-XXX-YYY"/>
	<acme:input-moment code="developer.training-session.form.label.beginningMoment" path="beginningMoment" readonly="false"/>
	<acme:input-moment code="developer.training-session.form.label.endMoment" path="endMoment" readonly="false"/>
	<acme:input-textbox code="developer.training-session.form.label.location" path="location" readonly="false"/>
	<acme:input-textbox code="developer.training-session.form.label.instructor" path="instructor" readonly="false"/>
	<acme:input-url code="developer.training-session.form.label.contactEmail" path="contactEmail" readonly="false" placeholder="example@acme.com"/>
	<acme:input-url code="developer.training-session.form.label.link" path="link" readonly="false"/>
	<acme:input-select path="trainingModule" code="developer.training-session.form.label.trainingModule" choices="${trainingModules}" readonly="false"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training-session.form.button.create" action="/developer/training-session/create"/>
		</jstl:when>	
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="developer.training-session.form.button.update" action="/developer/training-session/update"/>
			<acme:submit code="developer.training-session.form.button.delete" action="/developer/training-session/delete"/>
			<acme:submit code="developer.training-session.form.button.publish" action="/developer/training-session/publish"/>	
		</jstl:when>
	</jstl:choose>	
	
	
</acme:form>

