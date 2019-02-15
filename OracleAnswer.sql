-- Q1.
SELECT
    last_name,
    salary,
    department_name
FROM
    employees emp
    JOIN departments dept ON emp.department_id = dept.department_id
WHERE
    commission_pct IS NOT NULL;


-- Q2.

SELECT
    last_name,
    salary,
    job_id
FROM
    employees
WHERE
    manager_id IN (
        SELECT
            employee_id
        FROM
            employees
        WHERE
            last_name = 'King'
    );


-- Q3.

SELECT
    emp.last_name,
    emp.salary
FROM
    employees emp
    JOIN employees man ON emp.manager_id = man.employee_id
WHERE
    emp.salary > man.salary;



-- Q4.

SELECT
    MIN(salary),
    MAX(salary),
    SUM(salary),
    round(AVG(salary),0)
FROM
    employees;


-- Q5.

SELECT
    last_name,
    salary
FROM
    employees emp
WHERE
    salary < (
        SELECT
            AVG(salary)
        FROM
            employees emp2
        WHERE
            emp.DEPARTMENT_ID = emp2.department_id
    );

SELECT
    department_id,
    AVG(salary)
FROM
    employees
GROUP BY
    department_id;