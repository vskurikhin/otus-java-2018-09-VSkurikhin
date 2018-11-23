package ru.otus.db.dao.jdbc;

/*
 * Created by VSkurikhin at winter 2018.
 */

import org.apache.tapestry.pages.Exception;
import ru.otus.db.Executor;
import ru.otus.db.dao.DAOController;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class AbstractController<E extends DataSet, K> implements DAOController<E, K>
{
    private DataSource dataSource;

    public AbstractController()
    {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/PostgresMyDB");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractController(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    protected DataSource getDataSource()
    {
        return dataSource;
    }

    protected List<E> getArrayListAll(String sql, Function<ResultSet, E> f) throws SQLException
    {
        List<E> result = new ArrayList<>();
        Executor executor = new Executor(getDataSource().getConnection());

        executor.execQuery(sql, rs -> {
            while (rs.next()) {
                result.add(f.apply(rs));
            }
        });

        return result;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
