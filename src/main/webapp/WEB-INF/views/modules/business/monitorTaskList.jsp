<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>APP监控</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(function(){
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出排名数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/business/taskInfo/export");
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });


            $("select[name='appname']").change(function () {
                $.ajax({
                    type: "POST",
                    url: '${ctx}/business/taskInfo/getKeyWordByAppName',
                    data: {appname:$("select[name='appname']").val()},
                    dataType:'json',
                    cache: false,
                    success: function(data){
                            //json字符串(json数组)
                            var jsonArrayStr = data.jsonArray;
                            //console.log(jsonArrayStr);
                            var keyword = document.getElementById('keyword');
                            keyword.length = 0 ;
                            //console.log(keyword.length);
                            //清空数组
//                            var objects=$("#s2id_keyword").children("a").children("span");
//                            objects[0].html("");

//                            //添加空值
                            var option=new Option('','');
                            keyword.add(option);

                            $.each(jsonArrayStr,function (index, obj) {
                                //console.log(jsonArrayStr[index].keyword);
                                var xValue=jsonArrayStr[index];
                                var xText=jsonArrayStr[index];
                                var option=new Option(xText,xValue);
                                keyword.add(option);
                            });
                    }
                });
            });
        });
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/business/taskInfo/dataMap">APP排名监控</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="keyWordSort" action="${ctx}/business/taskInfo/dataMap" method="post" class="breadcrumb form-search">
        <label>APP应用名称：</label>
        <select name="appname" id="appname" class="required input-mini" style="width:163px;">
            <option value="">全部</option>
            <c:forEach items="${appNameList}" var="appname">
                <option value="${appname}" ${appname_s==appname?'selected':''}>${appname}</option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;
        <label>关键词：</label>
        <select name="keyword" id="keyword" class="required input-mini" style="width:163px;">
            <option value="">全部</option>
            <c:forEach items="${keywordList}" var="keyword">
                <option value="${keyword}"  ${keyword_s==keyword?'selected':''}>${keyword}</option>
            </c:forEach>
        </select>

        &nbsp;&nbsp;&nbsp;
        <label>上架时间：</label>
        <input id="time" name="time" type="text"  readonly="readonly" maxlength="20" class="input-medium Wdate"
               value="<fmt:formatDate value="${time_s}" pattern="yyyy-MM-dd"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd    ',isShowClear:false});"/>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>

        <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
	</form:form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>序号</th>
				<th>关键词</th>
				<th>0点</th><th>1点</th><th>2点</th><th>3点</th><th>4点</th><th>5点</th><th>6点</th>
				<th>7点</th><th>8点</th><th>9点</th><th>10点</th><th>11点</th><th>12点</th><th>13点</th>
				<th>14点</th><th>15点</th><th>16点</th><th>17点</th><th>18点</th><th>19点</th><th>20点</th>
				<th>21点</th><th>22点</th><th>23点</th><th>24点</th>
			</tr>
		</thead>
		<tbody>
        <c:choose>
            <c:when test="${!empty page}">
                <c:forEach items="${page}" var="map" varStatus="no">
                    <tr>
                        <td>${ no.index + 1}</td>
                        <td><c:out value="${map.key}"></c:out></td>
                        <c:forEach items="${map.value}" var="list">
                            <c:choose>
                                <c:when test="${list == 3}">
                                    <td style="color: sandybrown; font-size: 18px;"><c:out value="${list}"></c:out></td>
                                </c:when>
                                <c:when test="${list == 2}">
                                    <td style="color: sandybrown; font-size: 18px;"><c:out value="${list}"></c:out></td>
                                </c:when>
                                <c:when test="${list == 1}">
                                    <td style="color: sandybrown; font-size: 18px;"><c:out value="${list}"></c:out></td>
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="${list}"></c:out></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="25" align="center">没有数据</td></tr>
            </c:otherwise>
        </c:choose>
		</tbody>
	</table>
</body>
</html>