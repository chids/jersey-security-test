package org.github.chids.jersey.security.sample;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebListener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.container.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

@WebListener
public class GuiceConfiguration extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServletModule() {
			@Override
			protected void configureServlets() {
				filter("/*").through(GuiceContainer.class, createFilterParams());
			}
		});
	}

	@SuppressWarnings("serial")
	protected Map<String, String> createFilterParams() {
		return new HashMap<String, String>() {
			{
				put(PackagesResourceConfig.PROPERTY_PACKAGES, Resource.class.getPackage().getName());
				put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS, GZIPContentEncodingFilter.class.getName());
				put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, GZIPContentEncodingFilter.class.getName());
				put(ResourceConfig.FEATURE_DISABLE_WADL, Boolean.toString(false));
				put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES, RolesAllowedResourceFilterFactory.class.getName());
			}
		};
	}
}