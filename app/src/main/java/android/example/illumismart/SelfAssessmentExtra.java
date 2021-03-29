package android.example.illumismart;

import java.io.Serializable;
import java.util.ArrayList;

public class SelfAssessmentExtra implements Serializable {
    // id of current question; update handled in each question activity
    private int questionId;
    // Issues to be stored
    private ArrayList<String> issues;
    // id of suggestion text; update handled in each question activity before go to suggestion page
    private String suggestionId;

    public SelfAssessmentExtra(int questionId, ArrayList<String> issues, String suggestionId) {
        this.questionId = questionId;
        this.issues = issues;
        this.suggestionId = suggestionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setIssues(String keyword) {
        this.issues.add(keyword);
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public int getQuestionId() {
        return this.questionId;
    }

    public ArrayList<String> getIssues() {
        return this.issues;
    }

    public String getSuggestionId() {
        return this.suggestionId;
    }
}
