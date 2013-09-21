package ro.tatacalu.java7concurrency.ch01;

import java.util.Date;

public class Event {

    private Date   date;
    private String description;

    /**
     * @param date
     * @param description
     */
    public Event(Date date, String description) {
        super();
        this.date = date;
        this.description = description;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}
