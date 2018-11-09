package ru.otus.services;

import org.json.XML;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

public class DirectoryJDBCService implements DirectoryService
{
    private final String directorySQL =
            "WITH RECURSIVE recurs (id, pid, level, title)\n" +
            "  AS (\n" +
            "    SELECT id, pid, CAST(0 AS BIGINT), title FROM dep_directory WHERE pid = 0\n" +
            "    UNION ALL\n" +
            "    SELECT next_to.id, recurs.level + 1, next_to.pid, next_to.title\n" +
            "      FROM recurs \n" +
            "      JOIN dep_directory next_to\n" +
            "        ON recurs.id = next_to.pid\n" +
            "  )\n" +
            "  SELECT * FROM recurs\n;";
    private final String NL = System.lineSeparator();

    private DataSource dataSource;

    public DirectoryJDBCService(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    private StringBuilder sbAppendOrganizationUnit(ResultSet resultSet, StringBuilder sb)
    throws SQLException
    {
        sb.append("<organizationUnit id='")
                .append(resultSet.getLong("id"))
                .append("' title='")
                .append(resultSet.getString("title"))
                .append("'>").append(NL);
        return sb;
    }

    @Override
    public String getDirectoryXML(ServletContext sc) throws Exception
    {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append(NL).append("<directory>").append(NL);
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(directorySQL);
             ResultSet resultSet = ps.executeQuery())
        {
            long prevLevel = -1;
            Stack<Object> stack = new Stack<>();

            while (resultSet.next()) {
                long level = resultSet.getLong("level");
                if (prevLevel < level) {
                    sb = sbAppendOrganizationUnit(resultSet, sb);
                    stack.push(new Object());
                } else if (prevLevel > level) {
                    sb.append("</organizationUnit>").append(NL)
                      .append("</organizationUnit>").append(NL);
                    sb = sbAppendOrganizationUnit(resultSet, sb);
                    stack.pop();
                } else {
                    sb.append("</organizationUnit>").append(NL);
                    sb = sbAppendOrganizationUnit(resultSet, sb);
                }
                prevLevel = level;
            }

            while ( ! stack.empty()) {
                sb.append("</organizationUnit>").append(NL);
                stack.pop();
            }

            return sb.append("</directory>").toString();
        }
        catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public String getDirectoryJSON(ServletContext sc) throws Exception
    {
        return XML.toJSONObject(getDirectoryXML(sc)).toString();
    }
}
