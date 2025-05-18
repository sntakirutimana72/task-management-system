<h2>Tasks</h2>

<hr>

<h3>Create New Task</h3>
<form method="post" action="tasks">
  <input type="text" name="title" placeholder="Title" required />
  <input type="text" name="description" placeholder="Description" required />
  <select name="status">
    <option value="PENDING">TODO</option>
    <option value="IN_PROGRESS">IN_PROGRESS</option>
    <option value="COMPLETED">DONE</option>
  </select>
  <select name="priority">
    <option value="LOW">LOW</option>
    <option value="MEDIUM">MEDIUM</option>
    <option value="HIGH">HIGH</option>
  </select>
  <input type="number" name="assignedTo" placeholder="Assigned To (User ID)" required />
  <input type="number" name="createdBy" placeholder="Created By (User ID)" required />
  <input type="number" name="projectId" placeholder="Project ID" required />
  <input type="date" name="dueDate" required />
  <button type="submit">Create</button>
</form>

<hr>

<h3>All Tasks</h3>
<table border="1" cellpadding="5">
  <tr>
    <th>Title</th>
    <th>Description</th>
    <th>Status</th>
    <th>Priority</th>
    <th>Due Date</th>
    <th>Assigned To</th>
    <th>Project</th>
    <th>Actions</th>
  </tr>
  <c:forEach var="task" items="${tasks}">
    <tr data-id="${task.id}">
      <td contenteditable="true" class="editable" data-field="title">${task.title}</td>
      <td contenteditable="true" class="editable" data-field="description">${task.description}</td>
      <td>${task.status}</td>
      <td>${task.priority}</td>
      <td>${task.dueDate}</td>
      <td>${task.assignedTo}</td>
      <td>${task.projectId}</td>
      <td>
        <form method="post" action="tasks" style="display:inline;">
          <input type="hidden" name="_method" value="DELETE"/>
          <input type="hidden" name="id" value="${task.id}" />
          <button type="submit" onclick="return confirm('Delete this task?')">Delete</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

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
        const response = await fetch('tasks', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: formData.toString()
        });

        if (!response.ok) {
          alert(`Failed to update ${field}`);
        }
      } catch (err) {
        console.error(`Error updating ${field}:`, err);
        alert('Network error');
      }
    });
  });
</script>
