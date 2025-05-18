<h2>Login</h2>

<c:if test="${not empty error}">
  <div style="color: red;">${error}</div>
</c:if>

<form action="session" method="POST">
  <label>Email:</label>
  <input type="text" name="email" value="${param.email}" required />

  <label>Password:</label>
  <input type="password" name="password" required />

  <button type="submit">Login</button>
</form>
