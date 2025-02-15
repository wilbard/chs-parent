package com.chs.ui;

import com.chs.ui.user.UserView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.icons.VaadinIcons;
import org.vaadin.teemusa.sidemenu.SideMenu;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {

        /*Tabs tabs = new Tabs();
        FlexLayout centeredLayout = new FlexLayout();
        centeredLayout.setSizeFull();
        centeredLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        centeredLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        centeredLayout.add(tabs);

        tabs.add(new Tab(new RouterLink("Centered Tab", ReportView.class)));

        addToNavbar(false, centeredLayout);*/

        createHeader();
        createDrawer();
    }

    private void createHeader() {
        try {
            H4 logo = new H4("Human Resources Management");
            logo.addClassName("logo");

            MenuBar menuBar = new MenuBar();
            menuBar.setOpenOnHover(true);
            menuBar.addClassName("nav-bar");

            Text selected = new Text("");
            Div message = new Div(new Text("Selected: "), selected);

            MenuItem manageUser = menuBar.addItem("User Management");
            manageUser.addComponentAsFirst(VaadinIcon.USER.create());
            MenuItem settings = menuBar.addItem("Preferences");
            settings.addComponentAsFirst(VaadinIcon.COG.create());
            Anchor logout = new Anchor("/logout", "Sign Out");
            menuBar.addItem(logout).addComponentAsFirst(VaadinIcon.POWER_OFF.create());

            SubMenu manageUserSub = manageUser.getSubMenu();
            MenuItem users = manageUserSub.addItem("Users");
            MenuItem roles = manageUserSub.addItem("Roles");

            SubMenu settingsSub = settings.getSubMenu();
            settingsSub.addItem("System Settings", e -> selected.setText("System Settings"));
            settingsSub.addItem("Accessibility Settings", e -> selected.setText("Accessibility Settings"));

            SubMenu usersSub = users.getSubMenu();
            usersSub.addItem("Add User", e -> selected.setText("Add User"));
            Anchor listUsers = new Anchor("/users", "List Users");
            usersSub.addItem(listUsers);

            SubMenu rolesSub = roles.getSubMenu();
            rolesSub.addItem("Add Role", e -> selected.setText("Add Role"));
            rolesSub.addItem("List Role", e -> selected.setText("List Role"));

            FlexLayout rightLayout = new FlexLayout();
            rightLayout.setSizeFull();
            rightLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            rightLayout.setAlignItems(FlexComponent.Alignment.END);
            rightLayout.add(menuBar);

            HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, rightLayout);
            header.addClassName("header");
            header.setWidth("100%");
            header.setHeight("50%");
            header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

            Span workingEmp = new Span("Working Employees: 54");
            Span leaveEmp = new Span("Employees On Leave: 2");

            HorizontalLayout infoBar = new HorizontalLayout(workingEmp, leaveEmp);
            infoBar.addClassName("info-header");
            infoBar.setWidth("100%");
            infoBar.setHeight("50%");
            infoBar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

            VerticalLayout navBar = new VerticalLayout(header, infoBar);

            addToNavbar(navBar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDrawer() {
        try {
            RouterLink dashboardLink = new RouterLink(null, DashboardView.class);
            Icon homeIcon = VaadinIcon.HOME.create();
            homeIcon.getElement().getStyle().set("vertical-align", "baseline");
            homeIcon.addClassName("side-icons");
            homeIcon.setSize("0.2em");
            dashboardLink.addComponentAsFirst(homeIcon);
            Span homeText = new Span("Dashboard");
            homeText.getElement().getStyle().set("margin-left", "5px");
            homeText.getElement().getStyle().set("vertical-align", "baseline");
            homeText.getElement().getStyle().set("font-size", "16px");
            dashboardLink.add(homeText);
            dashboardLink.addClassName("side-link");
            dashboardLink.getElement().getStyle().set("width", "90%");
            dashboardLink.getElement().getStyle().set("height", "var(--lumo-space-m)");
            dashboardLink.getElement().getStyle().set("vertical-align", "text-bottom");
            dashboardLink.getElement().getStyle().set("padding", "var(--lumo-space-m)");
            dashboardLink.getElement().getStyle().set("background-color", "var(--lumo-contrast-30pct)");
            dashboardLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink usersLink = new RouterLink(null, UserView.class);
            Icon usersIcon = VaadinIcon.USERS.create();
            usersIcon.getElement().getStyle().set("vertical-align", "baseline");
            usersIcon.addClassName("side-icons");
            homeIcon.setSize("0.2em");
            usersLink.addComponentAsFirst(usersIcon);
            Span usersText = new Span("Users");
            usersText.getElement().getStyle().set("margin-left", "5px");
            usersText.getElement().getStyle().set("vertical-align", "baseline");
            usersText.getElement().getStyle().set("font-size", "16px");
            usersLink.add(usersText);
            usersLink.addClassName("side-link");
            usersLink.getElement().getStyle().set("width", "90%");
            usersLink.getElement().getStyle().set("height", "var(--lumo-space-m)");
            usersLink.getElement().getStyle().set("vertical-align", "text-bottom");
            usersLink.getElement().getStyle().set("padding", "var(--lumo-space-m)");
            usersLink.getElement().getStyle().set("background-color", "var(--lumo-contrast-30pct)");
            usersLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink reportsLink = new RouterLink(null, ReportView.class);
            Icon reportsIcon = VaadinIcon.FILE_PRESENTATION.create();
            reportsIcon.getElement().getStyle().set("vertical-align", "baseline");
            reportsIcon.addClassName("side-icons");
            reportsIcon.setSize("0.2em");
            reportsLink.addComponentAsFirst(reportsIcon);
            Span reportssText = new Span("Reports");
            reportssText.getElement().getStyle().set("margin-left", "5px");
            reportssText.getElement().getStyle().set("vertical-align", "baseline");
            reportssText.getElement().getStyle().set("font-size", "16px");
            reportsLink.add(reportssText);
            reportsLink.addClassName("side-link");
            reportsLink.getElement().getStyle().set("width", "90%");
            reportsLink.getElement().getStyle().set("height", "var(--lumo-space-m)");
            reportsLink.getElement().getStyle().set("vertical-align", "text-bottom");
            reportsLink.getElement().getStyle().set("padding", "var(--lumo-space-m)");
            reportsLink.getElement().getStyle().set("background-color", "var(--lumo-contrast-30pct)");
            reportsLink.setHighlightCondition(HighlightConditions.sameLocation());

            VerticalLayout sideBarLayout = new VerticalLayout(dashboardLink, usersLink, reportsLink);
            sideBarLayout.setSizeFull();
            sideBarLayout.getElement().getStyle().set("background-color", "var(--lumo-contrast-30pct)");
            sideBarLayout.getElement().getStyle().set("padding", "var(--lumo-space-m)");

            SideMenu sideMenu = new SideMenu();
            sideMenu.addMenuItem("Dashboard", VaadinIcons.HOME, () -> {
                VerticalLayout content = new VerticalLayout();
                content.addComponentAsFirst(dashboardLink);
            });

            sideBarLayout.addClassName("side-menu");

            addToDrawer(sideBarLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HorizontalLayout getFooter() {
        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.setSizeFull();
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        Span footerContent = new Span("2020 CHS Solutions");
        footerContent.addComponentAsFirst(VaadinIcon.COPYRIGHT.create());

        footerLayout.add(footerContent);

        return footerLayout;
    }
}
