package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Controller {

    static final int START = 1;
    static final int RANGE = 1000000;

    public ProgressBar progressBar;
    public Label labelSum;
    public Label labelTime;

    Task task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            return null;
        }

        @Override
        public void run() {
            Calendar calTime, calEnd;
            int n;
            Long lDur;
            SimpleDateFormat outputTime;

            outputTime = new SimpleDateFormat("HH:mm:ss.SSS");
            n = 2;
            calTime = Calendar.getInstance();

            for (int i = START; i <= RANGE; i++) {
                for (int z = 2; z <= i / 2; z++) {
                    if (i % z == 0)
                        break;
                    else if (z == i / 2)
                        n++;
                }
                updateProgress(i / 10000, 100);
                updateTitle(Integer.toString(n));
                calEnd = Calendar.getInstance();
                calEnd.add(Calendar.HOUR, -1);
                lDur = calEnd.getTimeInMillis() - calTime.getTimeInMillis();
                updateMessage(outputTime.format(lDur));
            }
        }
    };

    public void handleButtonActionBeenden(ActionEvent actionEvent) {
        //Platform.exit();
    }

    public void handleButtonActionBerechnen(ActionEvent actionEvent) {
        progressBar.setProgress(0);

        progressBar.progressProperty().bind(task.progressProperty());
        labelSum.textProperty().bind(task.titleProperty());
        labelTime.textProperty().bind(task.messageProperty());
        new Thread(task).start();
    }
}
