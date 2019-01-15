package com.crossover.trial.journals.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity(name = "subscription")
public class Subscription {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private User user;

	@Column(nullable = false)
	private Date date;

	@ManyToOne(optional= false)
	private Category category;

	@PrePersist
	private void onPersist() {
		date = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
