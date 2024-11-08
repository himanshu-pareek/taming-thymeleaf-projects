package dev.javarush.taming_thymeleaf.thyme_wizards.configuration;

import dev.javarush.taming_thymeleaf.thyme_wizards.db.InMemoryUniqueIdGenerator;
import dev.javarush.taming_thymeleaf.thyme_wizards.db.UniqueIdGenerator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.UUID;

@Configuration
public class WebConfiguration {

    @Bean
    public ITemplateResolver svgTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/svg/");
        resolver.setSuffix(".svg");
        resolver.setTemplateMode(TemplateMode.XML);
        return resolver;
    }

    @Bean
    public UniqueIdGenerator<UUID> uniqueIdGenerator() {
        return new InMemoryUniqueIdGenerator();
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
