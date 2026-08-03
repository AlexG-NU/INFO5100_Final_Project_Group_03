# Global Workforce Staffing Network

## Project Overview

Hiring a contractor involves more than finding a person. The client defines the staffing requirements and reviews candidates. The staffing agency recruits candidates and creates the contractor assignment after the client approves a candidate. Compliance confirms that the contractor has the required credentials and is cleared to begin work. Payroll processes approved timecards, and Billing prepares the related client invoices.

Our project brings these steps into one application so that each handoff is visible and the same contractor, assignment, credential, timecard, and billing records are used throughout the process.

---

## Project Structure and Roles

The network contains four enterprises: Client, Staffing Agency, Compliance Services, and Payroll and Billing. Network and enterprise administrators maintain the system structure and user accounts, but they are separate from the ten operational roles listed below.

| Enterprise | Organizations | Roles and Responsibilities |
| :--- | :--- | :--- |
| **Client** | Human Resources<br>Project Management | • **Hiring Manager**: Creates staffing requests and reviews candidates.<br><br>• **Project Supervisor**: Assigns contractor tasks and approves timecards.<br><br>• **Contractor**: Views assigned tasks and submits weekly timecards. |
| **Staffing Agency** | Recruitment<br>Contractor Management | • **Recruiter**: Reviews staffing requests, manages candidates, and sends candidate submissions to the client.<br><br>• **Contractor Coordinator**: Creates contractor assignments and submits them for compliance review. |
| **Compliance Provider** | Background Screening<br>Credentialing and Compliance | • **Compliance Manager**: Assigns cases to analysts, monitors workload, and views reports.<br><br>• **Compliance Analyst**: Reviews the overall case, requests credential verification, evaluates the returned result, and makes the final decision.<br><br>• **Credential Specialist**: Checks the required credential evidence and returns the verification result to the analyst. |
| **Payroll Provider** | Payroll Processing<br>Billing | • **Payroll Specialist**: Processes approved time and calculates contractor pay.<br><br>• **Billing Analyst**: Prepares client invoices and tracks billing status. |

---

## How Work Moves Through the Network

1. The hiring manager sends a staffing request to the Staffing Agency.


2. The recruiter sends a candidate submission to the hiring manager for review.


3. After the hiring manager approves a candidate, the contractor coordinator creates the contractor assignment and submits it for compliance review.


4. The compliance manager assigns the case to an analyst.


5. The compliance analyst selects the required credential, adds instructions when needed, and sends the credential verification task to the credential specialist.


6. The credential specialist checks the credential evidence and returns the verification result to the analyst.


7. The compliance analyst reviews the result, records the final findings, and approves or rejects the case. Approval changes the assignment status to Cleared. Rejection changes it to compliance Rejected.


8. After clearance, the contractor coordinator activates the assignment.


9. The Contractor submits a weekly timecard, and the Project Supervisor approves it.


10. The payroll specialist processes the approved timecard, and the billing analyst creates the related client invoice.



---

## Main Application Features

* One login screen with role based routing for all user accounts.


* Create, view, update, and delete functions where appropriate for the record owner.


* Validation for required fields, dates, automatically generated IDs, duplicate credentials, and review notes.


* Shared work queues showing the request status, sender, receiver, and the role expected to act next.


* Java Faker demonstration data and reports so the complete workflow can be shown without entering every record manually.



---

## Demo Accounts & Logins

| Type | Role | Username | Password |
| --- | --- | --- | --- |
| **Admin** | Network Administrator | `network.admin` | `password` |
| **Admin** | Staffing Enterprise Admin | `staffing.admin` | `password` |
| **Admin** | Compliance Enterprise Admin | `compliance.admin` | `password` |
| **Admin** | Client Enterprise Admin | `client.admin` | `password` |
| **Admin** | Payroll Enterprise Admin | `payroll.admin` | `password` |
| **Non-admin** | Hiring Manager | `HR` | `password` |
| **Non-admin** | Recruiter | `recruiter` | `password` |
| **Non-admin** | Compliance Manager | `C.manager` | `password` |
| **Non-admin** | Compliance Analyst | `C.analyst` | `password` |
| **Non-admin** | Credential Specialist | `C.specialist` | `password` |
| **Non-admin** | Contractor Coordinator | `coordinator` | `password` |
| **Non-admin** | Contractor | `Contractor` | `password` |
| **Non-admin** | Project Supervisor | `Sup` | `password` |
| **Non-admin** | Payroll Specialist | `p.specialist` | `password` |
| **Non-admin** | Billing Analyst | `b.analyst` | `password` |
