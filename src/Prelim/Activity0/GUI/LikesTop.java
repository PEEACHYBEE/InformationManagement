package Prelim.Activity0.GUI;

import Prelim.Activity0.Classes.GeneralLikeDislike;
import javax.swing.*;

/**
 *
 * @author Encarnacion, Ma. Earl Freskkie
 *         Aquino, Jan Dolby
 * @since January 21-27, 2024
 */
public class LikesTop extends javax.swing.JFrame {
    private LoadingScreen loadingScreen;

    /**
     * Creates new form LikesTop
     */
    public LikesTop() {
        initComponents();
        setLocationRelativeTo(null);
        loadingScreen = new LoadingScreen(this);
    }

    // <editor-fold defaultstate="collapsed" desc="More Codes">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        viewLikesButton = new javax.swing.JToggleButton();
        viewDislikesButton = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        backButton = new javax.swing.JToggleButton();
        instructionsLabel = new javax.swing.JLabel();
        SearchBar = new javax.swing.JTextField();
        resultsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultsTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Viral Youtube Videos Analyzer");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(245, 235, 224));

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(78, 80, 82));
        titleLabel.setText("Most Liked and Disliked Video");

        viewLikesButton.setText("View Likes");
        viewLikesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewLikesButtonActionPerformed(evt);
            }
        });

        viewDislikesButton.setText("View Dislikes");
        viewDislikesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDislikesButtonActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(214, 204, 194));

        jLabel2.setForeground(new java.awt.Color(78, 80, 82));
        jLabel2.setText("© Team Ba 2024");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(323, 323, 323)
                                .addComponent(jLabel2)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addContainerGap())
        );

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        instructionsLabel.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        instructionsLabel.setForeground(new java.awt.Color(78, 80, 82));
        instructionsLabel.setText("Search viral videos tags and it will display based on the most liked or disliked");

        SearchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBarActionPerformed(evt);
            }
        });

        resultsPanel.setBackground(new java.awt.Color(227, 213, 202));

        resultsTextArea.setColumns(20);
        resultsTextArea.setRows(5);
        jScrollPane1.setViewportView(resultsTextArea);

        javax.swing.GroupLayout resultsPanelLayout = new javax.swing.GroupLayout(resultsPanel);
        resultsPanel.setLayout(resultsPanelLayout);
        resultsPanelLayout.setHorizontalGroup(
                resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(resultsPanelLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(16, Short.MAX_VALUE))
        );
        resultsPanelLayout.setVerticalGroup(
                resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(resultsPanelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SearchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(167, 167, 167))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(backButton))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(233, 233, 233)
                                                .addComponent(viewLikesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(76, 76, 76)
                                                .addComponent(viewDislikesButton))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(125, 125, 125)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(instructionsLabel)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(102, 102, 102)
                                                .addComponent(resultsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(instructionsLabel)
                                .addGap(18, 18, 18)
                                .addComponent(SearchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(viewLikesButton)
                                        .addComponent(viewDislikesButton))
                                .addGap(18, 18, 18)
                                .addComponent(resultsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addComponent(backButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void viewLikesButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Start the loading screen
        loadingScreen.startLoading();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Perform the long-running task here
                String filepath = "data.csv";
                String tag = SearchBar.getText();

                GeneralLikeDislike generalLikeDislike = new GeneralLikeDislike(filepath);
                generalLikeDislike.viewMostLikes(tag);
                generalLikeDislike.createMostLikesCSVFile("LikesOutput", tag); // If needed
                String summary = generalLikeDislike.getLikesSummary(tag);
                resultsTextArea.setText(summary + "\nTo see the full details, open the file \"LikesOutput_"+tag+".csv\".");
                return null;
            }
            @Override
            protected void done() {
                loadingScreen.dispose();
            }
        };

        worker.execute();
    }

    private void viewDislikesButtonActionPerformed(java.awt.event.ActionEvent evt) {
        loadingScreen.startLoading();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                String filepath = "data.csv";
                String tag = SearchBar.getText();

                GeneralLikeDislike generalLikeDislike = new GeneralLikeDislike(filepath);
                generalLikeDislike.viewMostDislikes(tag);
                generalLikeDislike.createMostDislikesCSVFile("DislikesOutput", tag);
                String summary = generalLikeDislike.getDislikesSummary(tag);
                resultsTextArea.setText(summary + "\nTo see the full details, open the file \"DislikesOutput_"+tag+".csv\".");
                return null;
            }
            @Override
            protected void done() {
                loadingScreen.dispose();
            }
        };

        worker.execute();
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }

    private void SearchBarActionPerformed(java.awt.event.ActionEvent evt) {
    }


    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LikesTop().setVisible(true);
            }
        });
    }

    private javax.swing.JTextField SearchBar;
    private javax.swing.JToggleButton backButton;
    private javax.swing.JLabel instructionsLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel resultsPanel;
    private javax.swing.JTextArea resultsTextArea;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JToggleButton viewDislikesButton;
    private javax.swing.JToggleButton viewLikesButton;
    // End of variables declaration
}
