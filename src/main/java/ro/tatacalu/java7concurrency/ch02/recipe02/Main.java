/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe02;

/**
 * @author Matei
 * 
 */
public class Main {

    private static final String STRING_TICKET_OFFICE1_THREAD_NAME = "TicketOffice1";
    private static final String STRING_TICKET_OFFICE2_THREAD_NAME = "TicketOffice2";

    /**
     * @param args
     */
    public static void main(String[] args) {
        Cinema cinema = new Cinema();

        TicketOffice1 ticketOffice1 = new TicketOffice1(cinema);
        Thread thread1 = new Thread(ticketOffice1, STRING_TICKET_OFFICE1_THREAD_NAME);

        TicketOffice2 ticketOffice2 = new TicketOffice2(cinema);
        Thread thread2 = new Thread(ticketOffice2, STRING_TICKET_OFFICE2_THREAD_NAME);

        thread1.start();
        thread2.start();

        // wait for the completion of the threads
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Room 1 Vacancies: %d\n", cinema.getVacanciesCinema1());
        System.out.printf("Room 2 Vacancies: %d\n", cinema.getVacanciesCinema2());
    }

}
