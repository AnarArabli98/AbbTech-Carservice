package com.abbtech.annotation;

public @interface SpringTransactionAnnotation {
    Class<? extends Throwable>[] rollbackFor();
    boolean readOnly();
}
