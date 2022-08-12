<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body onload='document.loginForm.username.focus();'>
	<h3>Remitano Project Test</h3>
	<h3>Funny Movies</h3>
	<c:if test="${not empty error}"><div>${error}</div></c:if>
	<c:if test="${not empty message}"><div>${message}</div></c:if>

	<form name='login' action="<c:url value='/loginPage' />" method='POST'>
		<table>
			<tr>
				<td>UserName:</td>
				<td><input type='text' name='username' value=''></td>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
				<td colspan='2'><input name="submit" type="submit" value="Login/Register" /></td>
			</tr>
		</table>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
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
</body>
</html>