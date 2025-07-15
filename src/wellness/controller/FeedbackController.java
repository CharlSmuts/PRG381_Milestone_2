/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wellness.controller;

import wellness.model.FeedbackModel;
import wellness.view.FeedbackPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Koekie
 */
public class FeedbackController {
    private final FeedbackPanel view;
    
    //Constructor
    public FeedbackController(FeedbackPanel view){
        this.view = view;
        
        //adds an even listener to button
        view.getSubmitButton().addActionListener(new SubmitButtonListener());   
    }
    
    //even listener in question, class that implements ActionListener interface
    class SubmitButtonListener implements ActionListener {  
        @Override   //button click action
        public void actionPerformed(ActionEvent e) {    
            String studentNr = view.getStudentNumber();
            String comment = view.getComment();
            int rating = view.getRating();
            
            // Validation of inputs:
            
            // Clearing the fields after submission:
            view.clearSubmissionFields();
            
            //Creation of feedback object and subsequent storage:
            FeedbackModel feedback = new FeedbackModel(studentNr, comment, rating);
            
            //Testing purposes:
            System.out.println("Feedback submitted:");
            System.out.println("Student: " + feedback.getStudentNr());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("Comment: " + feedback.getComment());
        }
    }
}
