package android.example.illumismart;

public class Utils {
    // timestamp reformatting: 20210226236 into 2021-02-26, 12:36:03
    public String getParsedTimestamp(String timestamp) {
        return timestamp.substring(0, 4) + "-" + timestamp.substring(4, 6) + "-" +
                timestamp.substring(6, 8) + ", " + timestamp.substring(8, 10) + ":" +
                timestamp.substring(10, 12) + ":" + timestamp.substring(12, 14);
    }
}
