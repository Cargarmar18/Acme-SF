<%--
- list-mine.jsp
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
	<acme:list-column code="sponsor.invoice.list-mine.label.code" path="code" />
	<acme:list-column code="sponsor.invoice.list-mine.label.dueDate" path="dueDate"/>
	<acme:list-column code="sponsor.invoice.list-mine.label.totalAmount" path="totalAmount"/>
	<acme:list-column code="sponsor.invoice.list-mine.label.draftMode" path="draftMode"/>
	<acme:list-column code="sponsor.invoice.list-mine.label.sponsorship" path="sponsorship.code"/>
</acme:list>
	<acme:button code="sponsor.sponsorhip.list-mine.button.create" action="/sponsor/invoice/create"/>	


