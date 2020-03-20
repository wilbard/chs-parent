package com.chs.ui.user;

import com.chs.db.model.User;
import com.chs.db.service.UserService;
import com.chs.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.PostConstruct;
import java.awt.print.Pageable;
import java.util.Arrays;

@PageTitle("CHS | Users")
@Route(value = "users", layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
public class UserView extends VerticalLayout {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersProvider usersProvider;

    private  UserForm form;
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    Button addUserButton = new Button(  "Add User");

    @PostConstruct
    private void build(){

        addClassName("list-user-view");
        setSizeFull();
        configureGrid();

        form = new UserForm();
        form.addListener(UserForm.SaveEvent.class, this::saveUser);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUser);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());

        FlexLayout userLayout = new FlexLayout();
        userLayout.setSizeFull();
        userLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        userLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        grid.setMinHeight("70%");
        form.setMaxWidth("30%");
        userLayout.add(grid, form);

        Div content = new Div(userLayout);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);

        //grid.setDataProvider(usersProvider);
        ConfigurableFilterDataProvider<User, Void, String> wrapper = usersProvider.withConfigurableFilter();
        grid.setDataProvider(wrapper);
        filterText.addValueChangeListener(event -> {
            String filter = event.getValue();
            if (filter.trim().isEmpty()) {
                filter = null;
            }
            wrapper.setFilter(filter);
        });
        updateList();
        closeEditor();
    }

    private void saveUser(UserForm.SaveEvent evt) {
        if (userService.findByUsername(evt.getUser().getUsername()) == null) {
            User newUser = evt.getUser();
            newUser.setRoles(Arrays.asList(userService.findUserRoleByRole("USER")));
            userService.addUser(newUser);
            updateList();
            closeEditor();
        } else {
            userService.saveUser(evt.getUser());
            updateList();
            closeEditor();
        }
    }

    private void deleteUser(UserForm.DeleteEvent event) {
        userService.removeUser(event.getUser());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.getDataProvider().refreshAll();
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User());
        form.setVisible(true);
    }

    private void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("user-grid");
        grid.setSizeFull();
        grid.setColumns("fullName", "phoneNumber", "email");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.ON_CHANGE.LAZY);
        //filterText.addValueChangeListener(e -> updateList());

        addUserButton.addClassName("add-user-button");
        addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addUserButton.addClickListener(event -> addUser());

        Div actionBarContent = new Div(filterText, addUserButton);
        actionBarContent.addClassName("action-bar-container");
        actionBarContent.setWidthFull();

        HorizontalLayout actionBar = new HorizontalLayout(actionBarContent);
        actionBar.addClassName("action-bar");
        actionBar.setWidthFull();

        return actionBar;
    }
}
