# Ticketing System Project
This ticketing system project represents one of the early milestones in my career, and it holds a special place in my heart as it marks the beginning of my journey in software development.

Overview
The ticketing system, although initially conceptualized for IT-related issues, evolved into a comprehensive solution for managing user account creation requests across various domains within the company. Leveraging a microservices architecture, it encompasses a collection of backend and frontend services seamlessly communicating with each other. Central to its design is an Eureka server facilitating service discovery and registration.

Functionality
User Account Creation Requests: Users can submit requests for account creation in different domains.
Approval Workflow: Requests undergo an approval process before being processed by the IT department.
Assignment: Approved requests are assigned to workers in the IT department for action.
PDF Generation: Once a request is processed, it can be printed in PDF format with designated spaces for signatures.
Technical Details
Microservices Architecture: The system comprises multiple microservices, including backend services for reading from and writing to the database, a frontend service utilizing Thymeleaf for rendering, and a gateway service responsible for security enforcement.
Communication: The microservices communicate via RESTful APIs, with flow control managed through Spring Web Flux and Web Client Builder.
Authentication: The application integrates with Active Directory for user authentication, ensuring secure access to the system.
Service Discovery: Service discovery and registration are facilitated by an Eureka server, enabling seamless communication between services.
PDF Generation: PDF generation is implemented using a combination of backend services and frontend rendering, providing users with printable documents for record-keeping.
Purpose
This project was instrumental in enhancing efficiency and organization within the company, streamlining the account creation process and improving communication between users and IT personnel.
