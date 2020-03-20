package com.chs.ui.user;

import com.chs.db.model.User;
import com.chs.db.service.UserService;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import java.util.List;


@Scope("prototype")
@SpringComponent
public class UsersProvider extends PageableDataProvider<User,String> {

    @Autowired
    private UserService userService;

    @Override
    protected Page<User> fetchFromBackEnd(Query<User, String> query, Pageable pageable) {
        return userService.findAll(query.getFilter().orElse(""), pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return QuerySortOrder.asc("username").build();
    }

    @Override
    protected int sizeInBackEnd(Query<User, String> query) {
        return userService.count(query.getFilter().orElse(""));
    }
}
