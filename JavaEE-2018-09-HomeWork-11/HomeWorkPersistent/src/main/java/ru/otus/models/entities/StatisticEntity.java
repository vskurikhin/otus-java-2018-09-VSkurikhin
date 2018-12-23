/*
 * StatisticEntity.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.otus.adapters.json.UserEntityJsonAdapter;
import ru.otus.adapters.xml.LocalDateTimeXMLAdapter;
import ru.otus.adapters.xml.UserEntityTypeXMLAdapter;
import ru.otus.models.DataSet;
import ru.otus.shared.Constants;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import static ru.otus.shared.Constants.DB.*;

@Data
@EqualsAndHashCode
@Entity
@Table(name = TBL_STATISTIC)
@NamedStoredProcedureQuery(
    name = Constants.PROC_INSERT_STATISTIC,
    procedureName = PRC_INSERT_STATISTIC,
    parameters = {
        @StoredProcedureParameter(name = F_NAME_MARKER, mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = F_JSP_PAGE_NAME, mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = F_IP_ADDRESS, mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = F_USER_AGENT, mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = F_CLIENT_TIME, mode = ParameterMode.IN, type = Date.class),
        @StoredProcedureParameter(name = F_SERVER_TIME, mode = ParameterMode.IN, type = Date.class),
        @StoredProcedureParameter(name = F_SESSIONID, mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = F_USER_ID, mode = ParameterMode.IN, type = Long.class),
        @StoredProcedureParameter(name = F_PREV_ID, mode = ParameterMode.IN, type = Long.class),
    }
)
@XmlRootElement(name = "statistic")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticEntity implements DataSet, Serializable
{
    static final long serialVersionUID = 40L;

    @Id
    @SequenceGenerator(name = SEQ_GENERATOR_STATISTIC, sequenceName = SEQ_NAME_STATISTIC, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = SEQ_GENERATOR_STATISTIC)
    @Column(name = F_ID)
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = F_NAME_MARKER, nullable = false)
    @XmlAttribute(required = true)
    private String nameMarker;

    @Basic
    @Column(name = F_JSP_PAGE_NAME, nullable = false)
    @XmlElement
    private String jspPageName;

    @Basic
    @Column(name = F_IP_ADDRESS, nullable = false)
    @XmlElement
    private String ipAddress;

    @Basic
    @Column(name = F_USER_AGENT, nullable = false)
    @XmlElement
    private String userAgent;

    @Basic
    @Column(name = F_CLIENT_TIME)
    @XmlJavaTypeAdapter(LocalDateTimeXMLAdapter.class)
    private LocalDateTime clientTime;

    @Basic
    @Column(name = F_SERVER_TIME)
    @XmlJavaTypeAdapter(LocalDateTimeXMLAdapter.class)
    private LocalDateTime serverTime;

    @Basic
    @Column(name = F_SESSIONID, nullable = false)
    @XmlElement
    private String sessionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = F_USER_ID, referencedColumnName = F_ID)
    @XmlJavaTypeAdapter(UserEntityTypeXMLAdapter.class)
    @JsonbTypeAdapter(UserEntityJsonAdapter.class)
    private UserEntity user;

    @Basic
    @Column(name = F_PREV_ID)
    @XmlAttribute
    private Long previousId;

    @Override
    public String nameGet()
    {
        return getNameMarker();
    }

    @Override
    public void letName(String name)
    {
        setNameMarker(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
