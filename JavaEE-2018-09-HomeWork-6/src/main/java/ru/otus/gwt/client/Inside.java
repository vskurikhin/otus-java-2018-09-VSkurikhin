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
import ru.otus.gwt.client.widget.AddView;
import ru.otus.gwt.shared.Emp;

import java.util.ArrayList;
import java.util.List;

import static com.google.gwt.dom.client.Style.Unit;
import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class Inside extends Welcome
{
    private static InsideServiceAsync service = INSTANCE.getInsideService();
    private TextBox loginTextBox;
    private PasswordTextBox passwordTextBox;
    private DataGrid<Emp> table = new DataGrid<>();
    private int deckIndexListView;
    private int deckIndexAddView;
    final DeckPanel deckPanel = new DeckPanel();
    ListDataProvider<Emp> model = new ListDataProvider<>(new ArrayList<>());

    public String getLogin() {
        return loginTextBox.getText();
    }

    public String getPassword() {
        return passwordTextBox.getText();
    }

    public int getDeckIndexListView()
    {
        return deckIndexListView;
    }

    public int getDeckIndexAddView()
    {
        return deckIndexAddView;
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

    private void doResize()
    {
        try {
            //noinspection GwtToHtmlReferences
            int width = RootPanel.get("main").getOffsetWidth() - 41;
            width = width > 759 ? width : 759;
            table.setWidth(width + "px");
        } catch (Throwable e) {
            Window.alert(e.toString());
            table.setWidth(759 + "px");
        }
    }

    private DataGrid<Emp> drawTable()
    {
        Inside localInside = this;
        service.getEmpsList(new AsyncCallback<List<Emp>>() {
            private void update(int index, Emp emp, String value)
            {
                service.deleteEmp(
                        emp.getId(), getEmptyAsyncCallback(" Error delete id: " + emp.getId(), Void.class)
                );
                localInside.refresh();
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<Emp> result)
            {
                // final ListDataProvider<Emp> model = new ListDataProvider<>(result);
                model.setList(result);
                table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

                TextColumn<Emp> idColumn = new TextColumn<Emp>() {
                    @Override
                    public String getValue(Emp object) {
                        return Long.toString(object.getId());
                    }
                };
                idColumn.setCellStyleNames("text-align-right padding-right-5px");

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
                deleteBtn.setFieldUpdater(this::update);
                deleteBtn.setCellStyleNames("text-align-right padding-right-1px");

                table.addColumn(idColumn, "Id");
                table.addColumn(firstNameColumn, "First Name");
                table.addColumn(secondNameColumn, "Second Name");
                table.addColumn(surNameColumn, "Surname");
                table.addColumn(deleteBtn, "");

                table.setColumnWidth(idColumn, "20px");
                table.setColumnWidth(deleteBtn, "16px");
                table.setRowCount(result.size());
                table.setHeight((result.size() * 24 + 28 ) + "px");

                doResize();

                table.setTableWidth(100, Unit.PCT);
                table.setMinimumTableWidth(759, Unit.PX);
                model.addDataDisplay(table);
            }
        });

        return table;
    }

    private <T> VerticalPanel inboxing(DataGrid<T> data) {
        VerticalPanel vp = new VerticalPanel();
        Button addButtun = new Button();

        addButtun.setTitle("add");
        addButtun.setText("add new");
        addButtun.addClickHandler(event -> deckPanel.showWidget(getDeckIndexAddView()));

        Button refreshButtun = new Button();

        refreshButtun.setTitle("refresh");
        refreshButtun.setText("refresh");
        refreshButtun.addClickHandler(event -> refresh());

        vp.setHorizontalAlignment(TextColumn.ALIGN_CENTER);
        vp.add(data);
        vp.add(addButtun);
        vp.add(refreshButtun);

        return vp;
    }

    public void refresh() {
        model.setList(new ArrayList<>());
        model.refresh();
        table.redraw();

        service.getEmpsList(new AsyncCallback<List<Emp>>()
        {
            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<Emp> result)
            {
                model.setList(result);
                table.setRowCount(result.size());
                table.setHeight((result.size() * 24 + 28 ) + "px");
                model.refresh();
                table.redraw();
            }
        });
    }

    public void showDataGrid() {
        deckPanel.showWidget(getDeckIndexListView());
    }

    private void initMainContainer()
    {
        RootPanel rootPanel = fillDeckPanel(deckPanel);

        deckPanel.add(inboxing(drawTable()));
        deckIndexListView = deckPanel.getWidgetCount() - 1;
        deckPanel.showWidget(getDeckIndexListView());

        deckPanel.add(new AddView(service, this));
        deckIndexAddView = deckPanel.getWidgetCount() - 1;

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setHorizontalAlignment(TextColumn.ALIGN_CENTER);
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
        Window.addResizeHandler(event -> doResize());
    }
}
