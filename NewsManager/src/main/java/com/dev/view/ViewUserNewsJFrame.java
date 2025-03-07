/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dev.view;

import com.dev.dal.sql.SqlRepository;
import com.dev.model.Comment;
import com.dev.model.News;
import com.dev.utilities.UserSession;
import java.awt.Image;
import java.io.File;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author salum
 */
public class ViewUserNewsJFrame extends javax.swing.JFrame {

     private static ViewUserNewsJFrame instance;
    private final SqlRepository repository = new SqlRepository();
    private  News news;
   private String currentUsername;

public ViewUserNewsJFrame(News news) {
        this.news = news;
        this.currentUsername = UserSession.getInstance().getUsername(); // Get the logged-in username
        initComponents();
        setJMenuBar(createMenuBar());
        populateFields();
        loadComments();
    }
private ViewUserNewsJFrame() {
        initComponents();
        setJMenuBar(createMenuBar());
    }
public static ViewUserNewsJFrame getInstance() {
        if (instance == null) {
            instance = new ViewUserNewsJFrame();
        }
        return instance;
    }
public void setNews(News news) {
        this.news = news;
        this.currentUsername = UserSession.getInstance().getUsername();
        populateFields();
        loadComments();
    }
 private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Home Menu
        JMenu homeMenu = new JMenu("Home");
        JMenuItem dashboardItem = new JMenuItem("Go to Dashboard");
        dashboardItem.addActionListener(e -> {
            this.dispose();
            UserDashboardJFrame.getInstance().setVisible(true);
        });
        homeMenu.add(dashboardItem);

        // Account Menu
        JMenu accountMenu = new JMenu("Account");
        JMenuItem logoutItem = new JMenuItem("Log Out");
        logoutItem.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out and exit the application?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        accountMenu.add(logoutItem);

        menuBar.add(homeMenu);
        menuBar.add(accountMenu);

        return menuBar;
    }

private void populateFields() {
    titleField.setText(news.getTitle());
    descriptionArea.setText(news.getDescription());
    userNameField.setText(currentUsername); // Set the logged-in user's username

    // Display the image
    if (news.getThumbnailImage() != null && !news.getThumbnailImage().isEmpty()) {
        // Correctly construct the path for images in "otherSources/assets"
        String relativePath = "src/main/resources/otherSources/" + news.getThumbnailImage();
        File imageFile = new File(relativePath);

        if (imageFile.exists()) {
            // Image found, load it
            ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
            Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setText(""); // Clear any "No Image Found!" text
        } else {
            System.out.println("Image file not found: " + imageFile.getAbsolutePath());
            // Use a placeholder image if the thumbnail is missing
            setPlaceholderImage();
        }
    } else {
        // No thumbnail provided
        setPlaceholderImage();
    }
}

// Helper method to set the placeholder image
private void setPlaceholderImage() {
    String placeholderPath = "src/main/resources/otherSources/assets/placeholder.png";
    File placeholderFile = new File(placeholderPath);

    if (placeholderFile.exists()) {
        ImageIcon placeholderIcon = new ImageIcon(placeholderFile.getAbsolutePath());
        Image scaledPlaceholder = placeholderIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledPlaceholder));
        imageLabel.setText(""); // Clear "No Image Found!" if placeholder exists
    } else {
        System.out.println("Placeholder image not found at: " + placeholderPath);
        imageLabel.setIcon(null);
        imageLabel.setText("No Image Found!");
    }
}

/**
 * Sets a placeholder image when the actual image is not found or invalid.
 */

   private void saveComment() {
    String commentText = commentArea.getText();

    if (commentText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Comment cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String username = UserSession.getInstance().getUsername(); // Get the logged-in user's username
    Comment comment = new Comment(0, news.getNewsID(), commentText, username, new Date()); // Pass 0 as the placeholder CommentID

    try {
        repository.createComment(comment); // Save comment using the repository
        JOptionPane.showMessageDialog(this, "Comment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        commentArea.setText(""); // Clear the comment field after saving
        
        // Reload comments to reflect the newly added comment
        loadComments();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to save comment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        imageLabel = new javax.swing.JLabel();
        titleField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        userNameField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        commentArea = new javax.swing.JTextArea();
        saveCommentButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        commentsTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        descriptionArea.setColumns(20);
        descriptionArea.setRows(5);
        jScrollPane1.setViewportView(descriptionArea);

        commentArea.setColumns(20);
        commentArea.setRows(5);
        jScrollPane2.setViewportView(commentArea);

        saveCommentButton.setText("Save Comment");
        saveCommentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCommentButtonActionPerformed(evt);
            }
        });

        commentsTextArea.setColumns(20);
        commentsTextArea.setRows(5);
        jScrollPane3.setViewportView(commentsTextArea);

        jLabel1.setText("Comments");

        jLabel2.setText("Add comment");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(saveCommentButton))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveCommentButton)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveCommentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCommentButtonActionPerformed
        // TODO add your handling code here:
        saveComment();
    }//GEN-LAST:event_saveCommentButtonActionPerformed
private void loadComments() {
    try {
        // Fetch all comments for the specific news
        List<Comment> comments = repository.selectCommentsByNewsID(news.getNewsID());

        // Clear the text area
        commentsTextArea.setText("");

        // Append each comment to the text area
        for (Comment comment : comments) {
            commentsTextArea.append("User: " + comment.getUserName() + "\n");
            commentsTextArea.append("Comment: " + comment.getCommentText() + "\n");
            commentsTextArea.append("Date: " + comment.getCommentDate() + "\n");
            commentsTextArea.append("---------------------------\n");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to load comments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * @param args the command line arguments
     */
  public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // Simulate a logged-in user
            UserSession.getInstance().setUsername("testUser");

            // Create a dummy News object for testing
            News dummyNews = new News(1, "Sample Title", "Sample Description", "assets/sample_image.jpg", null, null, null);

            // Open the view
            ViewUserNewsJFrame.getInstance().setNews(dummyNews);
            ViewUserNewsJFrame.getInstance().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea commentArea;
    private javax.swing.JTextArea commentsTextArea;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton saveCommentButton;
    private javax.swing.JTextField titleField;
    private javax.swing.JTextField userNameField;
    // End of variables declaration//GEN-END:variables
}
