package com.szypula.github.githubscreen.list.domain

import com.szypula.github.core.usecase.UseCase
import spock.lang.Specification
import spock.lang.Unroll

class UserListInteractorImplTest extends Specification {

    UseCase getAllUsersUseCaseMock
    UseCase getFavouriteUsersUseCaseMock
    UseCase observeToggleFavouriteUseCaseMock

    UserListInteractorImpl tested

    void setup() {
        getAllUsersUseCaseMock = Mock(UseCase)
        getFavouriteUsersUseCaseMock = Mock(UseCase)
        observeToggleFavouriteUseCaseMock = Mock(UseCase)

        tested = new UserListInteractorImpl(getAllUsersUseCaseMock, getFavouriteUsersUseCaseMock, observeToggleFavouriteUseCaseMock)
    }

    def "getUsers should get all users"() {
        given:
        tested.isFavouriteOnlyEnabled = false

        when:
        tested.getUsers()

        then:
        1 * getAllUsersUseCaseMock.execute()
    }

    def "getUsers should get favourite users"() {
        given:
        tested.isFavouriteOnlyEnabled = true

        when:
        tested.getUsers()

        then:
        1 * getFavouriteUsersUseCaseMock.execute()
    }

    @Unroll
    def "toggleFavouriteEnabled should set isFavouriteOnlyEnabled to #result"() {
        given:
        tested.isFavouriteOnlyEnabled = isEnabled

        when:
        tested.toggleFavouriteEnabled()

        then:
        tested.isFavouriteOnlyEnabled == result

        where:
        isEnabled | result
        true      | false
        false     | true
    }

    @Unroll
    def "toggleFavouriteEnabled should return #isEnabled"() {
        given:
        tested.isFavouriteOnlyEnabled = isEnabled

        expect:
        tested.isFavouriteOnlyEnabled() == isEnabled

        where:
        isEnabled << [true, false]
    }

    def "observeFavouriteChanges should execute observeToggleFavourite use case"() {
        when:
        tested.observeFavouriteChanges()

        then:
        1 * observeToggleFavouriteUseCaseMock.execute()
    }
}
