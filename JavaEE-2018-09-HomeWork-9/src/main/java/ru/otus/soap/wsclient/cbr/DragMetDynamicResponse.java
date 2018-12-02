/*
 * DragMetDynamicResponse.java
 * This file was last modified at 2018.12.02 11:22 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wsclient.cbr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained
 * within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DragMetDynamicResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"dragMetDynamicResult"})
@XmlRootElement(name = "DragMetDynamicResponse")
public class DragMetDynamicResponse
{
    @XmlElement(name = "DragMetDynamicResult")
    protected DragMetDynamicResponse.DragMetDynamicResult dragMetDynamicResult;

    /**
     * Gets the value of the dragMetDynamicResult property.
     *
     * @return possible object is
     * {@link DragMetDynamicResponse.DragMetDynamicResult }
     */
    public DragMetDynamicResponse.DragMetDynamicResult getDragMetDynamicResult()
    {
        return dragMetDynamicResult;
    }

    /**
     * Sets the value of the dragMetDynamicResult property.
     *
     * @param value allowed object is
     *              {@link DragMetDynamicResponse.DragMetDynamicResult }
     */
    public void setDragMetDynamicResult(DragMetDynamicResponse.DragMetDynamicResult value)
    {
        this.dragMetDynamicResult = value;
    }

    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content
     * contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DragMetDynamicResult
    { /* None */ }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
