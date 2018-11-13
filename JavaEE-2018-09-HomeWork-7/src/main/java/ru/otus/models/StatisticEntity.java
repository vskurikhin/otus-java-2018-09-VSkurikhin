package ru.otus.models;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.otus.adapters.xml.LocalDateTimeXMLAdapter;
import ru.otus.adapters.xml.UserEntityTypeXMLAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "statistic")
@XmlRootElement(name = "statistic")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticEntity implements DataSet, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = "name_marker", nullable = false)
    @XmlAttribute(required = true)
    private String nameMarker;

    @Basic
    @Column(name = "jsp_page_name")
    @XmlElement
    private String jspPageName;

    @Basic
    @Column(name = "ip_address")
    @XmlElement
    private String ipAddress;

    @Basic
    @Column(name = "user_agent")
    @XmlElement
    private String userAgent;

    @Basic
    @Column(name = "client_time")
    @XmlJavaTypeAdapter(LocalDateTimeXMLAdapter.class)
    private LocalDateTime clientTime;

    @Basic
    @Column(name = "server_time")
    @XmlJavaTypeAdapter(LocalDateTimeXMLAdapter.class)
    private LocalDateTime serverTime;

    @Basic
    @Column(name = "session_id")
    @XmlElement
    private String sessionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @XmlJavaTypeAdapter(UserEntityTypeXMLAdapter.class)
    private UserEntity user;

    @Basic
    @Column(name = "prev_id")
    @XmlAttribute
    private Long previousId;

    @Override
    public String getName()
    {
        return getNameMarker();
    }

    @Override
    public void setName(String name)
    {
        setNameMarker(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
