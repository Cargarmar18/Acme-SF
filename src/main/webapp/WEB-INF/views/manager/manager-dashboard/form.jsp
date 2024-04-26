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
	<acme:input-integer code="manager.manager-dashboard.form.label.number-priority-must" path="numberOfMustStatusUserStories" readonly = "true"/>		
	<acme:input-integer code="manager.manager-dashboard.form.label.number-priority-should" path="numberOfShouldStatusUserStories" readonly = "true"/>		
	<acme:input-integer code="manager.manager-dashboard.form.label.number-priority-could" path="numberOfCouldStatusUserStories" readonly = "true"/>		
	<acme:input-integer code="manager.manager-dashboard.form.label.number-priority-willnot" path="numberOfWillNotStatusUserStories" readonly = "true"/>		
	<acme:input-double code="manager.manager-dashboard.form.label.average-cost-user-stories" path="averageCostUserStories" readonly = "true"/>		
	<acme:input-double code="manager.manager-dashboard.form.label.deviation-cost-user-stories" path="deviationCostUserStories" readonly = "true"/>			
	<acme:input-integer code="manager.manager-dashboard.form.label.maximum-cost-user-stories" path="maximumCostUserStories" readonly = "true"/>			
	<acme:input-integer code="manager.manager-dashboard.form.label.minimum-cost-user-stories" path="minimumCostUserStories" readonly = "true"/>			
	<acme:input-double code="manager.manager-dashboard.form.label.average-cost-projects" path="averageCostProjects" readonly = "true"/>			
	<acme:input-double code="manager.manager-dashboard.form.label.deviation-cost-projects" path="deviationCostProjects" readonly = "true"/>			
	<acme:input-integer code="manager.manager-dashboard.form.label.maximum-cost-projects" path="maximumCostProjects" readonly = "true"/>			
	<acme:input-integer code="manager.manager-dashboard.form.label.minimum-cost-projects" path="minimumCostProjects" readonly = "true"/>				
</acme:form>