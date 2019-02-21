<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Release Notes</title>
		<style>
			body {
				background: #eef;
			}

			#root {
				width: 1200px;
				padding: 50px;
				background: white;
				margin: 0 auto;
			}

			table {
				border-collapse: collapse;
				border: 1px solid black;
			}

			th {
				border: 1px solid black;
				padding: 5px;
			}

			td {
				border: 1px solid black;
				padding: 5px;	
			}

			.changeType {
				display: inline-block;
				width: 25px;
			}
		</style>
	</head>
	<body>
		<div id="root">
			<div>
				<p>Instruction text</p>
			</div>
			<div>
				<table width="100%">
					<tr>
						<th>#</th>
						<th>eRoom</th>
						<th>Ссылка на eRoom</th>
						<th>Описание</th>
					</tr>
					<#list isssueEntries as issueEntry>
						<tr>
							<td><a href="${issueEntry.redmineUrl}">#${issueEntry.redmineNum}</a></td>
							<td>${issueEntry.eRoomNum}</td>
							<td>
								<#list issueEntry.eRoomUrl as eRoomUrlEntry>
									<div><a href="${eRoomUrlEntry}">${eRoomUrlEntry}</a></div>
								</#list>
							</td>
							<td>${issueEntry.description}</td>
						</tr>
						<tr>
							<td colspan="4">
                                <#list issueEntry.changes as change>
							        <div>
                                        <span class="changeType">${change[0]}</span>
                                        <span>${change[1]}</span>
								    </div>
                                </#list>
							</td>
						</tr>
					</#list>
				</table>
			</div>
		</div>
	</body>
</html>
