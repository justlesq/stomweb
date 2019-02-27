<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关键词热度</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript">

        $(function(){

            $("#btnKeyWordHot").click(function(){
                $.ajax({
                    type: "POST",
                    url: '${ctx}/business/taskInfo/getKeyWordHot',
                    data: {nohotword:$("#nohot").val()},
                    dataType:'json',
                    cache: false,
                    success: function(data){
                        $("#researchhot").html(data);
                    }
                });
            });

            $("#nosort").click(function(){
                $("#sort").hide();
            });


        });

    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/business/taskInfo/searchKeyWordHot">关键词热度</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="keyWordHot" action="${ctx}/business/taskInfo/searchKeyWordHot" method="post" class="breadcrumb form-search">
		<label>appleid&nbsp;</label><form:input path="appid" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>关&nbsp;键&nbsp;词</label>
		<form:textarea path="keyword" htmlEscape="false" rows="3" maxlength="1000" class="input-xlarge"/>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>序号</th>
				<th>关键词</th>
				<th id="sort">排名</th>
                <th>指数</th>
			</tr>
		</thead>
		<%--<thead><tr>
			<th>归属公司</th>
			<th>归属部门</th>
			<th class="sort-column login_name">登录名</th>
			<th class="sort-column name">姓名</th>
			<th class="sort-column phone">电话</th>
			<th>手机</th>
		</tr></thead>--%>
		<tbody>
        <c:forEach items="${keywordHotList}" var="keywordHot" varStatus="no">
            <tr>
                <td>${no.index + 1}</td>
                <td>${keywordHot.keyword}</td>
				<c:choose>
					<c:when test="${keywordHot.keywordSort == 0}">
						<script>
                            $("#sort").hide();
						</script>
					</c:when>
					<c:otherwise>
						<td><c:out value="${keywordHot.keywordSort}"></c:out></td>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${keywordHot.keywordHot == 0}">
						<input id="nohot" type="hidden" value="${keywordHot.keyword}"/>
						<td id="researchhot"><input id="btnKeyWordHot" type="button" value="重试" style="width:40px;height:20px;background-color:#42AAE7"/></td>
					</c:when>
					<c:otherwise>
						<td><c:out value="${keywordHot.keywordHot}"></c:out></td>
					</c:otherwise>
				</c:choose>
            </tr>
        </c:forEach>
		</tbody>
	</table>
</body>
</html>