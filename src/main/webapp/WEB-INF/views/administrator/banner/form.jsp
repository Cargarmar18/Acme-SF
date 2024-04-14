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
	<acme:input-textbox code="administrator.banner.form.label.startDisplay" path="startDisplay"/>
	<acme:input-textbox code="administrator.banner.form.label.endDisplay" path="endDisplay"/>
	<acme:input-textbox code="administrator.banner.form.label.pictureLink" path="pictureLink"/>
	<acme:input-textbox code="administrator.banner.form.label.slogan" path="slogan"/>
	<acme:input-textbox code="administrator.banner.form.label.targetLink" path="targetLink"/>

	<acme:submit test="${_command == 'create'}" code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
	<acme:submit test="${_command == 'update'}" code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
</acme:form>
