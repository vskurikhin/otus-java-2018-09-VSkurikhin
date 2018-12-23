/*
 * GroupEntity.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.otus.models.DataSet;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

import static ru.otus.shared.Constants.DB.*;

@Data
@EqualsAndHashCode
@Entity
@Table(name = TBL_USER_GROUPS)
@XmlRootElement(name = "group")
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupEntity implements DataSet, Serializable
{
    static final long serialVersionUID = 30L;

    @Id
    @SequenceGenerator(name = SEQ_GENERATOR_GROUP, sequenceName = SEQ_NAME_GROUP, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = SEQ_GENERATOR_GROUP)
    @Column(name = F_ID, nullable = false, unique = true)
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = F_GROUPNAME, nullable = false)
    @XmlAttribute(required = true)
    private String groupname;

    @Basic
    @Column(name = F_LOGIN, nullable = false)
    @XmlAttribute(required = true)
    private String login;

    @Override
    public String nameGet()
    {
        return getGroupname();
    }

    @Override
    public void letName(String name)
    {
        setGroupname(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
