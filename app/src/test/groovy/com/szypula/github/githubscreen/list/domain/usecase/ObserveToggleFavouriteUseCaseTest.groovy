package com.szypula.github.githubscreen.list.domain.usecase

import com.szypula.github.githubscreen.list.domain.dao.FavouriteDAO
import com.szypula.github.githubscreen.list.domain.model.FavouriteDomainModel
import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import spock.lang.Specification

class ObserveToggleFavouriteUseCaseTest extends Specification {

    FavouriteDAO favouriteDAOMock

    ObserveToggleFavouriteUseCase tested

    void setup() {
        favouriteDAOMock = Mock(FavouriteDAO)

        tested = new ObserveToggleFavouriteUseCase(Schedulers.immediate(), favouriteDAOMock)
    }

    def "execute should return correct model"() {
        given:
        def testSubscriber = new TestSubscriber<FavouriteDomainModel>()
        and:
        def favourite = new FavouriteDomainModel("code", true)
        favouriteDAOMock.favourite() >> Observable.just(favourite)

        when:
        tested.execute().subscribe(testSubscriber)

        then:
        testSubscriber.assertValue(favourite)
    }
}
