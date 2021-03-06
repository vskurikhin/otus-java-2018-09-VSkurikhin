package ru.otus.gwt.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import ru.otus.gwt.client.service.InsideServiceAsync;
import ru.otus.gwt.shared.Emp;

import javax.inject.Inject;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class AddView extends Composite implements IsWidget
{
    @UiTemplate("AddView.ui.xml")
    public interface AddViewUiBinder extends UiBinder<VerticalPanel, AddView> {
    }

    @UiField
    TextBox firstNameTextField;

    @UiField
    TextBox secondNameTextField;

    @UiField
    TextBox surNameTextField;

    @UiField
    HorizontalPanel firstNamePanel;

    @UiField
    HorizontalPanel secondNamePanel;

    @UiField
    HorizontalPanel surNamePanel;

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
            0, firstNameTextField.getValue(), secondNameTextField.getValue(), surNameTextField.getValue()
        );
        service.addNewEmp(emp, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(Void result) { /* None */ }
        });
    }

    private static AddViewUiBinder ourUiBinder = INSTANCE.getAddViewUiBinder();

    @Inject
    public AddView(InsideServiceAsync service) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.service = service;
    }
}
