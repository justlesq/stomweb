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

		function deleteOrder(orderId){
            alert(orderId);
            var r = confirm("确定删除吗？");
            if (r == true){
                window.location.href = "${ctx}/order/order/delete?id="+orderId;
                //$('#dataGrid').dataGrid("reload");
            }
		}


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
                {header:'appleid', name:'appid', index:'appid',width:'85', align:"center"},
                {header:'词量', name:'keyWordCount',index:'keyWordCount',width:'65', align:"center",formatter: function(val, obj, row, act){
                    return val+'词';
                }},
                {header:'目标到榜', name:'top', index:'top',width:'65',align:"center"},
                {header:'周期', name:'cycle', width:'40',align:"center",fixed:true,formatter: function(val, obj, row, act){
                    return val+'天';
                }},
                {header:'渠道', name:'source', width:'110',align:"center", fixed:true},
                {header:'国家', name:'country', index:'country', width:'50',fixed:true, align:"center"},
                {header:'下单时间', name:'createDate', index:'createDate',width:'130',align:"center",formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
//                {header:'开始时间', name:'beginTime', index:'beginTime',width:'130',align:"center",formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
//                {header:'结束时间', name:'endTime',index:'endTime',width:'130',align:"center",formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
                {header:'状态', name:'status', width:'70',align:"center", fixed:true, formatter: function(val, obj, row, act){
                    if(val == 0){
                        return "<label style = 'color: darkseagreen;cursor: default'>"+'等待排期'+"</label>";
					}else if(val == 1){
                        return "<label style = 'color: darkseagreen;cursor: default'>"+'执行中'+"</label>";
                    }else if(val == 2){
                        return "<label style = 'color: darkseagreen;cursor: default'>"+'完成'+"</label>";
                    }
                }},
                {header:'操作', name:'status', width:'110',align:"center", fixed:true, formatter: function(val, obj, row, act){
                    var actions = [];
                    <shiro:hasPermission name="order:order:edit">
                    actions.push('<a href="${ctx}/order/order/form?id='+row.id+'" class="btnList" title="编辑">编辑</a>&nbsp;|&nbsp;');
                    actions.push('<a href="${ctx}/order/order/delete?id='+row.id+'" class="btnList" title="删除" data-confirm="确认要删除吗？">删除</a>&nbsp;');
                    </shiro:hasPermission>
                    return actions.join('');
                }},
                {header:'结算',name:'status',width:'77',align:"center", fixed:true,formatter: function(val, obj, row, act){
                    if(val == 2){
                        return "<input type = 'button' onclick='over(row.id)' value='结算'></label>";
                    }else{
                        return "<label style = 'color: darkseagreen;cursor: default'>"+'未结算'+"</label>";
					}
                }}
            ]
        });
	</script>
</body>
</html>