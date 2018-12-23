/*
 * DeptEntity.java
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
import javax.xml.bind.annotation.*;
import java.io.Serializable;

import static ru.otus.shared.Constants.DB.*;

@Data
@EqualsAndHashCode
@Entity
@Table(name = TBL_DEPT_DIRECTORY)
@XmlRootElement(name = "department")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeptEntity implements DataSet, Serializable
{
    static final long serialVersionUID = 10L;

    @Id
    @SequenceGenerator(name = SEQ_GENERATOR_DEPT, sequenceName = SEQ_NAME_DEPT, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = SEQ_GENERATOR_DEPT)
    @Column(name = F_ID, nullable = false, unique = true)
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = F_PID, nullable = false)
    @XmlAttribute
    private long parentId; // recursion

    @Basic
    @Column(name = F_TITLE, nullable = false)
    @XmlElement(required = true)
    private String title;

    @Override
    public String nameGet()
    {
        return getTitle();
    }

    @Override
    public void letName(String name)
    {
        setTitle(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
