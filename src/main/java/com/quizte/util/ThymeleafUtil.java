package com.quizte.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@RequiredArgsConstructor
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ThymeleafUtil {

    TemplateEngine templateEngine;

    public String getProcessedHtml(Map<String, Object> model, String templateName) {
        String template = null;

        Context context = new Context();

        if (model != null) {
            model.forEach(context::setVariable);
            template = templateEngine.process(templateName, context);
        }

        return template;
    }

}
