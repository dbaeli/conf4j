package org.conf4j.webapp.servlet;

import static org.conf4j.service.ConfServiceInstance.CONF;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/fulldump", name = "FullDumpServlet")
public class FullDumpServlet extends HttpServlet {
    private static final long serialVersionUID = 4887084502608183969L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        final ServletOutputStream os = resp.getOutputStream();
        try {
            final PrintStream ps = new PrintStream(os);
            ps.println("------------------------------------------------------------------");
            ps.println("----------------------- CONFIGURATION DUMP -----------------------");
            ps.println("------------------------------------------------------------------");
            CONF.dumpConf(ps, false);
            ps.println("------------------------------------------------------------------");
        } finally {
            os.close();
        }
    }
}
