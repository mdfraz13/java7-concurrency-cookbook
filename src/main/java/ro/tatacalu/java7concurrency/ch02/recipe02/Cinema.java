/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe02;

/**
 * @author Matei
 *
 */
public class Cinema {

    private static final long LONG_DEFAULT_VACANCIES = 20L;
    
    private long vacanciesCinema1;
    private long vacanciesCinema2;
    
    private final Object controlCinema1; 
    private final Object controlCinema2;
    
    public Cinema() {
        this.controlCinema1 = new Object();
        this.controlCinema2 = new Object();
        
        this.vacanciesCinema1 = LONG_DEFAULT_VACANCIES;
        this.vacanciesCinema2 = LONG_DEFAULT_VACANCIES;
    }
    
    public boolean sellTickets1(long number) {
        
        synchronized (this.controlCinema1) {
            if (number < this.vacanciesCinema1) {
                this.vacanciesCinema1 -= number;
                return true;
            } else {
                return false;
            }
        }
    }
    
    public boolean sellTickets2(long number) {
        
        synchronized (this.controlCinema2) {
            if (number < this.vacanciesCinema2) {
                this.vacanciesCinema2 -= number;
                return true;
            } else {
                return false;
            }
        }
    }
    
    public boolean returnTickets1(long number) {
        synchronized (this.controlCinema1) {
            this.vacanciesCinema1 += number;
            return true;
        }
    }
    
    public boolean returnTickets2(long number) {
        synchronized (this.controlCinema2) {
            this.vacanciesCinema2 += number;
            return true;
        }
    }

    /**
     * @return the vacanciesCinema1
     */
    public long getVacanciesCinema1() {
        return vacanciesCinema1;
    }

    /**
     * @return the vacanciesCinema2
     */
    public long getVacanciesCinema2() {
        return vacanciesCinema2;
    }
}
