package com.when.threemb.cinepolis;

/**
 * Created by User on 11/7/2017.
 */

public class TimingTemplate {

int timing[];
String theatre_name;
String theatre_address;
int theatre_id;
int screen_no;
String date;
int movie_id;
int ticket_amt;

    public TimingTemplate(int amt,int[] timing, String theatre_name, String theatre_address, int theatre_id, int screen_no, String date,int movie_id) {
        this.timing = timing;
        this.theatre_name = theatre_name;
        this.theatre_address = theatre_address;
        this.theatre_id = theatre_id;
        this.screen_no = screen_no;
        this.date = date;
        this.movie_id=movie_id;
        this.ticket_amt=amt;
    }

    public int[] getTiming() {
        return timing;
    }

    public String getTheatre_name() {
        return theatre_name;
    }

    public String getTheatre_address() {
        return theatre_address;
    }

    public int getTheatre_id() {
        return theatre_id;
    }

    public int getScreen_no() {
        return screen_no;
    }

    public String getDate() {
        return date;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public int getTicket_amt() {
        return ticket_amt;
    }
}
