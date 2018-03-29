package com.hao.lib.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Yang Shihao
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
