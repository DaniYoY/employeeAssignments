CREATE TABLE IF NOT EXISTS projects
(
    id bigserial PRIMARY KEY,
    project_number varchar (255) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS employees
(
    id bigserial PRIMARY KEY,
    personal_number varchar (255) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS assignments
(
    id bigserial PRIMARY KEY,
    employee_id integer NOT NULL,
    project_id integer NOT NULL,
    start_date date NOT NULL,
    end_date date,
    CONSTRAINT assignments_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES employees (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT assignments_project_id_fkey FOREIGN KEY (project_id)
        REFERENCES projects (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)