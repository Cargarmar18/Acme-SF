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
	<acme:input-textbox code="sponsor.invoice.form.label.code" path="code" placeholder="IN-XXXX-YYYY"/>
	<acme:input-moment code="sponsor.invoice.form.label.registrationTime" path="registrationTime"/>
	<acme:input-moment code="sponsor.invoice.form.label.dueDate" path="dueDate"/>
	<acme:input-money code="sponsor.invoice.form.label.invoiceQuantity" path="invoiceQuantity"/>
	<acme:input-double code="sponsor.invoice.form.label.tax" path="tax"/>			
	<acme:input-url code="sponsor.invoice.form.label.link" path="link"/>
	
	<jstl:choose>	 	
		<jstl:when test="${acme:anyOf(_command, 'show|delete|update|publish') && draftMode == true}">
			<acme:submit code="sponsor.invoice.form.button.show" action="/sponsor/invoice/show"/>
			<acme:submit code="sponsor.invoice.form.button.update" action="/sponsor/invoice/update"/>
			<acme:submit code="sponsor.invoice.form.button.delete" action="/sponsor/invoice/delete"/>
			<acme:submit code="sponsor.invoice.form.button.publish" action="/sponsor/invoice/publish"/>		
		</jstl:when>	
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.invoice.form.button.create" action="/sponsor/invoice/create"/>
		</jstl:when>
	</jstl:choose>	
</acme:form>

