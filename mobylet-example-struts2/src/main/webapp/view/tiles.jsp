<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="main">
		<s:form action="tiles" method="get">
		input-&gt;<m:wrap istyle="4"><s:textfield name="message" size="10"></s:textfield></m:wrap><br />
		<s:submit value="GO!" />
		</s:form>
		output-&gt;${message}<br />
		<hr />
		<c:import url="include.jsp">
			<c:param name="pmm" value="PMM"></c:param>
		</c:import>
	</tiles:putAttribute>
</tiles:insertDefinition>
