import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.awt.Container;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

public class Networking
{
    private String apiKey;
    private String baseUrl;

    public Networking()
    {
        apiKey = "EgOidq4EAUIwWDkRALSXvKJ7nXAy8cRp";
        baseUrl = "https://app.ticketmaster.com/discovery/v2";
    }

    public ArrayList<TicketMaster> getEvents(String zipCode)
    {
        String endPoint = "/events.json";
        String urlNowPlaying = baseUrl + endPoint + "?postalCode=" + zipCode + "&apikey=" + apiKey;

        String response = makeAPICall(urlNowPlaying);
        System.out.println(response);
        ArrayList<TicketMaster> events = parseNowPlayingJSON(response);
        return events;
    }

    private String makeAPICall(String url)
    {
        try {
            URI myUri = URI.create(url);
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ArrayList<TicketMaster> parseNowPlayingJSON(String json)
    {
        ArrayList<TicketMaster> events = new ArrayList<TicketMaster>();

        JSONObject jsonObj = new JSONObject(json);
        JSONObject embedded = jsonObj.getJSONObject("_embedded");
        JSONArray eventList = embedded.getJSONArray("events");

        for (int i = 0; i < eventList.length(); i++)
        {
            JSONObject eventObj = eventList.getJSONObject(i);
            String eventName = eventObj.getString("name");
            String eventID = eventObj.getString("id");
            TicketMaster event = new TicketMaster(eventName, eventID);
            events.add(event);
        }
        return events;
    }

    public EventDetails getEventDetails(String eventID)
    {
        String endPoint = "/events/" + eventID;
        String urlEventDetails = baseUrl + endPoint + ".json?apikey=" + apiKey;

        String response = makeAPICall(urlEventDetails);

        EventDetails detailed = parseEventDetailJSON(response);
        return detailed;
    }

    private EventDetails parseEventDetailJSON(String json)
    {
        ArrayList<String[]> presales = new ArrayList<String[]>();

        JSONObject jsonObj = new JSONObject(json);

        String eventName = jsonObj.getString("name");
        String eventID = jsonObj.getString("id");

        String url = jsonObj.getString("url");
        String info = jsonObj.getString("info");
        String pleaseNote = jsonObj.getString("pleaseNote");

        JSONObject dates = jsonObj.getJSONObject("dates");
        JSONObject start = dates.getJSONObject("start");
        String startLocalDate = start.getString("localDate");
        JSONObject localTimeS = start.getJSONObject("localTime");
        String startTime = localTimeS.getString("dateTime");

        JSONObject end = dates.getJSONObject("end");
        String endLocalDate = end.getString("localDate");
        JSONObject localTimeE = end.getJSONObject("dateTime");
        String endTime = localTimeE.getString("dateTime");

        JSONObject sales = jsonObj.getJSONObject("sales");
        JSONObject publicObj = sales.getJSONObject("public");
        String saleStart = publicObj.getString("startDateTime");
        String saleEnd = publicObj.getString("endDateTime");

        JSONArray pre = sales.getJSONArray("presales");
        for (int i = 0; i < pre.length(); i++)
        {
            JSONObject obj = pre.getJSONObject(i);
            String presaleName = obj.getString("name");
            String presaleDescription = obj.getString("description");
            String presaleURL = obj.getString("url");
            String presaleStart = obj.getString("startDateTime");
            String presaleEnd = obj.getString("endDateTime");
            String[] presale = {presaleName, presaleDescription, presaleURL, presaleStart, presaleEnd};
            presales.add(presale);
        }

        JSONObject seatObj = jsonObj.getJSONObject("seatmap");
        String seatmap = seatObj.getString("staticUrl");

        JSONObject limit = jsonObj.getJSONObject("ticketLimit");
        String ticketLimit = limit.getString("info");

        JSONObject place = jsonObj.getJSONObject("place");
        String placeName = place.getString("name");
        JSONObject address = place.getJSONObject("address");
        String address1 = address.getString("line1");
        String address2 = address.getString("line2");
        String address3 = address.getString("line3");
        JSONObject cityObj = jsonObj.getJSONObject("city");
        String city = cityObj.getString("name");
        JSONObject stateObj = jsonObj.getJSONObject("state");
        String state = stateObj.getString("name");
        JSONObject country = place.getJSONObject("country");
        String postalCode = country.getString("postalCode");

        EventDetails eventInfo = new EventDetails(eventName, eventID, url, info, startLocalDate, startTime, endLocalDate, endTime, saleStart, saleEnd, presales, seatmap, ticketLimit, placeName, address1, address2, address3, city, state, postalCode, pleaseNote);
        return eventInfo;
    }
}
