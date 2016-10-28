package com.szypula.github.githubscreen.list.view

import com.szypula.github.R
import com.szypula.github.githubscreen.list.domain.UserListInteractor
import com.szypula.github.githubscreen.list.domain.model.UserDomainModel
import com.szypula.github.githubscreen.list.domain.model.FavouriteDomainModel
import com.szypula.github.githubscreen.list.view.model.UserViewModel
import com.szypula.github.githubscreen.list.view.model.UserViewModelMapper
import rx.Observable
import rx.schedulers.Schedulers
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.TimeUnit

class UserListPresenterTest extends Specification {

    UserListInteractor interactorMock
    UserListMVP.View viewMock
    UserListPresenter tested

    void setup() {
        interactorMock = Mock(UserListInteractor)
        viewMock = Mock(UserListMVP.View)
        tested = new UserListPresenter(interactorMock, new UserViewModelMapper(), Schedulers.immediate())

        tested.injectView(viewMock)
    }

    def "present should turn favourite icon on"() {
        given:
        interactorMock.isFavouriteOnlyEnabled() >> true
        and:
        interactorMock.users >> Observable.empty()
        interactorMock.observeFavouriteChanges() >> Observable.empty()

        when:
        tested.present()

        then:
        1 * viewMock.turnFavouriteIconOn()
    }

    def "present should turn favourite icon off"() {
        given:
        interactorMock.isFavouriteOnlyEnabled() >> false
        and:
        interactorMock.users >> Observable.empty()
        interactorMock.observeFavouriteChanges() >> Observable.empty()

        when:
        tested.present()

        then:
        1 * viewMock.turnFavouriteIconOff()
    }

    def "present should show loading"() {
        given:
        interactorMock.users >> Observable.empty()
        interactorMock.observeFavouriteChanges() >> Observable.empty()

        when:
        tested.present()

        then:
        1 * viewMock.showLoading()
    }

    def "present should show user list"() {
        given: "interactor returned user list"
        def userList = []
        interactorMock.users >> Observable.just(userList)
        and:
        interactorMock.observeFavouriteChanges() >> Observable.empty()

        when:
        tested.present()

        then:
        1 * viewMock.showUserList(userList)
    }

    def "present should notify view that items were added"() {
        given: "interactor returned user list"
        def userList = []
        interactorMock.users >> Observable.just(userList)
        and:
        interactorMock.observeFavouriteChanges() >> Observable.empty()

        when:
        tested.present()

        then:
        1 * viewMock.onItemsAdded()
    }

    def "present should hide loading"() {
        given: "interactor returned user list"
        def userList = []
        interactorMock.users >> Observable.just(userList)
        and:
        interactorMock.observeFavouriteChanges() >> Observable.empty()

        when:
        tested.present()

        then:
        1 * viewMock.hideLoading()
    }

    def "present should hide loading when error occurred"() {
        given:
        interactorMock.users >> Observable.error(new Exception("error"))
        and:
        interactorMock.observeFavouriteChanges() >> Observable.empty()

        when:
        tested.present()

        then:
        1 * viewMock.hideLoading()
    }

    def "present should show error"() {
        given:
        interactorMock.users >> Observable.error(new Exception("error"))
        and:
        interactorMock.observeFavouriteChanges() >> Observable.empty()

        when:
        tested.present()

        then:
        1 * viewMock.showError("error")
    }

    def "present should showUserList when user was added to favourites"() {
        given:
        interactorMock.isFavouriteOnlyEnabled() >> true
        interactorMock.observeFavouriteChanges() >> Observable.just(new FavouriteDomainModel("codeA", true))
        and:
        interactorMock.users >> Observable.just([]) >> Observable.just([domainModelFav("A")])
        and:
        viewMock.currentList >> []

        when:
        tested.present()

        then:
        1 * viewMock.showUserList([])
        then:
        1 * viewMock.showUserList([viewModel("A")])
    }

    @Unroll
    def "present should notify view that user was added on position #position"() {
        given:
        interactorMock.isFavouriteOnlyEnabled() >> true
        interactorMock.observeFavouriteChanges() >> Observable.just(new FavouriteDomainModel("codeC", true))
        and:
        interactorMock.users >> Observable.just([]) >> Observable.just(newList)
        and:
        viewMock.currentList >> currentList

        when:
        tested.present()

        then:
        1 * viewMock.onItemAdded(position)

        where:
        currentList                      | newList                                                         | position
        []                               | [domainModelFav("C")]                                           | 0
        [viewModel("A")]                 | [domainModelFav("A"), domainModelFav("C")]                      | 1
        [viewModel("A"), viewModel("D")] | [domainModelFav("A"), domainModelFav("C"), domainModelFav("E")] | 1
        [viewModel("D"), viewModel("E")] | [domainModelFav("C"), domainModelFav("D"), domainModelFav("E")] | 0
        [viewModel("A"), viewModel("B")] | [domainModelFav("A"), domainModelFav("B"), domainModelFav("C")] | 2
    }

    def "present should showUserList when user was removed from favourites"() {
        given:
        interactorMock.isFavouriteOnlyEnabled() >> true
        interactorMock.observeFavouriteChanges() >> Observable.just(new FavouriteDomainModel("codeA", false))
        and:
        interactorMock.users >> Observable.just([domainModelUnFav("A")]) >> Observable.just([])
        and:
        viewMock.currentList >> [viewModel("A")]

        when:
        tested.present()

        then:
        1 * viewMock.showUserList([viewModel("A")])
        then:
        1 * viewMock.showUserList([])
    }

    @Unroll
    def "present should notify view that user was removed on position #position"() {
        given:
        interactorMock.isFavouriteOnlyEnabled() >> true
        interactorMock.observeFavouriteChanges() >> Observable.just(new FavouriteDomainModel("codeC", false))
        and:
        interactorMock.users >> Observable.just([]) >> Observable.just(newList)
        and:
        viewMock.currentList >> currentList

        when:
        tested.present()

        then:
        1 * viewMock.onItemRemoved(position)

        where:
        currentList                                      | newList                                        | position
        [viewModel("C")]                                 | []                                             | 0
        [viewModel("A"), viewModel("C")]                 | [domainModelUnFav("A")]                        | 1
        [viewModel("A"), viewModel("C"), viewModel("E")] | [domainModelUnFav("A"), domainModelUnFav("D")] | 1
        [viewModel("C"), viewModel("D"), viewModel("E")] | [domainModelUnFav("D"), domainModelUnFav("E")] | 0
        [viewModel("A"), viewModel("B"), viewModel("C")] | [domainModelUnFav("A"), domainModelUnFav("B")] | 2
    }

    def "present should show error when there was problem with toggling favourite"() {
        given:
        interactorMock.isFavouriteOnlyEnabled() >> true
        interactorMock.observeFavouriteChanges() >> Observable.error(new Exception("error"))
        and:
        interactorMock.users >> Observable.just([])

        when:
        tested.present()

        then:
        1 * viewMock.showError("error")
    }

    def "unsubscribe should unsubscribe from users subscription"() {
        given:
        interactorMock.users >> Observable.empty().delay(10, TimeUnit.SECONDS)
        interactorMock.observeFavouriteChanges() >> Observable.empty()
        and:
        tested.present()

        when:
        tested.unsubscribe()

        then:
        tested.getUsersSubscription.isUnsubscribed()
    }

    def "unsubscribe should unsubscribe from favourite change subscription"() {
        given:
        interactorMock.users >> Observable.empty()
        interactorMock.observeFavouriteChanges() >> Observable.empty().delay(10, TimeUnit.SECONDS)
        and:
        tested.present()

        when:
        tested.unsubscribe()

        then:
        tested.favouriteChangesSubscription.isUnsubscribed()
    }

    def "handleMenuClick should turn favourite icon on"() {
        given:
        interactorMock.isFavouriteOnlyEnabled() >> true
        and:
        interactorMock.users >> Observable.empty()

        when:
        tested.handleMenuClick(R.id.action_favorite)

        then:
        viewMock.turnFavouriteIconOn()
    }

    def "handleMenuClick should turn favourite icon off"() {
        given:
        interactorMock.isFavouriteOnlyEnabled() >> false
        and:
        interactorMock.users >> Observable.empty()

        when:
        tested.handleMenuClick(R.id.action_favorite)

        then:
        viewMock.turnFavouriteIconOff()
    }

    @Unroll
    @SuppressWarnings("GroovyAssignabilityCheck")
    def "handleMenuClick should return #result"() {
        given:
        interactorMock.users >> Observable.empty()

        expect:
        tested.handleMenuClick(menuItemId as int) == result

        where:
        menuItemId           | result
        R.id.action_favorite | true
        -1                   | false
    }

    def domainModelFav(String id) {
        UserDomainModel.builder().name("name" + id).logoURL("").code("code" + id).isFavourite(true).build()
    }

    def domainModelUnFav(String id) {
        UserDomainModel.builder().name("name" + id).logoURL("").code("code" + id).isFavourite(false).build()
    }

    def viewModel(String id) {
        UserViewModel.builder().name("name" + id).logoUrl("").code("code" + id).build()
    }
}
