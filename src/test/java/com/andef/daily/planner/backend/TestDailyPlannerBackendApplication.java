package com.andef.daily.planner.backend;

import org.springframework.boot.SpringApplication;

public class TestDailyPlannerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(DailyPlannerBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
