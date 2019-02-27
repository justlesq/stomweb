<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>cookie管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cookie/cookieRefresh/">cookie列表</a></li>
		<shiro:hasPermission name="cookie:cookieRefresh:edit"><li><a href="${ctx}/cookie/cookieRefresh/form">cookie添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cookieRefresh" action="${ctx}/cookie/cookieRefresh/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>cookie：</label><form:input path="cookie" htmlEscape="false" maxlength="50" class="input-medium"/></li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<td>cookie</td>
				<shiro:hasPermission name="cookie:cookieRefresh:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cookieRefresh">
			<tr>
				<td>${cookieRefresh.cookie}</td>
				<shiro:hasPermission name="cookie:cookieRefresh:edit"><td>
    				<a href="${ctx}/cookie/cookieRefresh/form?id=${cookieRefresh.id}">修改</a>
					<a href="${ctx}/cookie/cookieRefresh/delete?id=${cookieRefresh.id}" onclick="return confirmx('确认要删除该cookie吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>