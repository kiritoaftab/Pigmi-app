package com.ot.pigmy.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class MyConfig {

    @Bean
    Docket getDocket() {

		Contact contact = new Contact("Orbit Technologys Team", "https://orbit-technologys.com/",
				"info@orbittechnologys.com");

		@SuppressWarnings("rawtypes")
		List<VendorExtension> vendorExtensions = new ArrayList<>();

		ApiInfo apiInfo = new ApiInfo("PIGMY Application",
				"This Application has information about PIGMY Union Bank Application", "1.0",
				"https://orbit-technologys.com/", contact, "Apache 2.0", "https://orbit-technologys.com/",
				vendorExtensions);
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.ot.pigmy"))
				.build().apiInfo(apiInfo).useDefaultResponseMessages(false);
	}

}