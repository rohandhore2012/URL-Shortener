package org.UrlShortner.repository;

import org.UrlShortner.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlMapping, String> {
}
