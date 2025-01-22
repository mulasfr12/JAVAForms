/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dev.view;
import com.dev.dal.sql.DataSourceSingleton;
import com.dev.dal.sql.SqlRepository;
import com.dev.factory.ParserFactory;
import com.dev.factory.UrlConnectionFactory;
import com.dev.utilities.ImageDownloader;
import com.dev.utilities.Item;
import com.dev.utilities.RssFeed;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author salum
 */

public class AdminDashboardJFrame extends javax.swing.JFrame {

    /**
     * Creates new form AdminDashboardJFrame
     */
    public AdminDashboardJFrame() {
        initComponents();
        loadNews();
    }
     private final SqlRepository repository = new SqlRepository();
private void loadNews() {
    String[] columnNames = {"ID", "Title", "Description", "Image Path"}; // Include Image Path
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

    String query = "SELECT NewsID, Title, Description, ThumbnailImage FROM News"; // Ensure ThumbnailImage is selected

    try (Connection connection = DataSourceSingleton.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         var resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            int id = resultSet.getInt("NewsID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String imagePath = resultSet.getString("ThumbnailImage"); // Fetch image path

            tableModel.addRow(new Object[]{id, title, description, imagePath});
        }

        newsTable.setModel(tableModel);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to load news: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
public void reloadNewsTable() {
    DefaultTableModel tableModel = (DefaultTableModel) newsTable.getModel();
    tableModel.setRowCount(0); // Clear existing rows

    String query = "SELECT NewsID, Title, Description, ThumbnailImage FROM News";

    try (Connection connection = DataSourceSingleton.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         var resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            int id = resultSet.getInt("NewsID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String imagePath = resultSet.getString("ThumbnailImage");

            tableModel.addRow(new Object[]{id, title, description, imagePath});
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to reload news: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void handleEditNews() {
    int selectedRow = newsTable.getSelectedRow(); // Get the selected row index

    if (selectedRow == -1) { // No row selected
        JOptionPane.showMessageDialog(this, "Please select a news item to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Fetch data from the selected row
    int newsId = (int) newsTable.getValueAt(selectedRow, 0); // Assuming ID is in column 0
    String title = (String) newsTable.getValueAt(selectedRow, 1); // Assuming Title is in column 1
    String description = (String) newsTable.getValueAt(selectedRow, 2); // Assuming Description is in column 2
    String imagePath = (String) newsTable.getValueAt(selectedRow, 3); // Assuming Image Path is in column 3

    // Open the EditNewsJFrame
    new EditNewsJFrame(newsId, title, description, imagePath).setVisible(true);
}

 private void handleRSSUpload() {
        String rssFeedUrl = "https://feeds.bbci.co.uk/news/entertainment_and_arts/rss.xml";

        try {
            // Fetch and parse the RSS feed
            InputStream inputStream = UrlConnectionFactory.getInputStream(rssFeedUrl);
            RssFeed rssFeed = ParserFactory.parse(inputStream, RssFeed.class);

            // Save parsed data to the database
            saveRSSDataToDatabase(rssFeed.getChannel().getItems());

            // Reload news in the table
            loadNews();

            JOptionPane.showMessageDialog(this, "News uploaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to upload news: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uploadRSSButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        newsTable = new javax.swing.JTable();
        editNewsButton = new javax.swing.JButton();
        createNewsButton = new javax.swing.JButton();
        viewNewsButton = new javax.swing.JButton();
        deleteAllNewsButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Dashboard");

        uploadRSSButton.setText("Upload Latest  News");
        uploadRSSButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadRSSButtonActionPerformed(evt);
            }
        });

        newsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(newsTable);

        editNewsButton.setText("Edit News");
        editNewsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editNewsButtonActionPerformed(evt);
            }
        });

        createNewsButton.setText("Create news");
        createNewsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewsButtonActionPerformed(evt);
            }
        });

        viewNewsButton.setText("View News");
        viewNewsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewNewsButtonActionPerformed(evt);
            }
        });

        deleteAllNewsButton.setText("Erase Everything");
        deleteAllNewsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAllNewsButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uploadRSSButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createNewsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewNewsButton)
                        .addGap(18, 18, 18)
                        .addComponent(deleteAllNewsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editNewsButton)
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uploadRSSButton)
                    .addComponent(editNewsButton)
                    .addComponent(createNewsButton)
                    .addComponent(viewNewsButton)
                    .addComponent(deleteAllNewsButton)
                    .addComponent(refreshButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void uploadRSSButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadRSSButtonActionPerformed
        // TODO add your handling code here:
        uploadRSSButton.addActionListener(e -> handleRSSUpload());
    }//GEN-LAST:event_uploadRSSButtonActionPerformed

    private void editNewsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editNewsButtonActionPerformed
       int selectedRow = newsTable.getSelectedRow();
    if (selectedRow >= 0) {
        int newsId = (int) newsTable.getValueAt(selectedRow, 0);
        String title = (String) newsTable.getValueAt(selectedRow, 1);
        String description = (String) newsTable.getValueAt(selectedRow, 2);
        String imagePath = (String) newsTable.getValueAt(selectedRow, 3);

        EditNewsJFrame editNewsFrame = new EditNewsJFrame(newsId, title, description, imagePath, this); // Pass AdminDashboardJFrame instance
        editNewsFrame.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Please select a news item to edit.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_editNewsButtonActionPerformed

    private void createNewsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewsButtonActionPerformed
        // TODO add your handling code here: 
       new CreateNewsJFrame().setVisible(true);
    }//GEN-LAST:event_createNewsButtonActionPerformed

    private void viewNewsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewNewsButtonActionPerformed
        // TODO add your handling code here:
        int selectedRow = newsTable.getSelectedRow();
    if (selectedRow >= 0) {
        int newsId = (int) newsTable.getValueAt(selectedRow, 0);
        String title = (String) newsTable.getValueAt(selectedRow, 1);
        String description = (String) newsTable.getValueAt(selectedRow, 2);
        String imagePath = (String) newsTable.getValueAt(selectedRow, 3);

        new ViewNewsJFrame(newsId, title, description, imagePath).setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Please select a news item to view.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_viewNewsButtonActionPerformed

    private void deleteAllNewsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAllNewsButtonActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to delete all news? This action cannot be undone.", 
        "Confirm Deletion", 
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("{CALL DeleteAllData}")) {

            // Call the stored procedure to delete all data
            preparedStatement.execute();

            // Delete all images from the assets directory
            File assetsDir = new File("src/main/resources/otherSources/assets");
            if (assetsDir.isDirectory()) {
                for (File file : assetsDir.listFiles()) {
                    if (file.isFile()) {
                        file.delete(); // Delete each file
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "All news and images deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Reload the news table
            loadNews();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete all news: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_deleteAllNewsButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
         try {
        loadNews(); // Reload the news data from the database
        JOptionPane.showMessageDialog(this, "News data refreshed successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to refresh news data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_refreshButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AdminDashboardJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboardJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboardJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboardJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AdminDashboardJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createNewsButton;
    private javax.swing.JButton deleteAllNewsButton;
    private javax.swing.JButton editNewsButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable newsTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton uploadRSSButton;
    private javax.swing.JButton viewNewsButton;
    // End of variables declaration//GEN-END:variables

private void saveRSSDataToDatabase(List<RssFeed.Channel.Item> items) {
    String insertQuery = "IF NOT EXISTS (SELECT 1 FROM News WHERE Title = ?) "
                       + "INSERT INTO News (Title, Description, ThumbnailImage, FullSizeImage, PublicationDate) "
                       + "VALUES (?, ?, ?, ?, ?)";

    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", java.util.Locale.ENGLISH); // Adjust to match RSS format

    try (Connection connection = DataSourceSingleton.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

        for (RssFeed.Channel.Item item : items) {
            preparedStatement.setString(1, item.getTitle()); // Check for duplicates
            preparedStatement.setString(2, item.getTitle()); // Title
            preparedStatement.setString(3, item.getDescription()); // Description
            preparedStatement.setString(4, item.getMediaThumbnail() != null ? item.getMediaThumbnail().getUrl() : null); // Thumbnail
            preparedStatement.setString(5, item.getMediaContent() != null ? item.getMediaContent().getUrl() : null); // Full-size image

            // Parse the publication date
            String pubDate = item.getPublicationDate();
            Timestamp publicationTimestamp = null;
            if (pubDate != null && !pubDate.isEmpty()) {
                try {
                    Date parsedDate = dateFormat.parse(pubDate); // Parse the date
                    publicationTimestamp = new Timestamp(parsedDate.getTime()); // Convert to Timestamp
                } catch (Exception e) {
                    e.printStackTrace(); // Log parsing errors
                }
            }
            preparedStatement.setTimestamp(6, publicationTimestamp);

            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to save RSS data to the database: " + e.getMessage());
    }
}



private java.sql.Timestamp parsePublicationDate(String pubDate) {
    try {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        Date date = format.parse(pubDate);
        return new java.sql.Timestamp(date.getTime());
    } catch (Exception e) {
        e.printStackTrace();
        return null; // Handle invalid date formats gracefully
    }
}

}

