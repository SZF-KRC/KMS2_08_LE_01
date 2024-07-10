package manager.menu;
import manager.control.InputControl;
import manager.control.SqlControl;
import manager.interfaces.IEmployeeService;
import manager.interfaces.IPayrollService;
import manager.interfaces.IPersonnelRecords;
import manager.management.*;
import manager.services.*;

public class Menu {
    IPayrollService payrollService = new PayrollService();
    InputControl control = new InputControl();
    SqlControl sqlControl = new SqlControl();

    IEmployeeService employeeService = new EmployeeService();
    IPersonnelRecords personnelRecords = new PersonnelRecords(control,employeeService,sqlControl);
    WorkTime workTime = new WorkTime(new WorkTimeService(),control, payrollService,sqlControl);
    AdministrationPayroll administration = new AdministrationPayroll(payrollService,control,employeeService,sqlControl);
    VacationAndAbsence vacationAndAbsence = new VacationAndAbsence(control,new VacationAndAbsenceService(), sqlControl);
    TrainingAndFurtherEducation trainingAndFurtherEducation = new TrainingAndFurtherEducation(control,new TrainingService());
    PerformanceManagement performanceManagement = new PerformanceManagement(control,new PerformanceService(), sqlControl);




    public  void mainMenu(){
        boolean exit = false;
        while (!exit){
            System.out.println("\n*** Main Menu ***");
            System.out.println("0. Exit");
            System.out.println("1. Management of personnel records");
            System.out.println("2. Working time management");
            System.out.println("3. Payroll administration");
            System.out.println("4. Vacation and absence management:");
            System.out.println("5. Performance management");
            System.out.println("6. Management of training and further education");
            switch (control.intEntry("Enter index of your choice: ")){
                case 0:exit = true;break;
                case 1:managementOfPersonnelRecords();break;
                case 2:managementOfWorkTime(); break;
                case 3:payrollAdministration();break;
                case 4:managementVacationAndAbsence();break;
                case 5:managementPerformance();break;
                case 6:managementOfTrainingAndFurtherEducation(); break;
                default: printDefaultAnswer(6);break;
            }
        }
    }

    private void managementOfPersonnelRecords(){
        boolean exit = false;
        while (!exit){
            System.out.println("\n*** Personnel Records ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Add New Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. List All Employees");
            switch (control.intEntry("Enter index of your choice: ")){
                case 0:exit = true;break;
                case 1:personnelRecords.addNewEmployee();break;
                case 2:personnelRecords.updateEmployee(); break;
                case 3:personnelRecords.deleteEmployee();break;
                case 4:personnelRecords.listAllEmployees();break;
                default:printDefaultAnswer(4);
            }
        }
    }

    private void managementOfWorkTime(){
        boolean exit = false;
        while (!exit){
            System.out.println("\n*** Work Time ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Check In");
            System.out.println("2. Check Out");
            System.out.println("3. Start Break");
            System.out.println("4. End Break");
            System.out.println("5. List Employees Currently Working"); // Added this option
            System.out.println("6. List Employees Currently on Break"); // Added this option
            switch (control.intEntry("Enter index of your choice: ")){
                case 0:exit = true;break;
                case 1:workTime.checkIn();break;
                case 2:workTime.checkOut(); break;
                case 3:workTime.startBreak();break;
                case 4:workTime.endBreak();break;
                case 5:workTime.listCurrentlyWorkingEmployees();break; // Added case for listing employees currently working
                case 6:workTime.listCurrentlyOnBreakEmployees();break; // Added case for listing employees currently on break
                default:printDefaultAnswer(6);
            }
        }
    }

    private void payrollAdministration() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n*** Payroll Administration ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Generate Payroll");
            System.out.println("2. List All Payrolls");
            System.out.println("3. Calculate Tax Deduction");
            switch (control.intEntry("Enter index of your choice: ")) {
                case 0:
                    exit = true;
                    break;
                case 1:administration.generatePayroll();break;
                case 2:administration.listAllPayrolls();break;
                case 3:administration.calculateTaxDeductionForEmployee();break;
                default:printDefaultAnswer(3);
            }
        }
    }

    private void managementVacationAndAbsence() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n*** Vacation & Absence ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Request Holiday");
            System.out.println("2. Check Holiday Balance");
            switch (control.intEntry("Enter index of your choice: ")) {
                case 0:
                    exit = true;
                    break;
                case 1:vacationAndAbsence.requestHoliday();break;
                case 2:vacationAndAbsence.getCurrentHolidays();break;
                default:printDefaultAnswer(2);
            }
        }
    }


    private void managementPerformance() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n*** Performance management ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Add Performance Review");
            System.out.println("2. List Performance Reviews");
            System.out.println("3. Track Goals");
            switch (control.intEntry("Enter index of your choice: ")) {
                case 0:exit = true;break;
                case 1:performanceManagement.addPerformanceReview();break;
                case 2:performanceManagement.listPerformanceReviews();break;
                case 3:performanceManagement.trackGoals(); break;
                default:printDefaultAnswer(3); break;
            }
        }
    }

    private void managementOfTrainingAndFurtherEducation() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n*** Training & Further Education ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Add Training");
            System.out.println("2. Update Training");
            System.out.println("3. Delete Training");
            System.out.println("4. List All Trainings");
            switch (control.intEntry("Enter index of your choice: ")) {
                case 0:
                    exit = true;
                    break;
                case 1:trainingAndFurtherEducation.addTraining();break;
                case 2:trainingAndFurtherEducation.updateTraining();break;
                case 3:trainingAndFurtherEducation.deleteTraining();break;
                case 4:trainingAndFurtherEducation.listAllTrainings();break;
                default: printDefaultAnswer(4);
            }
        }
    }

    private void printDefaultAnswer(int number){
        System.out.println("\n--- Enter only index between 0 and "+ number+" ---\n");
    }
}
