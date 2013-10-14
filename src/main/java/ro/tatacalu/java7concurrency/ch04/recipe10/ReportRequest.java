/**
 * 
 */
package ro.tatacalu.java7concurrency.ch04.recipe10;

import java.util.concurrent.CompletionService;

/**
 * @author tatacalu
 * 
 */
public class ReportRequest implements Runnable {

    private String                    name;

    private CompletionService<String> service;

    public ReportRequest(String name, CompletionService<String> service) {
        this.name = name;
        this.service = service;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        ReportGenerator reportGenerator = new ReportGenerator(name, "Report");
        service.submit(reportGenerator);

    }

}
