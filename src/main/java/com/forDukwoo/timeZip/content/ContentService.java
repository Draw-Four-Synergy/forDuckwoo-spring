package com.forDukwoo.timeZip.content;

import com.forDukwoo.timeZip.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.PortUnreachableException;

@Service
public class ContentService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ContentDao contentDao;
    private final ContentProvider contentProvider;
    private final JwtService jwtService;

    public ContentService(ContentDao contentDao, ContentProvider contentProvider, JwtService jwtService) {
        this.contentDao = contentDao;
        this.contentProvider = contentProvider;
        this.jwtService = jwtService;
    }
}
