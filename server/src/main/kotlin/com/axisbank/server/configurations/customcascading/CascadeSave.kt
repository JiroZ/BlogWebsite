package com.axisbank.server.configurations.customcascading

import java.lang.annotation.RetentionPolicy


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class CascadeSave { //
}