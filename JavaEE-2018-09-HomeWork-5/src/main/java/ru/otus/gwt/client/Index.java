package ru.otus.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xhr.client.ReadyStateChangeHandler;
import com.google.gwt.xhr.client.XMLHttpRequest;
import ru.otus.gwt.client.news.News;
import ru.otus.gwt.client.service.LoginServiceAsync;
import ru.otus.gwt.client.valutes.CBRValutes;
import ru.otus.gwt.client.widget.*;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class Index implements EntryPoint
{
    private static LoginServiceAsync service = INSTANCE.getService();

    protected void initHeaderAndTitle()
    {
        Document.get()
                .getElementById("header-1")
                .setInnerText("Рога и копыта");
        Document.get()
                .getElementById("header-2")
                .setInnerText("Заведение, занимается заготовкой «когтей и хвостов» и «горчицы и щёлока»");
        Document.get()
                .getElementById("title")
                .setInnerText("Home Work 5 of JavaEE 2018-09 | a work showcasing the feature of GWT");
    }

    public void menuAddClickHandler(DeckPanel deckPanel, String id, int n)
    {
        for (int i = 0; i < n; ++i) {
            Anchor anchor = Anchor.wrap(
                    Document.get().getElementById(id + i).cast()
            );
            if (null != anchor) {
                int finalI = i;
                anchor.addClickHandler(event -> deckPanel.showWidget(finalI));
            } else {
                throw new NullPointerException(); // TODO custom Exception
            }
        }
    }

    @SuppressWarnings("GwtToHtmlReferences")
    protected RootPanel fillDeckPanel(DeckPanel deckPanel)
    {
        //noinspection GWTStyleCheck
        deckPanel.setStyleName("deckpanel");

        Label stubLabel = new Label("stub");

        deckPanel.add(new IndexView());
        deckPanel.add(new ContactsView());
        deckPanel.add(new BackformView());
        deckPanel.add(new ArchiveView());
        deckPanel.add(stubLabel);

        RootPanel rootPanel = RootPanel.get("main-container");

        if (null != rootPanel) {
            deckPanel.showWidget(0);
        } else {
            rootPanel = RootPanel.get("login-container");
            if (null != rootPanel) {
                deckPanel.add(new LoginView(service));
                deckPanel.showWidget(5);
            } else {
                rootPanel = RootPanel.get("inside-container");
            }
        }

        return rootPanel;
    }

    // main-container
    private void initMainContainer()
    {
        final DeckPanel deckPanel = new DeckPanel();

        RootPanel rootPanel = fillDeckPanel(deckPanel);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(deckPanel);

        menuAddClickHandler(deckPanel, "navigation-menu-", 4);
        menuAddClickHandler(deckPanel, "aside-menu-", 4);

        rootPanel.add(vPanel);
    }

    private ReadyStateChangeHandler handler = xhr -> {
        if (xhr.getReadyState() == XMLHttpRequest.DONE) {
            // Window.alert("headers: " + headers);
            if (xhr.getStatus() != 200) {
                Window.alert("Ok XML document: " + xhr.getResponseText());
            } else {
                Window.alert("Could not parse XML document.");
            }
        }
    };

    public void onModuleLoad()
    {
        initHeaderAndTitle();
        initMainContainer();
        new News(GWT.getHostPageBaseURL() + "/rbcnews");
        new CBRValutes(GWT.getHostPageBaseURL() + "/cbrforex");
    }
}
