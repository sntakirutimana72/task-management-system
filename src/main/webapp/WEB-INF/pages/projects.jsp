<h2>Projects</h2>
<table border="1" cellpadding="5">
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Description</th>
    <th>CreatedBy</th>
    <th>CreatedAt</th>
    <th>UpdatedAt</th>
  </tr>
  <c:forEach var="project" items="${projects}">
    <tr data-id="${project.id}">
      <td contenteditable="true" class="editable" data-field="name">
        ${project.name}
      </td>
      <td contenteditable="true" class="editable" data-field="description">
        ${project.description}
      </td>
      <td>${project.createdBy}</td>
      <td>${project.createdAt}</td>
      <td>${project.updatedAt}</td>
      <td>
        <form method="post" action="projects" style="display:inline;">
          <input type="hidden" name="_method" value="DELETE"/>
          <input type="hidden" name="id" value="${project.id}" />
          <button type="submit" onclick="return confirm('Delete this project?')">Delete</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

<hr/>

<h3>Create New Project</h3>
<form method="POST" action="projects">
  <label>Name: <input type="text" name="name" required /></label><br/>
  <label>Description: <textarea name="description" required></textarea></label><br/>
  <button type="submit">Create</button>
</form>

<script>
  document.querySelectorAll('.editable').forEach(cell => {
    cell.addEventListener('blur', async function () {
      const row = cell.closest('tr');
      const id = row.dataset.id;
      const field = cell.dataset.field;
      const value = cell.textContent.trim();

      const formData = new URLSearchParams();
      formData.append('_method', 'PUT');
      formData.append('id', id);
      formData.append('field', field);
      formData.append('value', value);

      try {
        const response = await fetch('projects', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: formData.toString()
        });

        if (!response.ok) {
          alert('Failed to save changes');
        }
      } catch (err) {
        console.error('Error saving changes:', err);
        alert('Network error saving changes');
      }
    });
  });
</script>
