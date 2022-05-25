public class TicketMaster {
    private String eventName;
    private String eventID;

    public TicketMaster(String eventName, String eventID) {
        this.eventName = eventName;
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventID()
    {
        return eventID;
    }
}
