/*
 * Created by VSkurikhin 28.11.18 20:30.
 * GroupEntity.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "user_groups")
@XmlRootElement(name = "group")
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupEntity  implements DataSet, Serializable
{
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = "groupname", nullable = false)
    @XmlAttribute(required = true)
    private String groupname;

    @Basic
    @Column(name = "login", nullable = false)
    @XmlAttribute(required = true)
    private String login;

    @Override
    public String getName()
    {
        return getGroupname();
    }

    @Override
    public void setName(String name)
    {
        setGroupname(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
