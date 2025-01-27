/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dev.view;

import com.dev.dal.sql.DataSourceSingleton;
import com.dev.dal.sql.SqlRepository;
import com.dev.model.News;
import com.dev.utilities.ImageDownloader;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author salum
 */

public class EditNewsJFrame extends javax.swing.JFrame {

    private boolean isAdmin; // Indicates if the current user is an admin
    private int newsId; // News ID to be edited
    private AdminDashboardJFrame owner; // Reference to the Admin Dashboard for table refresh
    private final SqlRepository repository = new SqlRepository(); // Repository for database operations

    // Constructor for Admin usage with reference to the Admin Dashboard
    public EditNewsJFrame(int newsId, String title, String description, String imagePath, AdminDashboardJFrame owner) {
        this.newsId = newsId;
        this.owner = owner;
        this.isAdmin = true;
        initComponents();
        setJMenuBar(createMenuBar());
        populateFields(title, description, imagePath);
    }
   // Constructor for general usage (e.g., user role)
    public EditNewsJFrame(int newsId, String title, String description, String imagePath) {
        this.newsId = newsId;
        this.isAdmin = false;
        initComponents();
        setJMenuBar(createMenuBar());
        populateFields(title, description, imagePath);
    }

    /**
     * Populates the fields with the existing news data.
     */
    private void populateFields(String title, String description, String imagePath) {
        titleField.setText(title);
        descriptionArea.setText(description);

        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.putClientProperty("imagePath", imagePath); // Save the image path for later use
        } else {
            imageLabel.setText("No Image Available");
        }
    }
   private void saveNewsUpdates() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        String imagePath = (String) imageLabel.getClientProperty("imagePath");

        if (title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        News updatedNews = new News(newsId, title, description, imagePath, null, null, null);

        try {
            repository.updateNews(newsId, updatedNews);
            JOptionPane.showMessageDialog(this, "News updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            if (owner != null) {
                owner.reloadNewsTable(); // Refresh the Admin Dashboard table
            }

            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update news: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
   private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = "news_" + newsId + "_" + selectedFile.getName();

            try {
                String imagePath = ImageDownloader.downloadImage(selectedFile.toURI().toString(), fileName);
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
                imageLabel.putClientProperty("imagePath", imagePath);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to upload image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Deletes the current news item from the database.
     */
    private void deleteNews() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this news item?",
                "Delete Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                repository.deleteNews(newsId);
                JOptionPane.showMessageDialog(this, "News deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                if (owner != null) {
                    owner.reloadNewsTable(); // Refresh the Admin Dashboard table
                }

                this.dispose();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to delete news: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    // Home Menu
    JMenu homeMenu = new JMenu("Home");
    JMenuItem dashboardItem = new JMenuItem("Go to Dashboard");
    dashboardItem.addActionListener(e -> {
        this.dispose();
        if (isAdmin) {
              AdminDashboardJFrame.getInstance().setVisible(true);
        } else {
            new UserDashboardJFrame().setVisible(true);
        }
    });
    homeMenu.add(dashboardItem);

    // Account Menu
    JMenu accountMenu = new JMenu("Exit App");
    JMenuItem logoutItem = new JMenuItem("Close App");
    logoutItem.addActionListener(e -> {
        int confirmation = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to log out and exit the application?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            System.exit(0); // Exit the application
        }
    });
    accountMenu.add(logoutItem);

    // Add menus to the menu bar
    menuBar.add(homeMenu);
    menuBar.add(accountMenu);

    return menuBar;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        descriptionArea = new java.awt.TextArea();
        saveButton = new javax.swing.JButton();
        titleField = new java.awt.TextField();
        jButton1 = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("News Title");

        jLabel2.setText("Description");

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        titleField.setText("textField1");

        jButton1.setText("Upload Image");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        imageLabel.setText("Image ");

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(titleField, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descriptionArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(deleteButton)
                                .addGap(32, 32, 32)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageLabel)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionArea, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(deleteButton))
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        saveNewsUpdates();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        uploadImage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        deleteNews();
    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditNewsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditNewsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditNewsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditNewsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
       SwingUtilities.invokeLater(() -> new EditNewsJFrame(1, "Sample Title", "Sample Description", null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteButton;
    private java.awt.TextArea descriptionArea;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton saveButton;
    private java.awt.TextField titleField;
    // End of variables declaration//GEN-END:variables
}
