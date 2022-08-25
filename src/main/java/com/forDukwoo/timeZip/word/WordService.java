package com.forDukwoo.timeZip.word;

import com.forDukwoo.timeZip.user.UserDao;
import com.forDukwoo.timeZip.user.UserProvider;
import com.forDukwoo.timeZip.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WordDao wordDao;
    private final WordProvider wordProvider;
    private final JwtService jwtService;

    @Autowired
    public WordService(WordDao wordDao, WordProvider wordProvider, JwtService jwtService) {
        this.wordDao = wordDao;
        this.wordProvider = wordProvider;
        this.jwtService = jwtService;
    }

}
