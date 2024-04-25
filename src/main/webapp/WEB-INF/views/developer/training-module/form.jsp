<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training-module.form.label.code" path="code" readonly="true"/>
	<acme:input-moment code="developer.training-module.form.label.creationMoment" path="creationMoment" readonly="true"/>
	<acme:input-textarea code="developer.training-module.form.label.details" path="details"/>
	<acme:input-select path="difficultyLevel" code="developer.training-module.form.label.difficultyLevel" choices="${difficultyLevel}" readonly="true"/>
	<acme:input-moment code="developer.training-module.form.label.updateMoment" path="updateMoment" readonly="true"/>
	<acme:input-url code="developer.training-module.form.label.link" path="link"/>
			
</acme:form>

