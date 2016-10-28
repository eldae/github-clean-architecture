package com.szypula.github.githubscreen.detail.domain.usecase

import com.szypula.github.githubscreen.detail.domain.dao.FavouritesDetailDAO
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import spock.lang.Specification
import spock.lang.Unroll

class ToggleFavouriteUseCaseTest extends Specification {

    FavouritesDetailDAO favouritesDetailDAOMock
    String code

    ToggleFavouriteUseCase tested

    void setup() {
        favouritesDetailDAOMock = Mock(FavouritesDetailDAO)
        code = "code"

        tested = new ToggleFavouriteUseCase(Schedulers.immediate(), favouritesDetailDAOMock, code)
    }

    def "execute should remove favourite"() {
        given:
        favouritesDetailDAOMock.isFavourite(code) >> true

        when:
        tested.execute()

        then:
        1 * favouritesDetailDAOMock.removeFavourite(code)
    }

    def "execute should add favourite"() {
        given:
        favouritesDetailDAOMock.isFavourite(code) >> false

        when:
        tested.execute()

        then:
        1 * favouritesDetailDAOMock.addFavourite(code)
    }

    @Unroll
    def "execute should emit #result"() {
        given:
        def testSubscriber = new TestSubscriber<Boolean>()
        and:
        favouritesDetailDAOMock.isFavourite(code) >> isFavourite


        when:
        tested.execute().subscribe(testSubscriber)

        then:
        testSubscriber.assertValue(result)

        where:
        isFavourite | result
        true        | false
        false       | true
    }
}
