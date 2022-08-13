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
            		<c:set var = "urlYoutube" scope = "session" value = "${movie.getData()}?autoplay=0&fs=0&iv_load_policy=3&showinfo=0&rel=0&cc_load_policy=0&start=0&end=0&origin=https://youtubeembedcode.com"/>
                	<iframe frameborder="0" scrolling="no" marginheight="0" marginwidth="0"width="788.54" height="443"  src="<c:out value = '${urlYoutube}'/>"></iframe>
			    </td>
            </tr>
        </c:forEach>
      </table>
      
</c:if>