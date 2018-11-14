package ru.otus.servlets;

import ru.otus.models.StatisticEntity;
import ru.otus.models.VisitsStatElem;
import ru.otus.services.DbService;
import ru.otus.services.StatisticService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.otus.gwt.shared.Constants.*;

@WebServlet("/" + REQUEST_VISITS_STAT_JSP)
public class StatisticJSPServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        try {
            DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
            StatisticService statService = (StatisticService) getServletContext().getAttribute(STAT_SERVICE);
            List<StatisticEntity> elems = statService.getAllVisitsStatElements(dbService);
            request.setAttribute(ATTR_STAT_ELEMEMTS, elems);
            Map<String, Long> chartValuesMap = elems.stream().collect(
                Collectors.groupingBy(StatisticEntity::getJspPageName, Collectors.counting())
            );
            String chartLabels = chartValuesMap.entrySet().stream().map(
                e -> "\"" + e.getKey() + "\"").collect(Collectors.joining(", ")
            );
            String chartValues = chartValuesMap.entrySet().stream().map(
                e -> e.getValue().toString()).collect(Collectors.joining(", ")
            );
            request.setAttribute(ATTR_STAT_CHART_LABELS, chartLabels);
            request.setAttribute(ATTR_STAT_CHART_VALUES, chartValues);

            request.getRequestDispatcher("/WEB-INF/classes/ftl/visits_stat.ftl").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace(); // TODO LOGGER
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
