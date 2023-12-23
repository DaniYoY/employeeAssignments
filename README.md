# employeeAssignmentsProject

This is an app to manage employee assignments on different projects

    Assumptions:
    - Employee IDs and Project IDs can consist of alphanumeric characters
    - Clients will select date format by submitting the file OR defaul is used "yyyy-MM-d" (year-month-day, eg 2023-12-6)
    - Clients will inform during file upload, if file has a header (acceptable texts: Y/YES/TRUE/N/NO/FALSE) OR Exception is thrown

Main Goal : to upload a CSV files (EmpID, ProjectID, DateFrom, DateTo) and based on the data to identify the pair of employees who
have worked together on common projects for the longest period
of time and the time for each of those projects.

Approach: REST API
1. Algorithm approach:
   - The task is separated on 2 levels: 
      - Repository level - In the DB only unique values of assignments are stored, so a query has to be created to get all pairings. 

      - Service level - calculate for each pair employees the total number of days worked on common projects and keep this number 
   with a map total number of days worked per project. 


- Why this approach is selected? It requires only one request to the DB (speed) and at the same time the application gets enough
data, so it can be represented per client specification (flexibility). 
Additionally, it is ensured that DB data is normalized as much as possible (data management) and on a service level it is 
iterated with linear time complexity (O(n)).

2. File upload: 
- The file upload requires additional client input in order to parse the data. The purpose is to ensure smooth data processing.
By client input if headers are present (mandatory) and what the date type is (default is set to yyyy-MM-d). File can be processed
and data processed without issues.

### Models:
DB Entity Models:
- Assignment - class that has Employee, Project, start and end date
The class is used to link a project with an employee for a specific time period
Employee can be assigned to many projects at the same time
If 2 or more Assignments link an employee with a project and the time is overlapping, those will be merged
- Employee - class that has DB id and personal ID number
- Project - class that has DB id and project ID number

DTO Models: 
- AssignmentDTO - used to share data with client and get assignments from clients
- EmployeePairDTO - internal to the app. Used for specific query from Postgres DB. It helps transferring data for joint assignments
- TeamDTO - internal to the app. It links 2 Employees in a team. It is important to note that Employees in TeamDTOs are interchangeable 
("A and B" is same as "B and A")
- TeamAssignmentDTO - internal to the app. Contains map of key value pair of project Id and duration number and total duration number

### Supported services:
#### REST
1. Post /assignments/upload - uploads file from form-data Body. It requires file File, text areHeaders, text dateFormat. 
1. Get /assignments - getAll assignments from DB
1. Post /assignments - creates an assignment. Required raw Body and AssignementDTO in JSON format
2. Get /assignments/pairs gets all possible pairs of Employees who worked on 1 project at the same time 
2. Get /assignments/longestrunningteam - returns 1 String message in the following format:
"Employee 1,Employee 2, total number of days worked together" and below for each project "id,number of days worked together".
This output is reached by using query of getting all pairs worked on a project. Then each pair is mapped by TeamDTO and TeamAssignmentDTO.
First the team is checked if already in the map, then prject is checked if already mapped for the team, then the duration od days is added
to the team and to the total number of days as a totalDuration.
If the team or the project is new, then the count starts from 0.  
2. Get /assignments/{employeeId}/{projectID}/all - gets all Assignments of an Employee on a project
2. Get /assignments/{employeeId}/{projectID} - gets all Assignments of an Employee on a project
2. Get /assignments/{employeeId}/{projectID}/{id} - gets Assignment with id of an Employee on a project. If Id does not match
   Employee and Project, then exception is thrown
2. Get /assignments/{id} - gets an Assignment with id
2. Delete /assignments/{id}- deletes an Assignment with id
2. Put /assignments/{id} - updates an Assignment with id, it requires raw Body and AssignementDTO in JSON format
1. Get /employees - Gets all employees' personal ID
1. Post /employees - creates a new employee
1. Get /employees/{personalid} - gets the id for an employee with id
1. Put /employees/{personalid} -updates the employee personal id
1. Delete /employees/{personalid} - deletes all assignements related to the employee and then deletes the employee record

### Dependencies: 
- Spring Data
- Spring JPA
- Spring Web
- Lombok
- Postgres SQL 