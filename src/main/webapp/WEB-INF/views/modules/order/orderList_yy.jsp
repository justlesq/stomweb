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
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/order">下单列表</a></li>
		<li ><a href="${ctx}/order/order/orderDetail">关键词列表</a></li>
		<shiro:hasPermission name="order:order:edit"><li><a href="${ctx}/order/order/form">下单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/order/order/listData" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<%--<sys:message content="${message}"/>--%>



	<table id="dataGrid"></table>
	<div class="pagination" id="dataGridPage"></div>
	<link href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
	<script type="text/javascript">
        $('#dataGrid').dataGrid({
            searchForm: $("#searchForm"),
            // 设置数据表格列
            columnModel: [
                {header:'logo', name:'logo', index:'phone', sortable:false,width:'45',align:"center",formatter: function(val, obj, row, act){
                    return "<img alt='' src='"+val+"' style='width: 38px;height: 35px'>";
                }},
                {header:'应用名称', name:'appname', index:'appname', width:'120',align:"center",formatter: function(val, obj, row, act){
                    return '<a href="${ctx}/order/order/orderDetail?orderId='+row.id+'" >'+val+'</a>';
                }},
                {header:'appleid', name:'appid', index:'appid',width:'120', align:"center"},
                {header:'词量', name:'keyWordCount',index:'keyWordCount',width:'75', align:"center",formatter: function(val, obj, row, act){
                    return val+'词';
                }},
                {header:'国家', name:'country', index:'country', width:'90',fixed:true, align:"center"},
                {header:'目标到榜', name:'top', index:'top',width:'88',align:"center"},
                {header:'周期', name:'cycle', width:'40',align:"center",fixed:true,formatter: function(val, obj, row, act){
                    return val+'天';
                }},
                {header:'渠道', name:'source', width:'80',align:"center", fixed:true},
                {header:'下单时间', name:'createDate', index:'createDate',width:'130',align:"center",formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
                {header:'开始时间', name:'beginTime', index:'beginTime',width:'130',align:"center",formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
                {header:'结束时间', name:'endTime',index:'endTime',width:'130',align:"center",formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
                {header:'状态', name:'status', width:'70',align:"center", fixed:true, formatter: function(val, obj, row, act){
                    if(val == 0){
                        return "<label style = 'color: darkseagreen;cursor: default'>"+'等待排期'+"</label>";
					}
                }},
                {header:'结算',name:'status',width:'70',align:"center", fixed:true,formatter: function(val, obj, row, act){
                    if(val != 3){
                        return "<label style = 'color: darkseagreen;cursor: default'>"+'未结算'+"</label>";
                    }else{
                        return "<input type = 'button' onclick='over(row.id)' value='结算'></label>";
					}
                }}
            ]
        });
	</script>
</body>
</html>