<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="developer.training-module.form.label.creationMoment" path="creationMoment" readonly="true"/>
	<acme:input-textbox code="developer.training-module.form.label.code" path="code" readonly="false" placeholder="XXX-YYY"/>
	<acme:input-textarea code="developer.training-module.form.label.details" path="details" readonly="false"/>
	<acme:input-select path="difficultyLevel" code="developer.training-module.form.label.difficultyLevel" choices="${difficultyLevels}" readonly="false"/>
	<acme:input-integer code="developer.training-module.form.label.totalTime" path="totalTime" readonly="false"/>
	<acme:input-url code="developer.training-module.form.label.link" path="link" readonly="false"/>
	<acme:input-select path="project" code="developer.training-module.form.label.project" choices="${projects}" readonly="false"/>
	<acme:input-moment code="developer.training-module.form.label.updateMoment" path="updateMoment" readonly="true"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training-module.form.button.create" action="/developer/training-module/create"/>
		</jstl:when>	
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="developer.training-module.form.button.update" action="/developer/training-module/update"/>
			<acme:submit code="developer.training-module.form.button.delete" action="/developer/training-module/delete"/>
			<acme:submit code="developer.training-module.form.button.publish" action="/developer/training-module/publish"/>
			<acme:button code="developer.training-module.form.button.training-sessions" action="/developer/training-session/list?masterId=${id}"/>		
		</jstl:when>
		<jstl:when test="${_command == 'show'}">
			<acme:button code="developer.training-module.form.button.training-sessions" action="/developer/training-session/list?masterId=${id}"/>		
		</jstl:when>
	</jstl:choose>	
	
	
</acme:form>

