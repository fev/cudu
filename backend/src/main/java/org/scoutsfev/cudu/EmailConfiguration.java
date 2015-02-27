package org.scoutsfev.cudu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class EmailConfiguration {

    @Bean
    public ITemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("mail/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding("UTF-8");
        emailTemplateResolver.setCacheable(false);
        emailTemplateResolver.setOrder(1);
        return emailTemplateResolver;
    }
}
