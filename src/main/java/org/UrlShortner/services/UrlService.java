package org.UrlShortner.services;


import org.UrlShortner.model.UrlMapping;
import org.UrlShortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;

    @Autowired
    private UrlRepository urlRepository;

    public UrlMapping createShortUrl(String originalUrl, int expiryMinutes) {
        String shortCode = generateShortCode();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(expiryMinutes);

        UrlMapping urlMapping = new UrlMapping(shortCode, originalUrl, now, expiresAt);
        return urlRepository.save(urlMapping);
    }

    public Optional<UrlMapping> getOriginalUrl(String shortCode) {
        Optional<UrlMapping> optional = urlRepository.findById(shortCode);
        optional.ifPresent(url -> {
            url.setClickCount(url.getClickCount() + 1);
            urlRepository.save(url);
        });
        return optional;
    }

    private String generateShortCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
