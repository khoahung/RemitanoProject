<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
            		<c:set var = "urlYoutube" scope = "session" value = "${movie.getData()}?autoplay=0&fs=0&iv_load_policy=3&showinfo=0&rel=0&cc_load_policy=0&start=0&end=0&origin=https://youtubeembedcode.com"/>
                	<iframe frameborder="0" scrolling="no" marginheight="0" marginwidth="0"width="788.54" height="443"  src="<c:out value = '${urlYoutube}'/>"></iframe>
			    </td>
            </tr>
        </c:forEach>
      </table>
</body>
</html>