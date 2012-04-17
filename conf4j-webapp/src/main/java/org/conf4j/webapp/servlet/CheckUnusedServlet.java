package org.conf4j.webapp.servlet;

import static org.conf4j.base.impl.ConfServiceImpl.CONF;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/unused", name = "CheckUnusedServlet")
public class CheckUnusedServlet extends HttpServlet {
    private static final long serialVersionUID = 4887084502608183969L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        final ServletOutputStream os = resp.getOutputStream();
        try {
            final PrintStream ps = new PrintStream(os);
            ps.println("------------------------------------------------------------------------------------------");
<<<<<<< HEAD:conf4j-webapp/src/main/java/org/conf4j/webapp/servlet/CheckUnusedServlet.java
            ps.println("-------------------------------------- CHECK UNUSED --------------------------------------");
=======
            ps.println("-------------------------------------- check-unused --------------------------------------");
>>>>>>> 95db5004d2fc805239122db650479189bb1647b1:conf4j-webapp/src/main/java/org/conf4j/webapp/servlet/UnusedServlet.java
            ps.println("------------------------------------------------------------------------------------------");
            CONF.checkUnused(ps);
            ps.println("------------------------------------------------------------------------------------------");
        } finally {
            os.close();
        }
    }
}
