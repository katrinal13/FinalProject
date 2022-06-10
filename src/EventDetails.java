import java.util.ArrayList;

public class EventDetails extends TicketMaster
{
    private String url;
    private String info;
    private String startLocalDate;
    private String startTime;
    private String endLocalDate;
    private String endTime;
    private String saleStart;
    private String saleEnd;
    private ArrayList<String[]> presales;
    private String seatmap;
    private String ticketLimit;
    private ArrayList<String[]> venues;
    private String pleaseNote;
    private String image;

    public EventDetails (String eventName, String eventID, String url, String info, String startLocalDate, String startTime, String endLocalDate, String endTime, String saleStart, String saleEnd, ArrayList<String[]> presales, String seatmap, String ticketLimit, ArrayList<String[]> venues, String pleaseNote, String image)
    {
        super(eventName, eventID);
        this.url = url;
        this.info = info;
        this.startLocalDate = startLocalDate;
        this.startTime = startTime;
        this.endLocalDate = endLocalDate;
        this.endTime = endTime;
        this.saleStart = saleStart;
        this.saleEnd = saleEnd;
        this.presales = presales;
        this.seatmap = seatmap;
        this.ticketLimit = ticketLimit;
        this.venues = venues;
        this.pleaseNote = pleaseNote;
        this.image = image;
    }

    public String getURL()
    {
        return url;
    }

    public String getInfo()
    {
        return info;
    }

    public String getStartLocalDate()
    {
        return startLocalDate;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public String getEndLocalDate()
    {
        return endLocalDate;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public String getSaleStart()
    {
        return saleStart;
    }

    public String getSaleEnd()
    {
        return saleEnd;
    }

    public ArrayList<String[]> getPresales()
    {
        return presales;
    }

    public String getSeatmap()
    {
        return seatmap;
    }

    public String getTicketLimit()
    {
        return ticketLimit;
    }

    public ArrayList<String[]> getVenues()
    {
        return venues;
    }

    public String getPleaseNote()
    {
        return pleaseNote;
    }

    public String getImage()
    {
        return image;
    }
}