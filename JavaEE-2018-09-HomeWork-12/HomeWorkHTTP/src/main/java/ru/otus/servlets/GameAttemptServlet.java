package ru.otus.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.ejb.GameAttemptsService;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet("/game_attempt")
public class GameAttemptServlet extends HttpServlet
{
    private static final Logger LOGGER = LogManager.getLogger(GameAttemptServlet.class.getName());

    @EJB(name = "GameAttemptsService")
    private GameAttemptsService service;

    private static final String REQUEST_PARAM_SELECTED_VALUE = "value";
    private static final String REQUEST_PARAM_USER_NAME = "user_name";

    private static final String JSON_RESPONSE_TEMPLATE = "{\"status\":%s, \"message\":\"%s\"}";
    private static final String MESSAGE_TEMPLATE = "Ответ %sверный. Загаданное число %d, выбранное число %d. Попыток осталось: %d";
    private static final String GAME_OVER_MESSAGE_TEMPLATE = "Игра окончена. Вы сможете продолжить через некоторое время.";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        req.setCharacterEncoding("UTF-8");

        Random random = new Random();
        Integer selectedValue = Integer.valueOf(req.getParameter(REQUEST_PARAM_SELECTED_VALUE));
        Integer generatedValue = random.nextInt(10);
        String userName = req.getParameter(REQUEST_PARAM_USER_NAME);

        boolean isLocked = service.isLocked(userName);

        service.doAttempt(userName);
        long attemptsCount = service.getAttemptsCount(userName);

        boolean status;
        String message;

        if (!isLocked) {
            status = selectedValue.compareTo(generatedValue) == 0;
            message = String.format(MESSAGE_TEMPLATE, status ? "" : "не ", generatedValue, selectedValue, attemptsCount);
        }
        else {
            status = false;
            message = GAME_OVER_MESSAGE_TEMPLATE;
        }

        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF8"), true)) {
            pw.write(String.format(JSON_RESPONSE_TEMPLATE, status, message));
        }
    }
}
