<html>
<head>
	<%-- jsp directives --%>
	<%@ page isELIgnored="false" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<title>Maths Question Bank</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
   <a href="QuestionControllerServlet">Back to List</a>
   <br/>
   <br/>

<div id="wrapper">
		<div id="header">
			<h2 align="center"> Maths Question Bank </h2>
		</div>
	</div>


	<table border="1" cellpadding="5">
	<ul>
		<%
			String[] langs = request.getParameterValues("questions");

		    if (langs == null) 
		    {
		    	out.println("No Questions Selected");
		    }
		    else
		    {
		  %>
		  <%@ page import = "java.sql.*" %>
		  <%
		      Connection conn = DriverManager.getConnection(
		          "jdbc:mysql://localhost:3306/question", "root", ""); // <== Check!
		      // Connection conn =
		      //    DriverManager.getConnection("jdbc:odbc:eshopODBC");  // Access
		      Statement stmt = conn.createStatement();
		 
		      String sqlStr = "SELECT qid, question FROM question WHERE qid IN (";
		      sqlStr += "'" + langs[0] + "'";  // First author
		      for (int i = 1; i < langs.length; ++i) {
		         sqlStr += ", '" + langs[i] + "'";  // Subsequent authors need a leading commas
		      }
		      sqlStr += ")  ORDER BY qid DESC";
		 
		      // for debugging
		      System.out.println("Query statement is " + sqlStr);
		      ResultSet rset = stmt.executeQuery(sqlStr);
		  %>
		      <hr>
		      <form method="get" action="select-question-form.jsp">
		        <table border=1 cellpadding=5>
		    	<tr>
					<th scope="col">QID</th>
					<th scope="col">Question</th>
            	</tr>      
		  <%
		      while (rset.next()) {
		       
		  %>
		          <tr>
		           
		            <td align="left"><%= rset.getString("qid") %></td>
		            <td  align="left" style="height:100px;width:1000px"><%= rset.getString("question") %></td>
		          
		          </tr>
		  <%
		      }
		  %>
		        </table>
		        <br>
		      
		      </form>
		 
		  <%
		      rset.close();
		      stmt.close();
		      conn.close();
		    }
		  %>
		</body>
		</html>