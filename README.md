
Title: ScheduleSoft
Purpose: Managing customers and appointments

Author: Muzaffer Fatih Pirim
IDE: IntelliJ Community 2020.01
JDK: Java SE 17.0.1
JavaFX: JavaFX-SDK-17.0.6

Instructions on running the program:

1. Launch application from Main.java located in: src/main/java/com/example/schedulesoft/Main.java

2. Log in using credentials (Ex: username=admin, password=admin)

3. Upon successful log-in, the user is met with the dashboard page which contains different reports.
   The reports can be toggled using the respective combo-boxes.

4. The user can navigate to different panels by clicking on the menu items on the sidebar. 
   Clicking log-out on the sidebar will terminate the session and log the user out

5. Clicking "Customers" will bring up the customers panel. The user can add a customer by clicking the "add" button, 
   which brings up the customer form. Selecting a customer in the table will display the "edit" and "delete" buttons. 
   Clicking "edit" will bring up customer form. Clicking delete will bring up a confirmation, and upon confirmation, 
   delete the customer.

6. Clicking "Appointments" will bring up the appointments panel. The user can schedule an appointment by 
   clicking the "add" button, which brings up the appointment form. Selecting an appointment in the table will display
   the "edit", "delete", and "adjust" buttons. Clicking "edit" will bring up appointment form. Clicking delete will bring 
   up a confirmation, and upon confirmation, delete the appointment. Clicking adjust will bring up a dialog box to adjust
   the appointment time.

7. The user may use the combo-box on the top right of the appointment table to filter appointments by all appointments,
   appointments this week, and appointments this month

Description of the additional report in A3F: 

The additional report is found in the Dashboard panel. It can be viewed by clicking the combo-box on top of the chart and 
selecting "By Country". The chart displays the number of appointments associated with each country. If a customer is from 
England and has an appointment, their appointment will be added to the number of appointments in England.

MySQL Connector Driver: mysql-connector-java-8.0.26
   