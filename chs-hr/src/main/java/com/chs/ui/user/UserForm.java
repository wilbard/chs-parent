package com.chs.ui.user;

import com.chs.db.model.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class UserForm extends FormLayout {

    TextField fullName = new TextField("Full Name");
    TextField phoneNumber = new TextField("Phone Number");
    EmailField email = new EmailField("Email");
    TextField username = new TextField("Username");
    PasswordField password = new PasswordField("Password");

    Button save = new Button("Save");
    Button edit = new Button(VaadinIcon.EDIT.create());
    Button delete = new Button(VaadinIcon.TRASH.create());
    Button close = new Button(VaadinIcon.CLOSE.create());

    Binder<User> userBinder = new BeanValidationBinder<>(User.class);

    public UserForm() {
        addClassName("user-form");

        userBinder.bindInstanceFields(this);

        fullName.setWidth("100%");
        phoneNumber.setWidth("100%");
        email.setWidth("100%");
        username.setWidth("100%");
        password.setWidth("100%");

        fullName.setPlaceholder("Enter Name");
        phoneNumber.setPlaceholder("Enter Phone Number");
        email.setPlaceholder("Enter Valid Email");
        username.setPlaceholder("Enter Login Username");

        VerticalLayout userFormLayout = new VerticalLayout();
        userFormLayout.addClassName("user-form-layout");
        userFormLayout.setWidth("100%");
        userFormLayout.setPadding(true);
        userFormLayout.add(fullName, phoneNumber, email, username, password, createButtonsLayout());

        add(userFormLayout);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        edit.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        edit.addClickShortcut(Key.COPY);
        delete.addClickShortcut(Key.POWER_OFF);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, userBinder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        userBinder.addStatusChangeListener(event -> save.setEnabled(userBinder.isValid()));

        return new HorizontalLayout(save, edit, delete, close);
    }

    public void setUser(User user) {
        userBinder.setBean(user);
    }

    private void validateAndSave() {
        if (userBinder.isValid()) {
            fireEvent(new SaveEvent(this, userBinder.getBean()));
        }
    }

    //Events
    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private User user;

        protected UserFormEvent(UserForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends UserFormEvent {
        SaveEvent(UserForm source, User user) {
            super(source, user);
        }
    }

    public static class EditEvent extends UserFormEvent {
        EditEvent(UserForm source, User user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends UserFormEvent {
        DeleteEvent(UserForm source, User user) {
            super(source, user);
        }
    }

    public static class CloseEvent extends UserFormEvent {
        CloseEvent(UserForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
