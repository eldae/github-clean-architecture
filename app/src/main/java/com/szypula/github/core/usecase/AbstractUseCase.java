package com.szypula.github.core.usecase;

import rx.Observable;
import rx.Scheduler;

public abstract class AbstractUseCase<T> implements UseCase<T> {

    private final Scheduler executionScheduler;

    public AbstractUseCase(Scheduler executionScheduler) {
        this.executionScheduler = executionScheduler;
    }

    protected abstract Observable<T> emit();

    @Override
    public Observable<T> execute() {
        return emit().subscribeOn(executionScheduler);
    }
}
