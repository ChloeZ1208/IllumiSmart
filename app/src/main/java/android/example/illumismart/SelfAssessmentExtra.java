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
}
