package manager.services;

import manager.model.Training;

import java.util.ArrayList;
import java.util.List;

public class TrainingService {
    private List<Training> trainings = new ArrayList<>();

    public void addTraining(Training training) {
        trainings.add(training);
    }

    public List<Training> getAllTrainings() {
        return trainings;
    }

    public void updateTraining(int trainingId, Training training) {
        Training existingTraining = trainings.stream()
                .filter(t -> t.getTrainingId() == trainingId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));
        trainings.remove(existingTraining);
        trainings.add(training);
    }

    public void deleteTraining(int trainingId) {
        Training training = trainings.stream()
                .filter(t -> t.getTrainingId() == trainingId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));
        trainings.remove(training);
    }
}
