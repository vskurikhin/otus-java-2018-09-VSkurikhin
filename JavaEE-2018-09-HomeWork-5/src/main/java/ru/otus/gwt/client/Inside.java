package ru.otus.gwt.client;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.cellview.client.DataGrid.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import ru.otus.gwt.client.aside.News;
import ru.otus.gwt.client.aside.CBRValutes;
import ru.otus.gwt.client.service.InsideServiceAsync;
import ru.otus.gwt.shared.Emp;
import java.util.List;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class Inside extends Index
{
    private static InsideServiceAsync service = INSTANCE.getInsideService();
    private TextBox loginTextBox;
    private PasswordTextBox passwordTextBox;
    private DataGrid<Emp> table = new DataGrid<>();
    final DeckPanel deckPanel = new DeckPanel();

    public String getLogin() {
        return loginTextBox.getText();
    }

    public String getPassword() {
        return passwordTextBox.getText();
    }

    public interface GwtCssDataGridResources extends DataGrid.Resources {
        @Source({Style.DEFAULT_CSS, "gwtDataGrid.css"})
        Style dataGrid();
    }

    public static final GwtCssDataGridResources gwtCssDataGridResources = GWT.create(GwtCssDataGridResources.class);

    static {
        gwtCssDataGridResources.dataGrid().ensureInjected();
    }

    private <T> AsyncCallback<T> getEmptyAsyncCallback(String message, Class<T> c) {
        return new AsyncCallback<T>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(message);
            }
            @Override
            public void onSuccess(T result) { /* None TODO */ }
        };
    }

    private DataGrid<Emp> drawTable()
    {

        service.getEmpsList(new AsyncCallback<List<Emp>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<Emp> result)
            {
                final ListDataProvider<Emp> model = new ListDataProvider<>(result);
                table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

                TextColumn<Emp> idColumn = new TextColumn<Emp>() {
                    @Override
                    public String getValue(Emp object) {
                        return Long.toString(object.getId());
                    }
                };

                Column<Emp, String> firstNameColumn = new Column<Emp, String>(new EditTextCell()) {
                    @Override
                    public String getValue(Emp emp) {
                        return emp.getFirstName();
                    }
                };

                firstNameColumn.setFieldUpdater((index, emp, value) -> service.setEmpFirstName(
                    emp.getId(), value,
                    getEmptyAsyncCallback("Error update: id: " + emp.getId() + " value: " + value, Void.class)
                ));

                Column<Emp, String> secondNameColumn = new Column<Emp, String>(new EditTextCell()) {
                    @Override
                    public String getValue(Emp emp) {
                        return emp.getSecondName();
                    }
                };
                secondNameColumn.setFieldUpdater((index, emp, value) -> service.setEmpFirstName(
                    emp.getId(), value,
                    getEmptyAsyncCallback("Error update: id: " + emp.getId() + " value: " + value, Void.class)
                ));

                TextColumn<Emp> surNameColumn = new TextColumn<Emp>() {
                    @Override
                    public String getValue(Emp object) {
                        return object.getSurName();
                    }
                };

                Column<Emp, String> deleteBtn = new Column<Emp, String>( new ButtonCell()) {
                    @Override
                    public String getValue(Emp c) {
                        return "x";
                    }
                };
                deleteBtn.setFieldUpdater((index, emp, value) -> service.deleteEmp(
                    emp.getId(), getEmptyAsyncCallback(" Error delete id: " + emp.getId(), Void.class))
                );

                table.addColumn(idColumn, "Id");
                table.addColumn(firstNameColumn, "First Name");
                table.addColumn(secondNameColumn, "Second Name");
                table.addColumn(surNameColumn, "Surname");
                table.addColumn(deleteBtn, "");
                table.setRowCount(result.size());

                table.setHeight((result.size() * 24 + 28 ) + "px");
                table.setColumnWidth(idColumn, "20px");
                table.setColumnWidth(deleteBtn, "16px");
                table.setWidth("450px");
                model.addDataDisplay(table);
            }
        });

        return table;
    }

    private void initMainContainer()
    {

        RootPanel rootPanel = fillDeckPanel(deckPanel);

        deckPanel.add(this::drawTable);
        deckPanel.showWidget(4);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(deckPanel);

        menuAddClickHandler(deckPanel, "navigation-menu-", 5);
        menuAddClickHandler(deckPanel, "aside-menu-", 4);

        rootPanel.add(vPanel);
    }

    public void onModuleLoad()
    {
        super.initHeaderAndTitle();
        initMainContainer();
        new News(GWT.getHostPageBaseURL() + "/rbcnews");
        new CBRValutes(GWT.getHostPageBaseURL() + "/cbrforex");
    }
}
