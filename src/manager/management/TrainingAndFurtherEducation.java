package manager.management;

import manager.control.InputControl;
import manager.model.Training;
import manager.services.TrainingService;

import java.util.Date;

public class TrainingAndFurtherEducation {
    private InputControl control;
    private TrainingService trainingService;

    public TrainingAndFurtherEducation(InputControl control, TrainingService trainingService){
        this.control=control;
        this.trainingService = trainingService;
    }

    public void addTraining() {
        Training training = new Training();
        training.setTrainingId(control.generateUniqueID());
        training.setTrainingName(control.stringEntry("Enter training name: "));
        training.setStartDate(new Date());
        training.setEndDate(new Date());
        trainingService.addTraining(training);
    }

    public void updateTraining() {
        int trainingId = control.intEntry("Enter Training ID to update: ");
        Training training = new Training();
        training.setTrainingName(control.stringEntry("Enter new training name: "));
        training.setStartDate(new Date());
        training.setEndDate(new Date());
        training.setTrainingId(trainingId);
        trainingService.updateTraining(trainingId, training);
    }

    public void deleteTraining() {
        int trainingId = control.intEntry("Enter Training ID to delete: ");
        trainingService.deleteTraining(trainingId);
    }

    public void listAllTrainings() {
        trainingService.getAllTrainings().forEach(System.out::println);
    }
}
