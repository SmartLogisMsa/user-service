package com.smartlogis.userservice.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smartlogis.userservice.presentation.annotation.EnumValid;

import io.swagger.v3.oas.models.media.Schema;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenApiCustomizer schemaCustomizer() {
		return openApi -> openApi.getComponents().getSchemas().forEach((schemaName, schema) -> {
			if (schema.getProperties() == null) return;

			schema.getProperties().forEach((name, value) -> {
				if (! (value instanceof Schema<?>)) return;
				applyEnum(schemaName, name.toString(), (Schema<?>) value);
			});
		});
	}

	private void applyEnum(String schema, String name, Schema<?> value) {
		try {
			Class<?> dtoClass = Class.forName(schema);

			var field = Arrays.stream(dtoClass.getDeclaredFields())
				.filter(f -> f.getName().equals(name))
				.findFirst()
				.orElse(null);

			if (field == null || !field.isAnnotationPresent(EnumValid.class)) return;

			Class<? extends Enum<?>> enumClass = field.getAnnotation(EnumValid.class).enumClass();

			List<String> values = Arrays.stream(enumClass.getEnumConstants())
				.map(Enum::name)
				.collect(Collectors.toList());

			if ("array".equals(value.getType()) && value.getItems() != null) {
				@SuppressWarnings("unchecked")
				Schema<String> itemSchema = (Schema<String>) value.getItems();
				itemSchema.setEnum(values);
			} else {
				@SuppressWarnings("unchecked")
				Schema<String> stringSchema = (Schema<String>) value;
				stringSchema.setEnum(values);
			}
		} catch (ClassNotFoundException e) {}
	}
}
