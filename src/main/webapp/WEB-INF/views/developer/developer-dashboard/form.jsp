
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-integer code="developer.developer-dashboard.form.label.number-with-update-moment" path="numberWithUpdateMoment" readonly = "true"/>		
	<acme:input-integer code="developer.developer-dashboard.form.label.number-with-link" path="numberWithLink" readonly = "true"/>		
	<acme:input-double code="developer.developer-dashboard.form.label.average-total-time" path="averageTotalTime" readonly = "true"/>		
	<acme:input-double code="developer.developer-dashboard.form.label.deviation-total-time" path="deviationTotalTime" readonly = "true"/>		
	<acme:input-integer code="developer.developer-dashboard.form.label.min-total-time" path="minTotalTime" readonly = "true"/>		
	<acme:input-integer code="developer.developer-dashboard.form.label.max-total-time" path="maxTotalTime" readonly = "true"/>			
</acme:form>