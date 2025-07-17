/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wellness.model;

/**
 *
 * @author Koekie
 */
public class FeedbackModel {
    private String studentNr;
    private String comment;
    private int rating;
    
    // Constructor
    public FeedbackModel(String studentNr, String comment, int rating){
        this.studentNr = studentNr;
        this.comment = comment;
        this.rating = rating;
    }
    
    // Setters
    public void setStudentNr(String studentNr){
        this.studentNr = studentNr;
    }
    
    public void setComment(String comment){
        this.comment = comment;
    }
    
    public void setRating(int rating){
        this.rating = rating;
    }
    
    // Getters
    public String getStudentNr(){
        return studentNr;
    }
    
    public String getComment(){
        return comment;
    }
    
    public int getRating(){
        return rating;
    }
}
