package com.cclean.terminal;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
public class TerminalApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication ap = new SpringApplication(TerminalApplication.class);
        ap.setBannerMode(Banner.Mode.OFF);
        ap.run(args);
    }

}
