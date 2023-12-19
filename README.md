# employeeAssignments
This is an app to manage employee assignments on different projects

    Assumptions:
    - Employee IDs and Project IDs can consist of alphanumeric characters
    - Clients will select date format by submitting the file

### Models:
- Assignment - class that has Employee, Project, start and end date
The class is used to link a project with an employee for a specific time period
Employee can be assigned to many projects at the same time
If 2 or more Assignments link an employee with a project and the time is overlapping, those will be merged
- Employee - class that has DB id and personal ID number
- Project - class that has DB id and project ID number

### Supported services:
#### REST