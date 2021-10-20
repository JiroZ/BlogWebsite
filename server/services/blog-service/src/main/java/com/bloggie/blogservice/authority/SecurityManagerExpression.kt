package com.bloggie.blogservice.authority

import org.springframework.security.access.expression.SecurityExpressionRoot
import org.springframework.security.core.Authentication

class SecurityManagerExpression(authentication: Authentication) : SecurityExpressionRoot(authentication) {


}