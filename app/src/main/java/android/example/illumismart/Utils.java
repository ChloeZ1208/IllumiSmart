package android.example.illumismart;

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
}
