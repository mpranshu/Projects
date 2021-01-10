package com.webapp.javaurlshortner.repository;

import com.webapp.javaurlshortner.entity.Url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Defining this interface serves two purposes:
// First, by extending JpaRepository we get a bunch of generic CRUD methods into our type that allows saving Url, deleting them and so on.
// Second, this will allow the Spring Data JPA repository infrastructure to scan the classpath for this interface and
// create a Spring bean for it.

// To have Spring create a bean that implements this interface, all we need to do is use the Spring JPA namespace and
// activate the repository support using the appropriate element:
// <jpa:repositories base-package="com.webapp.javaurlshortner" />

// Source https://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa/

// Type of Entity: Url and type of its Key: Long
@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
}
