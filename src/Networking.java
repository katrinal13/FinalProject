import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


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

        if (jsonObj.has("_embedded"))
        {
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
        ArrayList<String[]> venues = new ArrayList<String[]>();

        JSONObject jsonObj = new JSONObject(json);

        String eventName = jsonObj.getString("name");
        String eventID = jsonObj.getString("id");

        String url = jsonObj.getString("url");
        String info = "";
        if (jsonObj.has("info"))
        {
            info = jsonObj.getString("info");
        }
        String pleaseNote = "";
        if (jsonObj.has("pleaseNote"))
        {
            pleaseNote = jsonObj.getString("pleaseNote");
        }

        JSONObject dates = jsonObj.getJSONObject("dates");
        String startLocalDate = "";
        String startTime = "";
        if (dates.has("start"))
        {
            JSONObject start = dates.getJSONObject("start");
            if (start.has("localDate"))
            {
                startLocalDate = start.getString("localDate");
            }
            if (start.has("dateTime"))
            {
                startTime = start.getString("dateTime");
            }
        }
        String endLocalDate = "";
        String endTime = "";
        if (dates.has("end"))
        {
            JSONObject end = dates.getJSONObject("end");
            if (end.has("endLocalDate"))
            {
                endLocalDate = end.getString("localDate");
            }
            if (end.has("localTime"))
            {
                endTime = end.getString("dateTime");
            }
        }
        JSONObject sales = jsonObj.getJSONObject("sales");
        JSONObject publicObj = sales.getJSONObject("public");
        String saleStart = publicObj.getString("startDateTime");
        String saleEnd = publicObj.getString("endDateTime");

        String presaleName = "";
        String presaleDescription = "";
        String presaleURL = "";
        String presaleStart = "";
        String presaleEnd = "";
        if (sales.has("presales"))
        {
            JSONArray pre = sales.getJSONArray("presales");
            for (int i = 0; i < pre.length(); i++)
            {
                JSONObject obj = pre.getJSONObject(i);
                presaleName = obj.getString("name");
                if (obj.has("description"))
                {
                    presaleDescription = obj.getString("description");
                }
                if (obj.has("url"))
                {
                    presaleURL = obj.getString("url");
                }
                presaleStart = obj.getString("startDateTime");
                presaleEnd = obj.getString("endDateTime");
                String[] presale = {presaleName, presaleDescription, presaleURL, presaleStart, presaleEnd};
                presales.add(presale);
            }
        }

        String seatmap = "";
        if (jsonObj.has("seatmap"))
        {
            JSONObject seatObj = jsonObj.getJSONObject("seatmap");
            seatmap = seatObj.getString("staticUrl");
        }

        String ticketLimit = "";
        if (jsonObj.has("ticketLimit"))
        {
            JSONObject limit = jsonObj.getJSONObject("ticketLimit");
            ticketLimit = limit.getString("info");
        }

        JSONObject embedded = jsonObj.getJSONObject("_embedded");
        JSONArray venuesObj = embedded.getJSONArray("venues");
        for (int i = 0; i < venuesObj.length(); i++)
        {
            JSONObject venue = venuesObj.getJSONObject(i);
            String[] venueInfo = new String[5];
            venueInfo[0] = venue.getString("name");
            JSONObject address = venue.getJSONObject("address");
            if (address.has("line1"))
            {
                venueInfo[0] += "\n" + address.getString("line1");
            }
            if (address.has("line2"))
            {
                venueInfo[1] += "\n" + address.getString("line2");
            }
            if (address.has("line3"))
            {
                venueInfo[1] += "\n" + address.getString("line1");
            }
            if (venue.has("city"))
            {
                JSONObject city = venue.getJSONObject("city");
                venueInfo[2] = city.getString("name");
            }
            if (venue.has("state"))
            {
                JSONObject state = venue.getJSONObject("state");
                venueInfo[3] = state.getString("name");
            }
            venueInfo[4] = venue.getString("postalCode");
            venues.add(venueInfo);
        }

        JSONArray imagesObj = jsonObj.getJSONArray("images");
        JSONObject image = imagesObj.getJSONObject(3);
        String imageURL = image.getString("url");

        EventDetails eventInfo = new EventDetails(eventName, eventID, url, info, startLocalDate, startTime, endLocalDate, endTime, saleStart, saleEnd, presales, seatmap, ticketLimit, venues, pleaseNote, imageURL);
        return eventInfo;
    }
}
