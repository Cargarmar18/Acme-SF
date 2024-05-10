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
	<acme:input-textbox code="sponsor.invoices.form.label.code" path="code" placeholder="IN-XXXX-YYYY"/>
	<acme:input-moment code="sponsor.invoices.form.label.registrationTime" path="registrationTime"/>
	<acme:input-moment code="sponsor.invoices.form.label.dueDate" path="dueDate"/>
	<acme:input-money code="sponsor.invoices.form.label.invoiceQuantity" path="invoiceQuantity"/>
	<acme:input-double code="sponsor.invoices.form.label.tax" path="tax"/>			
	<acme:input-url code="sponsor.invoices.form.label.link" path="link"/>
	
	<jstl:choose>	 	
		<jstl:when test="${acme:anyOf(_command, 'show|delete|update') && draftMode == true}">
			<acme:submit code="sponsor.invoices.form.button.publish" action="/sponsor/invoices/publish"/>
			<acme:submit code="sponsor.invoices.form.button.update" action="/sponsor/invoices/update"/>
			<acme:submit code="sponsor.invoices.form.button.delete" action="/sponsor/invoices/delete"/>
			<acme:button code="sponsor.invoices.form.button.user-stories" action="/sponsor/user-story/list?masterId=${id}"/>		
		</jstl:when>	
		<jstl:when test="${acme:anyOf(_command, 'publish') && draftMode == true}">
			<acme:submit code="sponsor.invoices.form.button.publish" action="/sponsor/invoices/publish"/>	
		</jstl:when>
		<jstl:when test="${_command == 'show'}">
			<acme:button code="sponsor.invoices.form.button.user-stories" action="/sponsor/user-story/list?masterId=${id}"/>		
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.invoices.form.button.create" action="/sponsor/invoices/create"/>
		</jstl:when>
	</jstl:choose>	
</acme:form>

