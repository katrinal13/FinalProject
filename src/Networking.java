import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

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
        baseUrl = "https://app.ticketmaster.com/discovery/v2/";
    }

    public ArrayList<TicketMaster> getEvents(String zipCode)
    {
        String endPoint = "/discovery/v2/events.json";
        String urlNowPlaying = baseUrl + endPoint + "?postalCode=" + zipCode + "&api_key="+ apiKey;

        String response = makeAPICall(urlNowPlaying);

        ArrayList<TicketMaster> events = parseNowPlayingJSON(response);
        return events;
    }

    private String makeAPICall(String url)
    {
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
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
        JSONArray eventList = jsonObj.getJSONArray("events");

        for (int i = 0; i < eventList.length(); i++)
        {
            JSONObject eventObj = eventList.getJSONObject(i);
            String eventName = eventObj.getString("name");
            int eventID = eventObj.getInt("id");
            TicketMaster event = new TicketMaster(eventName, eventID);
            events.add(event);
        }
        return events;
    }
}
