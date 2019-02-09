package com.gn4me.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
	// configure swagger object for documentation
    @Bean
    public Docket customImplementation(){
       return new Docket(DocumentationType.SWAGGER_2)
     	  .apiInfo(apiInfo())	 
     	  //.useDefaultResponseMessages(false)
     	  .select()
 	      .apis(RequestHandlerSelectors.basePackage("com.gn4me.app.core.api"))
 	      .paths(PathSelectors.any())
 	      .build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Starter Web App API")
                .description("Application API reference for developers")
                //.termsOfServiceUrl("#")
                .contact(new Contact("DevTeam", "#", "mfeky@gn4me.com"))
                //.license(" License ").licenseUrl("#")
                .version("1.0")
                .build();
    }

    
    
}