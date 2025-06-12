package org.UrlShortner.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UrlMapping {

    @Id
    private String shortCode;

    @Column(nullable = false)
    private String originalUrl;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private int clickCount;

    // Constructors
    public UrlMapping() {}

    public UrlMapping(String shortCode, String originalUrl, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.clickCount = 0;
    }

    // Getters and Setters

    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public int getClickCount() { return clickCount; }
    public void setClickCount(int clickCount) { this.clickCount = clickCount; }
}
