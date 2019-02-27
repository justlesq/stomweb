<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>下单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

            // 只能输入[0-9]数字
            jQuery.validator.addMethod("isDigits", function(value, element) {
                return this.optional(element) || /^\d+$/.test(value);
            }, "只能输入0-9数字");


			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/order/">下单列表</a></li>
		<li><a href="${ctx}/order/order/orderDetail">关键词列表</a></li>
		<li class="active"><a href="${ctx}/order/order/form?id=${order.id}">下单<shiro:hasPermission name="order:order:edit">${not empty order.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:order:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="order" action="${ctx}/order/order/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
			<div  style="float: left">
				<label class="control-label">应用名称：</label>
				<div class="controls">
					<form:input path="appname" htmlEscape="false" maxlength="64" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">APPID：</label>
				<div class="controls">
					<form:input path="appid" htmlEscape="false" maxlength="64" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-group" style="float: left;display:inline">
				<label class="control-label">到榜类型：</label>
				<div class="controls">
					<form:select path="top" class="input-mini">
						<form:option value="TOP1" label="TOP1"/>
						<form:option value="TOP2" label="TOP2"/>
						<form:option value="TOP3" label="TOP3"/>
						<form:option value="TOP5" label="TOP5"/>
						<form:option value="TOP10" label="TOP10"/>
					</form:select>
				</div>
			</div>
			<div style="float: left;display:inline">
				<label class="control-label">周期类型：</label>
				<div class="controls">
					<form:select path="cycle" class="input-mini">
						<form:option value="3" label="3天"/>
						<form:option value="7" label="7天"/>
						<form:option value="14" label="14天"/>
						<form:option value="30" label="30天"/>
					</form:select>
				</div>
			</div>
			<div class="control-group" style="display:inline" >
				<label class="control-label">国家：</label>
				<div class="controls">
					<form:select path="country" class="input-mini">
						<form:option value="中国" label="中国"/>
						<form:option value="美国" label="美国"/>
					</form:select>
				</div>
			</div>
			<div  style="float: left;display:inline">
				<label class="control-label">渠道：</label>
				<div class="controls">
					<form:select path="source" cssStyle="width: 290px;"  multiple="true">
						<form:option value="0" label="0"/>
						<form:option value="1" label="1"/>
						<form:option value="2" label="2"/>
						<form:option value="3" label="3"/>
						<form:option value="4" label="4"/>
						<form:option value="5" label="5"/>
						<form:option value="6" label="6"/>
						<form:option value="7" label="7"/>
						<form:option value="8" label="8"/>
						<form:option value="9" label="9"/>
					</form:select>
				</div>
			</div>

			<div class="control-group"  >
				<label class="control-label">开始时间：</label>
				<div class="controls">
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				</div>
			</div>

			<div style="float: left" >
				<label class="control-label">总量：</label>
				<div class="controls">
					<form:input path="total" htmlEscape="false" maxlength="64" class="input-mini required isDigits" value=""/>
				</div>
			</div>
			<div>
				<label class="control-label">关键词：</label>
				<div class="controls">

						<%--<tbody id="orderDetailList">
						</tbody>
						<shiro:hasPermission name="order:order:edit">
							<tfoot>
								<tr><td colspan="11"><a href="javascript:" onclick="addRow('#orderDetailList', orderDetailRowIdx, orderDetailTpl);orderDetailRowIdx = orderDetailRowIdx + 1;" class="btn">新增</a></td></tr>
							</tfoot>
						</shiro:hasPermission>--%>
					<form:textarea id="orderDetail" path="orderDetail" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
				</div>
					<script type="text/template" id="orderDetailTpl">//<!--
						<tr id="orderDetailList{{idx}}">
							<td class="hide">
								<input id="orderDetailList{{idx}}_id" name="orderDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="orderDetailList{{idx}}_delFlag" name="orderDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_keyWord" name="orderDetailList[{{idx}}].keyWord" type="text" value="{{row.keyWord}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_totalCount" name="orderDetailList[{{idx}}].totalCount" type="text" value="{{row.totalCount}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_finishCount" name="orderDetailList[{{idx}}].finishCount" type="text" value="{{row.finishCount}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_lockCount" name="orderDetailList[{{idx}}].lockCount" type="text" value="{{row.lockCount}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_oldRank" name="orderDetailList[{{idx}}].oldRank" type="text" value="{{row.oldRank}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_newRank" name="orderDetailList[{{idx}}].newRank" type="text" value="{{row.newRank}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_hot" name="orderDetailList[{{idx}}].hot" type="text" value="{{row.hot}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_putTime" name="orderDetailList[{{idx}}].putTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.putTime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<td>
								<textarea id="orderDetailList{{idx}}_remarks" name="orderDetailList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="order:order:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#orderDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var orderDetailRowIdx = 0, orderDetailTpl = $("#orderDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(order.orderDetailList)};
							for (var i=0; i<data.length; i++){
								addRow('#orderDetailList', orderDetailRowIdx, orderDetailTpl, data[i]);
								orderDetailRowIdx = orderDetailRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:order:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>