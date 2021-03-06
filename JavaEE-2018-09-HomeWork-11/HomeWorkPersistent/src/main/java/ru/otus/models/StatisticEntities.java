/*
 * StatisticEntities.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.otus.models.entities.StatisticEntity;

import javax.json.bind.annotation.JsonbProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@XmlRootElement(name = "registrations")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticEntities implements Serializable, Entities<StatisticEntity>
{
    @XmlElement(name = "registration")
    @JsonbProperty("registrations")
    private List<StatisticEntity> registrations = new ArrayList<>();

    public StatisticEntities()
    { /* None */ }

    public StatisticEntities(List<StatisticEntity> registrations)
    {
        this.registrations = registrations;
    }

    public List<StatisticEntity> getRegistrations()
    {
        return registrations;
    }

    public void setRegistrations(List<StatisticEntity> registrations)
    {
        this.registrations = registrations;
    }

    @Override
    public void add(StatisticEntity department)
    {
        registrations.add(department);
    }

    @Override
    public List<StatisticEntity> asList()
    {
        return getRegistrations();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
