<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务信息管理</title>
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
		<li class="active"><a href="${ctx}/business/taskInfo/">任务信息列表</a></li>
		<shiro:hasPermission name="business:taskInfo:edit"><li><a href="${ctx}/business/taskInfo/form">任务信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="taskInfo" action="${ctx}/business/taskInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="sort-column id">ID</th><th>应用名称</th><th appleid">appleId</th><th class="sort-column keyword">关键词</th><th>热度</th><th>任务总量</th><shiro:hasPermission name="business:taskInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="taskInfo">
			<tr>
				<td>${taskInfo.id}</td>
				<td>${taskInfo.appname}</td>
				<td>${taskInfo.appleid}</td>
				<td>${taskInfo.keyword}</td>
				<td>${taskInfo.keywordhot}</td>
				<td>${taskInfo.totalcount}</td>
				<shiro:hasPermission name="business:taskInfo:edit"><td>
    				<a href="${ctx}/business/taskInfo/form?id=${taskInfo.id}">修改</a>
					<a href="${ctx}/business/taskInfo/delete?id=${taskInfo.id}" onclick="return confirmx('确认要删除该任务信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>