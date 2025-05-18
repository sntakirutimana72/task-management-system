CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL,
  hashed_password TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT users_unique_email UNIQUE(email)
);


CREATE TABLE groups (
  id SERIAL PRIMARY KEY,
  name VARCHAR(24) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT groups_unique_name UNIQUE(name)
);


CREATE TABLE user_groups (
  id SERIAL PRIMARY KEY,
  group_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL
);
CREATE INDEX user_groups_group_id_user_id_index ON user_groups(group_id, user_id);


CREATE TYPE permission_actions AS ENUM ('READ', 'EDIT', 'CREATE', 'DESTROY', 'ALL');

CREATE TABLE permissions (
  id SERIAL PRIMARY KEY,
  resource VARCHAR(16) NOT NULL,
  actions permission_actions[],
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE roles (
  id SERIAL PRIMARY KEY,
  name VARCHAR(16) NOT NULL,
  group_id INT NOT NULL REFERENCES groups(id),
  permission_id INT NOT NULL REFERENCES permissions(id),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT roles_unique_name UNIQUE(name)
);
CREATE INDEX roles_permission_id_group_id_index ON roles(permission_id, group_id);


CREATE TABLE projects (
  id SERIAL PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  description TEXT NOT NULL,
  created_by INT NOT NULL REFERENCES users(id),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TYPE task_status AS ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETED');
CREATE TYPE task_priorities AS ENUM ('LOW', 'MEDIUM', 'HIGH');

CREATE TABLE tasks (
  id SERIAL PRIMARY KEY,
  title VARCHAR(60) NOT NULL,
  description TEXT NOT NULL,
  status task_status NOT NULL DEFAULT 'PENDING',
  priority task_priorities NOT NULL DEFAULT 'LOW',
  created_by INT NOT NULL REFERENCES users(id),
  assigned_to INT NOT NULL REFERENCES users(id),
  project_id INT NOT NULL REFERENCES projects(id),
  due_date DATE NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
