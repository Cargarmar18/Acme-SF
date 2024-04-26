<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training-module.form.label.code" path="code"/>
	<acme:input-moment code="developer.training-module.form.label.creationMoment" path="creationMoment" readonly="true"/>
	<acme:input-textarea code="developer.training-module.form.label.details" path="details"/>
	<acme:input-select path="difficultyLevel" code="developer.training-module.form.label.difficultyLevel" choices="${difficultyLevels}"/>
	<acme:input-moment code="developer.training-module.form.label.updateMoment" path="updateMoment" readonly="true"/>
	<acme:input-integer code="developer.training-module.form.label.totalTime" path="totalTime"/>
	<acme:input-url code="developer.training-module.form.label.link" path="link"/>
	<acme:input-select path="project" code="developer.training-module.form.label.project" choices="${projects}"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training-module.form.button.create" action="/developer/training-module/create"/>
		</jstl:when>	
	</jstl:choose>	
	
	
	
	
</acme:form>

