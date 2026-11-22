package com.auditoria.ai.service;

import com.auditoria.ai.dto.SourceRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SingleSourceParser {

    private static final Pattern ENTRADA_PATTERN = Pattern.compile("^(https?://\\S+)\\s+[-–—:]?\\s*(.+)$");

    public SourceRequest parse(String entrada) {
        Matcher matcher = ENTRADA_PATTERN.matcher(entrada.trim());
        if (!matcher.matches() || !isHttpUrl(matcher.group(1))) {
            throw new IllegalArgumentException(
                    "entrada deve conter uma URL http(s), seguida da identificação da fonte."
            );
        }

        String url = matcher.group(1);
        String identificacao = matcher.group(2).trim();
        if (identificacao.isEmpty()) {
            throw new IllegalArgumentException(
                    "entrada deve informar a identificação da fonte após a URL."
            );
        }

        return new SourceRequest(identificacao, identificacao, url);
    }

    private boolean isHttpUrl(String value) {
        try {
            URI uri = new URI(value);
            return uri.getHost() != null && ("http".equalsIgnoreCase(uri.getScheme())
                    || "https".equalsIgnoreCase(uri.getScheme()));
        } catch (URISyntaxException exception) {
            return false;
        }
    }
}
