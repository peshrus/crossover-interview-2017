package com.crossover.trial.journals.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity
public class Journal {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Date publishDate;

	@ManyToOne(optional = false)
    @JoinColumn(name = "publisher_id")
	private Publisher publisher;

    @Column(nullable = false)
    private String uuid; //external id

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    void onPersist() {
        this.publishDate = new Date();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public Date getPublishDate() {
        return publishDate;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Journal journal = (Journal) o;

        return id != null ? id.equals(journal.id) : journal.id == null;
    }

    @Override
    public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
