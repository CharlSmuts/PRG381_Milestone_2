/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wellness.controller;

import wellness.model.FeedbackModel;
import wellness.view.FeedbackPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import wellness.model.DBConnection;
import java.util.*;



/**
 *
 * @author Koekie
 */
public class FeedbackController {
    private final FeedbackPanel view;
    private final DBConnection db;

    //Constructor
    public FeedbackController(FeedbackPanel view, DBConnection db){
        this.view = view;
        this.db = db;
        
        //adds an event listeners to buttons
        view.getSubmitButton().addActionListener(new SubmitButtonListener());
        view.getUpdateButton().addActionListener(new UpdateButtonListener());
        view.getDeleteButton().addActionListener(new DeleteButtonListener());
        
        populateTable();
    }
    
    // populates table from database
    public void populateTable()
    {
        // First clear the table
        view.getTableModel().setRowCount(0);
        
        String rating, feedbackID, studentNr, comments;
        
        ArrayList<String[]> feedbackList = db.viewFeedback();
        for (String[] strings : feedbackList) {
            feedbackID = strings[0];
            studentNr = strings[1];
            comments = strings[2];
            rating = strings[3];
            
            view.getTableModel().addRow(new Object[]{feedbackID, studentNr, rating, comments});
        }   
    }
    
    //even listeners in question, class that implements ActionListener interface
    class SubmitButtonListener implements ActionListener {  
        @Override   //button click action
        public void actionPerformed(ActionEvent e) {    
            String studentNr = view.getStudentNumber();
            String comment = view.getComment();
            int rating = view.getRating();
            
            // Validation of inputs:
            if (Objects.equals(studentNr, "")){
                view.throwWarning("Student Number field may not be empty", "Submission Error");
                return;
            }else if(studentNr.length() > 10){
                view.throwWarning("Student Number may not be larger than 10", "Submission Error");
                return;
            }else if(comment.length() > 100){
                view.throwWarning("Comments may not be larger than 100 characters", "Submission Error");
                return;
            }else if(Objects.equals(comment, "")){
                    view.throwWarning("Comments field may not be empty", "Submission Error");
                    return;
            }
            
            
            // Clearing the fields after submission:
            view.clearSubmissionFields();
            
            //Creation of feedback object and subsequent storage:
            FeedbackModel feedback = new FeedbackModel(studentNr, comment, rating);            
            
            // Add to database using inputs
            if(db.addDataFeedback(feedback.getStudentNr(), feedback.getComment(), feedback.getRating())){
                view.throwSuccess("Successfully added", "Feedback Submission"); 
                populateTable();
            }
        }
    }
 
    class UpdateButtonListener implements ActionListener {  
        @Override   //button click action
        public void actionPerformed(ActionEvent e) {  
            String selectedID = view.getSelectedID();
            String studentNr = view.getStudentNumber();
            String comment = view.getComment();
            int rating = view.getRating();
            
            // Validation of inputs:
            if (Objects.equals(selectedID, "")){  
                view.throwWarning("Valid row must be selected", "Update Error");
                return;
            }else if(Objects.equals(comment, "")){
                view.throwWarning("Comments field may not be empty", "Update Error");
                return;
            }else if(comment.length() > 100){
                view.throwWarning("Comments may not be larger than 100 characters", "Update Error");
                return;
            }else if(Objects.equals(studentNr, "")){
                view.throwWarning("Student Number field may not be empty", "Update Error");
            }else if(studentNr.length() > 10){
                view.throwWarning("Student Number may not be larger than 10", "Update Error");
                return;
            }
            
            // Clearing the fields after submission:
            view.clearSubmissionFields();
            
            //Creation of feedback object and subsequent storage:
            FeedbackModel feedback = new FeedbackModel(studentNr, comment, rating); 
            
            // Update in database:
            if(db.updateFeedback(feedback.getStudentNr(), feedback.getComment(), feedback.getRating(), Integer.valueOf(selectedID))){
                view.throwSuccess("Successfully updated", "Feedback Update"); 
                populateTable();
            }
        }
    }
    
    class DeleteButtonListener implements ActionListener {  
        @Override   //button click action
        public void actionPerformed(ActionEvent e) {    
            String selectedID = view.getSelectedID();
            
            // Validation of inputs:
            if (Objects.equals(selectedID, "")){
                view.throwWarning("Invalid table row selected!", "Selection Error"); 
                return;
            }
            // Clearing the fields after submission:
            view.clearSubmissionFields();
            
            //Delete from database using selectedID
            if(db.deleteFeedback(Integer.valueOf(selectedID))){
                view.throwSuccess("Successfully deleted", "Deletion"); 
                populateTable();
            }
        }
    }
}
