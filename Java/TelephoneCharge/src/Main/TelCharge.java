package Main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.Jsoner;

public class TelCharge {

    public static void createJSON(JSONArray list) {
        try (FileWriter file = new FileWriter("C:\\Mfec\\React\\hello-react\\src\\Data\\telCharge.json")) {
            file.write(Jsoner.prettyPrint(list.toJSONString()));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("telCharge.json has create at C:\\Mfec\\React\\hello-react\\src\\Data");
    }

    public static long valueTelCharge(int resultSecond) {
        int day = (int) TimeUnit.SECONDS.toDays(resultSecond);
        long hours = TimeUnit.SECONDS.toHours(resultSecond) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(resultSecond) - (TimeUnit.SECONDS.toHours(resultSecond) * 60);
        long second = TimeUnit.SECONDS.toSeconds(resultSecond) - (TimeUnit.SECONDS.toMinutes(resultSecond) * 60);
        long telCharge = 0;
        if (minute == 0 && second <= 59 || minute == 1 && second == 0) {
            telCharge = 3;
        }
        if (minute > 1 && second == 0) {
            telCharge = (minute - 1) + 3;
        }
        if (minute > 1 && second > 0) {
            telCharge = (minute - 1) + 3 + 1;
        }
        if (hours > 0 && minute == 0 && second == 0) {
            telCharge = (hours * 60) - 1 + 3;
        }
        if (hours > 0 && minute == 0 && second > 0) {
            telCharge = (hours * 60) - 1 + 3 + 1;
        }
        if (hours > 0 && minute > 0 && second == 0) {
            telCharge = (hours * 60) + (minute - 1) + 3;
        }
        if (hours > 0 && minute > 0 && second > 0) {
            telCharge = (hours * 60) + (minute - 1) + 3 + 1;
        }
        return telCharge;
    }

    public static int timeToSecond(String[] time) {
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        int second = Integer.parseInt(time[2]);
        int call;
        call = second + (60 * minute) + (3600 * hour);
        return call;
    }

    public static void main(String args[]) {
        JSONArray list = new JSONArray();
        try {
            FileInputStream file = new FileInputStream("C:\\Mfec\\Java\\TelephoneCharge\\src\\Log\\Java.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(file));
            String strLine;
            String[] strArray = new String[5];
            while ((strLine = br.readLine()) != null) {
                ArrayList<String> aList = new ArrayList(Arrays.asList(strLine.split("\\|")));
                for (int i = 0; i < aList.size(); i++) {
                    strArray[i] = aList.get(i);
                }
                if (strArray[4].equals("P1")) {
                    String[] StartCall = strArray[1].split(":");
                    String[] EndCall = strArray[2].split(":");
                    int startCall = timeToSecond(StartCall);
                    int endCall = timeToSecond(EndCall);
                    int resultSecond = endCall - startCall;
                    long telCharge = valueTelCharge(resultSecond);
                    JSONObject obj = new JSONObject();
                    obj.put("tel", strArray[3]);
                    obj.put("value", telCharge);
                    list.add(obj);
                }
            }
            createJSON(list);
            file.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
