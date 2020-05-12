package com.chs.ui.user;

import com.chs.db.model.User;
import com.chs.db.service.UserService;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import java.util.List;


@Scope("prototype")
@SpringComponent
public class UsersProvider extends PageableDataProvider<User,String> {

    @Autowired
    private UserService userService;

    @Override
    protected Page<User> fetchFromBackEnd(Query<User, String> query, Pageable pageable) {
        int offset = query.getOffset();
        int limit = query.getLimit();
        SortDirection sortDirection = SortDirection.DESCENDING;
        Sort sort = Sort.by("id").descending();
        for (SortOrder<String> sortOrder : query.getSortOrders()) {
            sort = Sort.by(sortOrder.getSorted()).ascending();
            if (sortOrder.getDirection() == sortDirection) {
                sort = Sort.by(sortOrder.getSorted()).descending();
            }
        }
        return this.userService.findAllPages(query.getFilter().orElse(""), offset, limit, sort);
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
