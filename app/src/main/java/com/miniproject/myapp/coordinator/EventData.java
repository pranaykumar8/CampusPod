package com.miniproject.myapp.coordinator;

public class EventData {
    String Eventtitle;
    String Eventdate;
    String Eventtime;
    String Eventvenue;




    public EventData(String Eventtitle, String Eventdate, String Eventtime, String Eventvenue) {
        this.Eventtitle = Eventtitle;
        this.Eventdate = Eventdate;
        this.Eventtime = Eventtime;
        this.Eventvenue = Eventvenue;
    }

    public void setEventtitle(String eventtitle) {
        this.Eventtitle = eventtitle;
    }

    public void setEventdate(String eventdate) {
        this.Eventdate = eventdate;
    }

    public void setEventtime(String eventtime) {
        this.Eventtime = eventtime;
    }

    public void setEventvenue(String eventvenue) {
        this.Eventvenue = eventvenue;
    }

    public String getEventtitle() {
        return Eventtitle;
    }

    public String getEventdate() {
        return Eventdate;
    }

    public String getEventtime() {
        return Eventtime;
    }

    public String getEventvenue() {
        return Eventvenue;
    }
    public EventData(){}
}
