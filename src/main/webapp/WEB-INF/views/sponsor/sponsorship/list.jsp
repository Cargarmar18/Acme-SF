<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="sponsor.sponsorship.form.label.code" path="code" />
	<acme:list-column code="sponsor.sponsorship.form.label.moment" path="moment"/>
	<acme:list-column code="sponsor.sponsorship.form.label.startSponsor" path="startSponnsor"/>
	<acme:list-column code="sponsor.sponsorship.form.label.endSponsor" path="endSponsor"/>
	<acme:list-column code="sponsor.sponsorship.form.label.amount" path="amount"/>
	<acme:list-column code="sponsor.sponsorship.form.label.sponsorshipType" path="sponsorshipType"/>
	<acme:list-column code="sponsor.sponsorship.form.label.email" path="email" />					
	<acme:list-column code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo" />
	<acme:list-column code="sponsor.sponsorship.list-mine.label.draft-mode" path="draftMode" width="20%"/>
</acme:list>
	<acme:button code="sponsor.sponsorship.list-mine.button.create" action="/sponsor/sponsorship/create"/>	


