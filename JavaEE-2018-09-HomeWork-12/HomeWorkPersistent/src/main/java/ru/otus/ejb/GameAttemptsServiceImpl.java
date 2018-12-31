package ru.otus.ejb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.db.dao.UserAttemptDAO;
import ru.otus.db.dao.UserDAO;
import ru.otus.db.dao.jpa.UserAttemptController;
import ru.otus.db.dao.jpa.UserController;
import ru.otus.models.entities.UserAttemptEntity;
import ru.otus.models.entities.UserEntity;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Remote(GameAttemptsService.class)
@Stateful
@TransactionAttribute(SUPPORTS)
public class GameAttemptsServiceImpl implements GameAttemptsService
{
    private static final Logger LOGGER = LogManager.getLogger(GameAttemptsServiceImpl.class.getName());

    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private UserDAO userDAO;

    private UserAttemptDAO attemptDAO;

    @EJB
    private LockedUsers lockedUsers;

    private Map<String, Integer> attemptsMap;

    @PostConstruct
    private void init()
    {
        attemptsMap = new ConcurrentHashMap<>();
        UserController userController = new UserController();
        userController.setEntityManager(em);
        UserAttemptController attemptController = new UserAttemptController();
        attemptController.setEntityManager(em);
        userDAO = userController;
        attemptDAO = attemptController;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public void doAttempt(String userName)
    {
        int attepts = attemptsMap.getOrDefault(userName, 11) - 1;
        attemptsMap.put(userName, attepts);
        UserEntity userEntity = userDAO.findEntityByLogin(userName);

        if (null != userEntity) {
            if (attepts < 1) {
                lockedUsers.lock(userEntity.getId(), LocalDateTime.now());
            }
            UserAttemptEntity entity = new UserAttemptEntity(userEntity, attepts);
            attemptDAO.create(entity);
            LOGGER.info("create: {}", entity);
        }
        else {
            LOGGER.error("user with login: {} not found!", userName);
        }
    }

    @Override
    public long getAttemptsCount(String userName)
    {
        UserEntity userEntity = userDAO.findEntityByLogin(userName);

        if (null == userEntity) {
            LOGGER.error("user with login: {} not found!", userName);
            return 0;
        }

        return attemptsMap.getOrDefault(userName, 10);
    }

    @Override
    public boolean isLocked(String userName)
    {
        UserEntity userEntity = userDAO.findEntityByLogin(userName);

        if (null != userEntity) {
            return lockedUsers.isLocked(userEntity.getId());
        }

        return false;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
