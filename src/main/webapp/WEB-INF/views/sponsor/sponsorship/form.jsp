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
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code" placeholder="XXX-YYY"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.moment" path="moment"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.startSponsor" path="startSponnsor"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.endSponsor" path="endSponsor"/>
	<acme:input-money code="sponsor.sponsorship.form.label.amount" path="amount"/>
	<acme:input-select  code="sponsor.sponsorship.form.label.sponsorshipType" path="sponsorshipType" choices="${sponsorshipTypes}"/>
	<acme:input-select code="sponsor.sponsorship.form.label.project" path="project"  choices="${projects}"/>
	<acme:input-email code="sponsor.sponsorship.form.label.email" path="email" placeholder="example@acme.com"/>					
	<acme:input-url code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo" />
	
	
	<jstl:choose>	 	
		<jstl:when test="${acme:anyOf(_command, 'publish|show|delete|update') && draftMode == true}">
			<acme:submit code="sponsor.sponsorship.form.button.publish" action="/sponsor/sponsorship/publish"/>
			<acme:submit code="sponsor.sponsorship.form.button.update" action="/sponsor/sponsorship/update"/>
			<acme:submit code="sponsor.sponsorship.form.button.delete" action="/sponsor/sponsorship/delete"/>
			<acme:button code="sponsor.sponsorship.form.button.invoice" action="/sponsor/invoice/list?masterId=${id}"/>		
		</jstl:when>	
		<jstl:when test="${acme:anyOf(_command, 'publish') && draftMode == true}">
			<acme:submit code="sponsor.sponsorship.form.button.publish" action="/sponsor/sponsorship/publish"/>	
		</jstl:when>
		<jstl:when test="${_command == 'show'}">
			<acme:button code="sponsor.sponsorship.form.button.show" action="/sponsor/sponsorship/list?masterId=${id}"/>		
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.sponsorship.form.button.create" action="/sponsor/sponsorship/create"/>
		</jstl:when>
	</jstl:choose>	
</acme:form>

