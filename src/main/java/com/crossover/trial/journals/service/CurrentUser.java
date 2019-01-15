package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.Role;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.model.Subscription;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private User user;
	private Subscription subscription;

	public CurrentUser(User user) {
		super(user.getLoginName(), user.getPwd(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Long getId() {
		return user.getId();
	}

	public Role getRole() {
		return user.getRole();
	}

}