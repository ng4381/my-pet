package com.nservices.mypet.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DbInit implements CommandLineRunner {

    @Value("${myvar.profilename}")
    private String profilename;


    @Override
    public void run(String... args) throws Exception {
        log.info("===================Profile=======================");
        log.info("Profile name: " + profilename);
        log.info("=================================================");
    }
}