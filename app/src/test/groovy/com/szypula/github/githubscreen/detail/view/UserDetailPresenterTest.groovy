package com.szypula.github.githubscreen.detail.view

import com.szypula.github.githubscreen.detail.domain.UserDetailInteractor
import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel
import com.szypula.github.githubscreen.detail.view.model.UserDetailViewModelMapper
import rx.Observable
import rx.schedulers.Schedulers
import spock.lang.Specification
import spock.lang.Unroll

class UserDetailPresenterTest extends Specification {

    UserDetailMVP.View viewMock
    UserDetailInteractor interactorMock

    UserDetailPresenter tested

    String name
    String logoUrl
    String site
    String phone
    boolean isFavourite

    void setup() {
        viewMock = Mock(UserDetailMVP.View)
        interactorMock = Mock(UserDetailInteractor)

        tested = new UserDetailPresenter(interactorMock, new UserDetailViewModelMapper(), Schedulers.immediate())
        tested.injectView(viewMock)

        name = "name"
        logoUrl = "logoUrl"
        site = "site"
        phone = "phone"
        isFavourite = false
    }

    def "present should show logo"() {
        given:
        interactorMock.user >> Observable.just(domainModel())

        when:
        tested.present()

        then:
        1 * viewMock.showLogo(logoUrl)
    }

    def "present should show name"() {
        given:
        interactorMock.user >> Observable.just(domainModel())

        when:
        tested.present()

        then:
        1 * viewMock.showName(name)
    }

    def "present should show website"() {
        given:
        interactorMock.user >> Observable.just(domainModel())

        when:
        tested.present()

        then:
        1 * viewMock.showWebsite(site)
    }

    @Unroll
    def "present should hide website for website = #result"() {
        given:
        site = result
        interactorMock.user >> Observable.just(domainModel())

        when:
        tested.present()

        then:
        1 * viewMock.hideWebsite()

        where:
        result << ["", null]
    }

    def "present should show is favourite"() {
        given:
        isFavourite = true
        interactorMock.user >> Observable.just(domainModel())

        when:
        tested.present()

        then:
        1 * viewMock.showIsFavourite()
    }

    def "present should show is not favourite"() {
        given:
        isFavourite = false
        interactorMock.user >> Observable.just(domainModel())

        when:
        tested.present()

        then:
        1 * viewMock.showIsNotFavourite()
    }

    def "present should show error"() {
        given:
        interactorMock.user >> Observable.error(new Exception("error"))

        when:
        tested.present()

        then:
        1 * viewMock.showError("error")
    }

    def "toggleFavourite should show is favourite"() {
        given:
        interactorMock.toggleFavourite() >> Observable.just(true)

        when:
        tested.toggleFavourite()

        then:
        1 * viewMock.showIsFavourite()
    }

    def "toggleFavourite should show is not favourite"() {
        given:
        interactorMock.toggleFavourite() >> Observable.just(false)

        when:
        tested.toggleFavourite()

        then:
        1 * viewMock.showIsNotFavourite()
    }

    def domainModel() {
        UserDetailDomainModel.builder()
                .name(name)
                .logoUrl(logoUrl)
                .site(site)
                .isFavourite(isFavourite)
                .build();
    }
}
