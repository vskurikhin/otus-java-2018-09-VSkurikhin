/*
 * AddView.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import ru.otus.client.Inside;
import ru.otus.client.service.InsideServiceAsync;
import ru.otus.shared.Emp;

import javax.inject.Inject;

import static ru.otus.client.gin.ApplicationInjector.INSTANCE;

public class AddView extends Composite implements IsWidget
{
    @UiTemplate("AddView.ui.xml")
    public interface AddViewUiBinder extends UiBinder<VerticalPanel, AddView>
    { /* None */ }

    @UiField
    TextBox firstNameTextField;

    @UiField
    TextBox secondNameTextField;

    @UiField
    TextBox surNameTextField;

    @UiField
    TextBox jobTextField;

    @UiField
    TextBox cityTextField;

    @UiField
    TextBox ageTextField;

    @UiField
    HorizontalPanel firstNamePanel;

    @UiField
    HorizontalPanel secondNamePanel;

    @UiField
    HorizontalPanel surNamePanel;

    @UiField
    HorizontalPanel jobPanel;

    @UiField
    HorizontalPanel cityPanel;

    @UiField
    HorizontalPanel agePanel;

    private Inside inside;
    private InsideServiceAsync service;

    @Override
    public Widget asWidget()
    {
        return getWidget();
    }

    @UiHandler("submit")
    void clickHandler(ClickEvent evt)
    {
        Emp emp = new Emp(
            0, firstNameTextField.getValue(), secondNameTextField.getValue(), surNameTextField.getValue(),
            jobTextField.getValue(), cityTextField.getValue(), ageTextField.getValue()
        );

        service.addNewEmp(emp, new AsyncCallback<Void>()
        {
            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(Void result)
            {
                inside.refresh();
                inside.showDataGrid();
            }
        });
    }

    private static AddViewUiBinder ourUiBinder = INSTANCE.getAddViewUiBinder();

    @Inject
    public AddView(InsideServiceAsync service, Inside inside)
    {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.service = service;
        this.inside = inside;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
