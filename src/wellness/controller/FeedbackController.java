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
    private FeedbackPanel view;
    
    public FeedbackController(FeedbackPanel view){
        this.view = view;
        
        view.getSubmitButton().addActionListener(new SubmitButtonListener());   //adds an even listener to button
    }
    
    class SubmitButtonListener implements ActionListener {  //even listener in question
        public void actionPerformed(ActionEvent e) {    //button click action
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
