<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>下单管理</title>
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
<body  scroll="no" >
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/order/order">下单列表</a></li>
		<li class="active"><a href="${ctx}/order/order/orderDetail">关键词列表</a></li>
		<shiro:hasPermission name="order:order:edit"><li><a href="${ctx}/order/order/form">下单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderDetail" action="${ctx}/order/order/orderDetailData?orderId=${orderId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<%--<sys:message content="${message}"/>--%>



	<table id="dataGrid"></table>
	<div class="pagination" id="dataGridPage"></div>
	<link href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
	<script type="text/javascript">
        $('#dataGrid').dataGrid({
            url: '${ctx}/order/order/orderDetailData?orderId=${orderId}',
            datatype: "json",
            cellEdit:true,//是否开启单元格的编辑功能
            cellsubmit:'remote',//or 'clientArray',remote代表每次编辑提交后进行服务器保存，clientArray只保存到数据表格不保存到服务器
            cellurl:'updateOrderDetail',//cellsubmit要提交的后台路径
            altRows: true,
            // 设置数据表格列
            columnModel: [
                {header:'应用名称', name:'appname', index:'appname', width:'65',align:"center"},
                {header:'关键词', name:'keyWord', index:'keyWord', width:'65',align:"center"},
                {header:'总量', name:'totalCount',editable:true,index:'totalCount', width:'40',align:"center"},
                {header:'完成量', name:'finishCount', index:'finishCount', width:'45',align:"center"},
                {header:'锁量', name:'lockCount', index:'lockCount', width:'45',align:"center"},
                {header:'原始排名', name:'oldRank', index:'oldRank', width:'45',align:"center"},
                {header:'最新排名', name:'newRank', index:'newRank', width:'45',align:"center"},
                {header:'热度', name:'hot', index:'hot', width:'45',align:"center"},
                {header:'上架时间', name:'putTime', index:'putTime', width:'85',align:"center"},
                {header:'状态', name:'keyWord', index:'keyWord', width:'85',align:"center"},
                {header:'操作', name:'keyWord', index:'keyWord', width:'85',align:"center"}
            ]
        });
	</script>
</body>
</html>