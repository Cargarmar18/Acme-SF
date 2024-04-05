<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.consumer.form.label.company" path="company"/>
	<acme:input-textbox code="authenticated.consumer.form.label.sector" path="sector"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.consumer.form.button.create" action="/authenticated/consumer/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.consumer.form.button.update" action="/authenticated/consumer/update"/>
</acme:form>
