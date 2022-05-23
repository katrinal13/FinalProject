public class TicketMaster {
    private String eventName;
    private int eventID;

    public TicketMaster(String eventName, int eventID) {
        this.eventName = eventName;
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventID()
    {
        return eventID;
    }
}
