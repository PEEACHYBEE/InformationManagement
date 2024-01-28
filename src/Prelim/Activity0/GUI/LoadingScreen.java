package Prelim.Activity0.GUI;

/**
 *
 * @author ENCARNACION, Ma. Earl Freskkie
 * @since January 24, 2024
 */

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JDialog {
    private JProgressBar progressBar;

    public LoadingScreen(Frame owner) {
        super(owner, "Loading...", ModalityType.APPLICATION_MODAL);
        progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true); // For an indeterminate progress bar
        add(progressBar, BorderLayout.CENTER);
        setSize(300, 100);
        setLocationRelativeTo(owner);
    }

    public void startLoading() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulate a long-running task
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50); // Sleep for 50 milliseconds
                    progressBar.setValue(i); // Update progress
                }
                return null;
            }

            @Override
            protected void done() {
                // When the task is done, dispose of the loading screen
                dispose();
            }
        };
        worker.execute();
        setVisible(true); // Make the dialog visible
    }
}
