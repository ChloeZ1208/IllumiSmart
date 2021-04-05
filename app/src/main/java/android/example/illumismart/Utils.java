package android.example.illumismart;

import android.content.Context;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Utils {
    // timestamp reformatting: 20210226236 into 2021-02-26, 12:36:03
    public String getParsedTimestamp(String timestamp) {
        return timestamp.substring(0, 4) + "-" + timestamp.substring(4, 6) + "-" +
                timestamp.substring(6, 8) + ", " + timestamp.substring(8, 10) + ":" +
                timestamp.substring(10, 12) + ":" + timestamp.substring(12, 14);
    }
    // Get timestamp with specified timezone
    public String getSpecifiedTimestamp() {
        SimpleDateFormat canadaFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        TimeZone canada = TimeZone.getTimeZone("EST");
        canadaFormat.setTimeZone(canada);
        return canadaFormat.format(new Date());
    }


    public String serializeSelfAssessmentIssues(ArrayList<String> issues) {
        if (issues.size() == 0) {
            return null;
        } else if (issues.size() == 1) {
            return issues.get(0);
        } else {
            String serializedIssues = issues.stream().collect(Collectors.joining("/"));
            return serializedIssues;
        }
    }

    public String [] deserializeSelfAssessmentIssues(String issues) {
        String [] strArr = issues.split("/");
        return strArr;
    }

    public SelfAssessmentExtra getOnBackPressedExtra(SelfAssessmentExtra extra) {
        if (extra.getIssues().size() == 0) {
            return extra;
        }
        int qid = extra.getQuestionId();
        ArrayList<String> arr = extra.getIssues();
        String [] s = arr.get(arr.size()-1).split("_");
        int lastSuggestionQid = Integer.parseInt(s[1]);
        if (lastSuggestionQid != (qid - 1)) {
            return extra;
        }
        arr.remove(arr.size()-1);
        SelfAssessmentExtra newExtra = new SelfAssessmentExtra(qid, arr, null);
        return newExtra;
    }

    public int getKeywordStringId(String keyword) {
        if (keyword.equals("question_2_suggest_a_keyword")) {
            return R.string.question2suggest_a_keyword;
        }
        if (keyword.equals("question_4_suggest_b_keyword")) {
            return R.string.question4suggest_b_keyword;
        }
        if (keyword.equals("question_6_suggest_b_keyword")) {
            return R.string.question6suggest_b_keyword;
        }
        if (keyword.equals("question_6_suggest_c_keyword")) {
            return R.string.question6suggest_c_keyword;
        }
        if (keyword.equals("question_7_suggest_a_keyword")) {
            return R.string.question7suggest_a_keyword;
        }
        if (keyword.equals("question_8_suggest_a_keyword")) {
            return R.string.question8suggest_a_keyword;
        }
        if (keyword.equals("question_9_suggest_a_keyword")) {
            return R.string.question9suggest_a_keyword;
        }
        if (keyword.equals("question_9_suggest_b_keyword")) {
            return R.string.question9suggest_b_keyword;
        }
        if (keyword.equals("question_9_suggest_c_keyword")) {
            return R.string.question9suggest_c_keyword;
        }
        if (keyword.equals("question_10_suggest_b_keyword")) {
            return R.string.question10suggest_b_keyword;
        }
        return -1;
    }

    public int getSuggestStringId(String keyword) {
        if (keyword.equals("question_2_suggest_a_keyword")) {
            return R.string.question2suggest_a;
        }
        if (keyword.equals("question_4_suggest_b_keyword")) {
            return R.string.question4suggest_b;
        }
        if (keyword.equals("question_6_suggest_b_keyword")) {
            return R.string.question6suggest_b;
        }
        if (keyword.equals("question_6_suggest_c_keyword")) {
            return R.string.question6suggest_c;
        }
        if (keyword.equals("question_7_suggest_a_keyword")) {
            return R.string.question7suggest_a;
        }
        if (keyword.equals("question_8_suggest_a_keyword")) {
            return R.string.question8suggest_a;
        }
        if (keyword.equals("question_9_suggest_a_keyword")) {
            return R.string.question9suggest_a;
        }
        if (keyword.equals("question_9_suggest_b_keyword")) {
            return R.string.question9suggest_b;
        }
        if (keyword.equals("question_9_suggest_c_keyword")) {
            return R.string.question9suggest_c;
        }
        if (keyword.equals("question_10_suggest_b_keyword")) {
            return R.string.question10suggest_b;
        }
        return -1;
    }

    public void openURL(String url, int color, Context ctx) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabColorSchemeParams params = new CustomTabColorSchemeParams.Builder()
                .setToolbarColor(color)
                .build();
        builder.setDefaultColorSchemeParams(params);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(ctx, Uri.parse(url));
    }
}
