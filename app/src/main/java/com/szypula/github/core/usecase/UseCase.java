package com.szypula.github.core.usecase;

import rx.Observable;

public interface UseCase<T> {

    Observable<T> execute();
}
