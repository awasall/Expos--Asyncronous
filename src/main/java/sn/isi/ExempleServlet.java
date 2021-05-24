package sn.isi;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns="/exemple", asyncSupported = true)
public class ExempleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        final long startTime = System.nanoTime();
        final AsyncContext asyncContext = request.startAsync(request, response);

        new Thread() {

            @Override
            public void run() {
                try {
                    ServletResponse response = asyncContext.getResponse();
                    response.setContentType("text/plain");
                    PrintWriter out = response.getWriter();
                    Thread.sleep(2000);
                    response.getWriter().println("test");
                    //System.out.println("Test console 1");
                    out.print("Work completed. Time elapsed: " + (System.nanoTime() - startTime));

                    out.flush();
                    asyncContext.complete();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
        try {
            Thread.sleep(3000);
            response.getWriter().println("test1");
            //System.out.println("Test console");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
