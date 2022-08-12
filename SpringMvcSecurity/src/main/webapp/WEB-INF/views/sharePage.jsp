<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<c:if test="${pageContext.request.userPrincipal.name != null}">
	<table>
			<tr>
			<td><b>Funny Movies</b></td>
			<td><b>Welcome ${username} </b></td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><INPUT TYPE="BUTTON" VALUE="Share a movie" ONCLICK="location.href='${pageContext.request.contextPath}/sharePage'"></td>
			<td><INPUT TYPE="BUTTON" VALUE="Logout" ONCLICK="javascript:document.getElementById('logout').submit()"></td>
			</tr>
	</table>
	
	<form action ="${pageContext.request.contextPath}/sharePage" method = "POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<table>
			<tr>
         		<td>Youtube URL: </td>
         		<td><input type = "text" name = "data"></td>
         	</tr>
			<tr>
				<td>&nbsp;</td>
         		<td><input type = "submit" value = "share" /></td>
         	</tr>
         </table>
     </form>
      <table>
      <c:forEach var="movie" items="${movies}" >
            <tr>
            	<td>
                	<iframe width="420" height="315"
						src="${movie.getData()}">
					</iframe>
			    </td>
            </tr>
        </c:forEach>
      </table>
      
</c:if>