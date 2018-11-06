package ru.otus.gwt.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import ru.otus.gwt.client.service.LoginService;
import ru.otus.gwt.client.text.ApplicationConstants;
import ru.otus.gwt.client.validation.ValidatorFactory.GwtValidator;
import ru.otus.gwt.client.widget.AddView.AddViewUiBinder;
import ru.otus.gwt.client.widget.LoginView.LoginViewUiBinder;
import ru.otus.gwt.client.widget.image.ApplicationImages;

public class ApplicationGinModule extends AbstractGinModule {
    protected void configure() {
        bind(LoginService.class);
        bind(ApplicationConstants.class);
        bind(AddViewUiBinder.class);
        bind(LoginViewUiBinder.class);
        bind(GwtValidator.class);
        bind(ApplicationImages.class);
    }
}
