package manager.management;

import manager.control.InputControl;
import manager.control.SqlControl;
import manager.model.Training;
import manager.services.TrainingService;
import java.util.Date;

public class TrainingAndFurtherEducation {
    private InputControl control;
    private TrainingService trainingService;
    private SqlControl sqlControl;

    public TrainingAndFurtherEducation(InputControl control, TrainingService trainingService, SqlControl sqlControl){
        this.control=control;
        this.trainingService = trainingService;
        this.sqlControl = sqlControl;
    }

    public void addTraining() {
        Training training = new Training();
        training.setTrainingName(control.stringEntry("Enter training name: "));
        training.setStartDate(new Date());
        training.setEndDate(new Date());
        trainingService.addTraining(training);
        System.out.println("Employee added with ID: " + training.getTrainingId());
    }

    public void updateTraining() {
        Integer trainingId = getValidTrainingId("update");
        if (trainingId == null){return;}

        Training training = new Training();
        training.setTrainingName(control.stringEntry("Enter new training name: "));
        training.setStartDate(new Date());
        training.setEndDate(new Date());
        training.setTrainingId(trainingId);
        trainingService.updateTraining(trainingId, training);
    }

    public void deleteTraining() {
        Integer trainingId = getValidTrainingId("delete");
        if (trainingId == null){return;}

        trainingService.deleteTraining(trainingId);
    }

    public void listAllTrainings() {
        trainingService.getAllTrainings().forEach(System.out::println);
    }

    public void addEmployeeToTraining() {
        Integer employeeId = getValidEmployeeId("add to training");
        if (employeeId == null){return;}

        Integer trainingId = getValidTrainingId("");
        if (trainingId == null){return;}

        trainingService.addEmployeeToTraining(employeeId, trainingId);
    }

    public void removeEmployeeFromTraining() {
        Integer employeeId = getValidEmployeeId("remove from training");
        if (employeeId == null){return;}

        Integer trainingId = getValidTrainingId("remove from");
        if (trainingId == null){return;}

        trainingService.removeEmployeeFromTraining(employeeId, trainingId);
    }

    public void listEmployeesForTraining() {
        Integer trainingId = getValidTrainingId("list employees");
        if (trainingId == null){return;}

        trainingService.getEmployeesForTraining(trainingId).forEach(System.out::println);
    }

    private Integer getValidTrainingId(String prompt){
        if (!sqlControl.anyTrainingExists()){ System.out.println("No Training in the Database");return null; }
        int trainingId = control.intEntry("Enter Training ID to " + prompt + ": ");
        if (!sqlControl.trainingExists(trainingId)){System.out.println("Training with ID: " + trainingId + " does not exist.");return null;}
        return trainingId;
    }

    private Integer getValidEmployeeId(String prompt){
        if (!sqlControl.anyEmployeeExists()){ System.out.println("No Employees in the Database");return null; }
        int employeeId = control.intEntry("Enter Employee ID to " + prompt + ": ");
        if (!sqlControl.employeeExists(employeeId)){System.out.println("Employee with ID: " + employeeId + " does not exist.");return null;}
        return employeeId;
    }
}
