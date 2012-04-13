package org.conf4j.webapp.servlet;

import static org.conf4j.base.ConfElements.*;
import static org.conf4j.base.impl.ConfServiceImpl.CONF;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/index", name = "IndexServlet")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 8712805354125783121L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Writer out = resp.getWriter();
        resp.setContentType("text/html");
        out.append("<html>");
        out.append("<body>");
        out.append("<a href=\"");
        out.append(CONF.getValue(devoxx_home_url));
        out.append("\">");
        out.append(CONF.getValue(devoxx_home_url));
        out.append("</a><br>");
        out.append("<a href=\"");
        out.append(CONF.getValue(devoxx_agenda_url));
        out.append("\">");
        out.append(CONF.getValue(devoxx_agenda_url));
        out.append("</a><br>");
        out.append("<hr>");
        out.append("<a href=\"dump\">dump</a><br>");
        out.append("<a href=\"fulldump\">fulldump</a><br>");
        out.append("<a href=\"usage\">usage</a><br>");
        out.append("<a href=\"unused\">unused</a><br>");
        out.append("</body>");
        out.append("</html>");
        out.close();
    }

}
