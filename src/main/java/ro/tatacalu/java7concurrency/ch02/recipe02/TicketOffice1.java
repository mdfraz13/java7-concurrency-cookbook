/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe02;

/**
 * @author Matei
 *
 */
public class TicketOffice1 implements Runnable {

    private Cinema cinema;
    
    public TicketOffice1(Cinema cinema) {
        this.cinema = cinema;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        this.cinema.sellTickets1(3);
        this.cinema.sellTickets1(2);
        this.cinema.sellTickets2(2);
        this.cinema.returnTickets1(3);
        this.cinema.sellTickets1(5);
        this.cinema.sellTickets2(2);
        this.cinema.sellTickets2(2);
        this.cinema.sellTickets2(2);
    }

}
