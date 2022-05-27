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

    private String placeName;

    private String address1;
    private String address2;
    private String address3;

    private String city;
    private String state;

    private String postalCode;

    private String pleaseNote;

    public EventDetails (String eventName, String eventID, String url, String info, String startLocalDate, String startTime, String endLocalDate, String endTime, String saleStart, String saleEnd, ArrayList<String[]> presales, String seatmap, String ticketLimit, String placeName, String address1, String address2, String address3, String city, String state, String postalCode, String pleaseNote)
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
        this.placeName = placeName;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.pleaseNote = pleaseNote;
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

    public String getPlaceName()
    {
        return placeName;
    }

    public String getAddress1()
    {
        return address1;
    }

    public String getAddress2()
    {
        return address2;
    }

    public String getAddress3()
    {
        return address3;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public String getPleaseNote()
    {
        return pleaseNote;
    }
}