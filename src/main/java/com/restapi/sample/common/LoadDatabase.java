package com.restapi.sample.common;

import com.restapi.sample.employee.Employee;
import com.restapi.sample.employee.EmployeeRepository;
import com.restapi.sample.order.Order;
import com.restapi.sample.order.OrderRepository;
import com.restapi.sample.order.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    /*
     * CommandLineRunner: 스프링 애플리케이션이 구동된 후에 실행되어야 하는 빈을 정의하기 위해 사용
     */
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository,  OrderRepository orderRepository) {
        return args -> {
            /*log.info("Preloading " + employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar")));
            log.info("Preloading " + employeeRepository.save(new Employee("Frodo", "Baggins", "theif")));

            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));


            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });*/
        };
    }

}
