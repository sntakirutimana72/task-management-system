<c:if test="${not empty sessionScope.user}">
  <p>Welcome, ${sessionScope.user.name}!</p>
  <form action="session" method="get">
    <input type="hidden" name="action" value="logout" />
    <button type="submit">Logout</button>
  </form>
</c:if>
<c:if test="${empty sessionScope.user}">
  <a href="session?action=login"><button>Login</button></a>
</c:if>
