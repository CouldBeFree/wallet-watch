package com.expenses.walletwatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class WalletWatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletWatchApplication.class, args);
	}

}
