

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:list>
	<acme:list-column code="administrator.banner.list.label.startDisplay" path="startDisplay" width="10%"/>
	<acme:list-column code="administrator.banner.list.label.endDisplay" path="endDisplay" width="10%"/>	
	<acme:list-column code="administrator.banner.list.label.pictureLink" path="pictureLink" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.targetLink" path="targetLink" width="30%"/>
</acme:list>

<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>


