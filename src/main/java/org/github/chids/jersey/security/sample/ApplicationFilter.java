package org.github.chids.jersey.security.sample;

import javax.annotation.security.DeclareRoles;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebFilter;

import com.google.inject.servlet.GuiceFilter;

@WebFilter("/*")
@DeclareRoles("user")
@ServletSecurity()
public class ApplicationFilter extends GuiceFilter {}