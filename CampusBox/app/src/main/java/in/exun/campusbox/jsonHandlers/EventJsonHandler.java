package in.exun.campusbox.jsonHandlers;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Anurag145 on 4/8/2017.
 */

public class EventJsonHandler {


    private static final String TAG = "EventJsonHandler";
    private int n = 0, offset, limit;
    private JSONArray data;
    private boolean allowPagination = true;

    public EventJsonHandler(JSONArray data, JSONObject metadata) {

        this.data = data;

        if (metadata == null) {
            Log.d("EventJsonHandler: ", "No pagination req");
            allowPagination = false;
        } else {
            try {
                limit = Integer.parseInt(metadata.getString("limit"));
                offset = metadata.getInt("offset");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        n = data.length();
    }

    public String getSingleData(int pos) {
        try {
            JSONArray array = new JSONArray();
            return array.put(data.getJSONObject(pos)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getData() {
        return data;
    }

    public String urlPagination() {
        try {
            return "limit=" + limit + "&offset=" + offset;
        } catch (Exception e) {
            e.printStackTrace();
            return "limit=6&offset=0";
        }
    }

    public String getDate(int position) throws ParseException {
        try {

            JSONObject jsonObject1 = data.getJSONObject(position);
            jsonObject1 = jsonObject1.getJSONObject("timings");
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(new StringTokenizer(jsonObject1.getJSONObject("from")
                    .getString("date"), "T").nextToken());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String from = new SimpleDateFormat("E, dd MMM").format(calendar.getTime());
            String fromTime = getTime(position,"from");
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(new StringTokenizer(jsonObject1.getJSONObject("to")
                    .getString("date"), "T").nextToken());
            calendar.setTime(date);
            String to = new SimpleDateFormat("E, dd MMM").format(calendar.getTime());
            String toTime = getTime(position,"to");
            if (!from.equals(to))
                from = from + " " + fromTime + " - " + to + " " + toTime;
            else if (!fromTime.equals(toTime))
                from = from + " " + fromTime + " - " + toTime;
            else
                from = from + " " + fromTime;
            return from;
        } catch (Exception e) {
            Log.e("date", e.toString());
            return null;
        }

    }

    public String getTime(int position, String tag){
        try {
            JSONObject jsonObject1 = data.getJSONObject(position);
            jsonObject1 = jsonObject1.getJSONObject("timings").getJSONObject(tag);
            return jsonObject1.getString("time");
        } catch (JSONException e) {
            e.printStackTrace();
            return "12:00";
        }
    }

    public String getVenue(int position) {
        try {

            JSONObject jsonObject1 = data.getJSONObject(position);
            jsonObject1 = jsonObject1.getJSONObject("details");
            return jsonObject1.getString("venue");
        } catch (Exception e) {
            return null;
        }
    }

    public String getDesc(int position) {
        try {
            JSONObject jsonObject1 = data.getJSONObject(position);
            return jsonObject1.getString("subtitle");
        } catch (Exception e) {
            return null;
        }
    }

    public int Length() {
        return n;
    }


    public String getTitle(int position) {
        try {
            JSONObject jsonObject1 = data.getJSONObject(position);
            return jsonObject1.getString("title");
        } catch (Exception e) {
            return null;
        }

    }

    public boolean isAppreciated(int pos) {
        try {
            JSONObject obj = data.getJSONObject(pos).getJSONObject("Actions").getJSONObject("Bookmarked");
            return obj.getBoolean("status");
        } catch (Exception e) {
            return false;
        }
    }

    public void setAppreciated(int pos, boolean value) {
        try {
            JSONObject obj = data.getJSONObject(pos).getJSONObject("Actions").getJSONObject("Bookmarked");
            obj.put("status", value);
        } catch (Exception e) {
            Log.d(TAG, "setAppreciated: failed");
        }
    }

    public boolean isAttending(int pos) {
        try {
            JSONObject obj = data.getJSONObject(pos).getJSONObject("Actions").getJSONObject("Participants");
            return obj.getBoolean("status");
        } catch (Exception e) {
            return false;
        }
    }

    public void setAttending(int pos, boolean value) {
        try {
            JSONObject obj = data.getJSONObject(pos).getJSONObject("Actions").getJSONObject("Participants");
            obj.put("status", value);
        } catch (Exception e) {
            Log.d(TAG, "setAttending: failed");
        }
    }

    public Bitmap getImage(int position) {
        try {
            JSONObject jsonObject1 = data.getJSONObject(position);
            String image = jsonObject1.getString("image");
            byte[] decodedString = Base64.decode(image.substring(image.lastIndexOf(',') + 1), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;

        } catch (Exception e) {
            Log.e("error", e.toString());
            return null;

        }
    }

    public boolean isAllowedPagination() {
        return allowPagination;
    }

    public int getId(int pos) {
        try {
            JSONObject obj = data.getJSONObject(pos);
            return obj.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    public String getCost(int position)
    {
        try {

            JSONObject jsonObject1 = data.getJSONObject(position);
            jsonObject1 = jsonObject1.getJSONObject("details");
            String str= jsonObject1.getString("price");
            if(str.equals("0"))
            return "Free";
            else return str;

        } catch (Exception e) {
            return null;
        }

    }

    public String getAuthorName(int position)
    {
        try {

            JSONObject jsonObject1 = data.getJSONObject(position);
            jsonObject1 = jsonObject1.getJSONObject("organiser");
            return jsonObject1.getString("name");

        } catch (Exception e) {
            return null;
        }

    }
    public String getAuthorLink(int position)
    {
        try {

            JSONObject jsonObject1 = data.getJSONObject(position);
            jsonObject1 = jsonObject1.getJSONObject("organiser");
            return jsonObject1.getString("link");

        } catch (Exception e) {
            return null;
        }

    }
    public String getTag(int position)
    {
        Log.e(TAG, "getTag: "+"I am here" );
        try {

            JSONObject jsonObject1 = data.getJSONObject(position);
            jsonObject1 = jsonObject1.getJSONObject("Tags");
            JSONArray jsonArray=jsonObject1.getJSONArray("data");
            String str="";
            for(int i =0;i<jsonArray.length();i++)
                str=str+" "+(new JSONObject(jsonArray.getString(i))).getString("name");

            Log.e(TAG, "getTag: "+str );
           return str;


        } catch (Exception e) {
            Log.e(TAG, "getTag: "+e.toString() );
            return null;
        }
    }
    public String getDescription(int position)
    {try {

        JSONObject jsonObject1 = data.getJSONObject(position);
        jsonObject1 = jsonObject1.getJSONObject("details");
        return jsonObject1.getString("description");
    } catch (Exception e) {
        return null;
    }

    }

    public int getLimit() {
        return limit;
    }

}
