package org.UrlShortner.controller;

import org.UrlShortner.model.UrlMapping;
import org.UrlShortner.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        int expiryMinutes = Integer.parseInt(request.getOrDefault("expiry", "60")); // default 60 mins

        UrlMapping urlMapping = urlService.createShortUrl(originalUrl, expiryMinutes);
        return ResponseEntity.ok(Map.of(
                "shortUrl", "http://localhost:8080/api/" + urlMapping.getShortCode(),
                "expiresAt", urlMapping.getExpiresAt().toString()
        ));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirectToOriginal(@PathVariable String shortCode) {
        return urlService.getOriginalUrl(shortCode)
                .map(url -> {
                    if (url.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
                        return ResponseEntity.status(410).body("Link expired.");
                    }
                    return ResponseEntity.status(302).location(URI.create(url.getOriginalUrl())).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
