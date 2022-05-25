public class EventDetails extends TicketMaster
{
    private String description;
    private String additionalInfo;
    private String url;

    private String startLocalDate;
    private String startTime;

    private String endLocalDate;
    private String endTime;

    private String saleStart;
    private String saleEnd;

    private String presaleName;
    private String presaleStart;
    private String presaleEnd;
    private String presaleURL;
    private String presaleDescription;

    private String seatmap;
    private String ticketLimit;

    private String address1;
    private String address2;
    private String address3;

    private String city;
    private String state;

    private String pleaseNote;

    public EventDetails (String eventName, String eventID, String description, String additionalInfo, String url, String startLocalDate, String startTime, String endLocalDate, String endTime, String saleStart, String saleEnd, String presaleName, String presaleStart, String presaleEnd, String presaleURL, String presaleDescription, String seatmap, String ticketLimit, String address1, String address2, String address3, String city, String state, String pleaseNote)
    {
        super(eventName, eventID);
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.url = url;
        this.startLocalDate = startLocalDate;
        this.startTime = startTime;
        this.endLocalDate = endLocalDate;
        this.endTime = endTime;
        this.saleStart = saleStart;
        this.saleEnd = saleEnd;
        this.presaleName = presaleName;
        this.presaleStart = presaleStart;
        this.presaleEnd = presaleEnd;
        this.presaleURL = presaleURL;
        this.presaleDescription = presaleDescription;
        this.seatmap = seatmap;
        this.ticketLimit = ticketLimit;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.state = state;
        this.pleaseNote = pleaseNote;
    }

    public String getDescription()
    {
        return description;
    }

    public String getAdditionalInfo()
    {
        return additionalInfo;
    }

    public String getURL()
    {
        return url;
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
}
