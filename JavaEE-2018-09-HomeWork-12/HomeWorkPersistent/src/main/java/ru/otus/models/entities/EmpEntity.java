/*
 * EmpEntity.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

import ru.otus.adapters.json.DeptEntityJsonAdapter;
import ru.otus.adapters.json.UserEntityJsonAdapter;
import ru.otus.adapters.xml.DeptEntityTypeXMLAdapter;
import ru.otus.adapters.xml.UserEntityTypeXMLAdapter;
import ru.otus.models.DataSet;

import static ru.otus.shared.Constants.DB.*;

@Data
@EqualsAndHashCode
@Entity
@Table(name = TBL_EMP_REGISTRY)
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmpEntity implements DataSet, Serializable
{
    static final long serialVersionUID = 20L;

    @Id
    @SequenceGenerator(name = SEQ_GENERATOR_EMP, sequenceName = SEQ_NAME_EMP, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = SEQ_GENERATOR_EMP)
    @Column(name = F_ID, nullable = false, unique = true)
    @XmlAttribute(required = true)
    @JsonbProperty
    private long id;

    @Basic
    @Column(name = F_FIRST_NAME, nullable = false)
    @XmlElement(required = true)
    @JsonbProperty("first-name")
    private String firstName;

    @Basic
    @Column(name = F_SECOND_NAME, nullable = false)
    @XmlElement(required = true)
    @JsonbProperty("second-name")
    private String secondName;

    @Basic
    @Column(name = F_SUR_NAME, nullable = false)
    @XmlElement(required = true)
    @JsonbProperty("sur-name")
    private String surName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = F_DEPARTMENT_ID, referencedColumnName = F_ID)
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(DeptEntityTypeXMLAdapter.class)
    @JsonbTypeAdapter(DeptEntityJsonAdapter.class)
    private DeptEntity department;

    @Basic
    @Column(name = F_CITY)
    @XmlElement
    private String city;

    @Basic
    @Column(name = F_JOB)
    @XmlElement
    private String job;

    @Basic
    @Column(name = F_SALARY)
    @XmlAttribute
    private Long salary;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = F_USER_ID, referencedColumnName = F_ID)
    @XmlJavaTypeAdapter(UserEntityTypeXMLAdapter.class)
    @JsonbTypeAdapter(UserEntityJsonAdapter.class)
    private UserEntity user;

    @Basic
    @Column(name = F_AGE)
    @XmlAttribute
    private Long age;

    @Override
    public String nameGet()
    {
        return getFirstName() + " " + getSecondName() + " " + getSurName();
    }

    @Override
    public void letName(String name)
    {
        String names[] = name.split(" ");
        if (names.length != 3)
            throw new IndexOutOfBoundsException(); // TODO Custom exceptions
        setFirstName(names[0]);
        setSecondName(names[1]);
        setSurName(names[2]);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
