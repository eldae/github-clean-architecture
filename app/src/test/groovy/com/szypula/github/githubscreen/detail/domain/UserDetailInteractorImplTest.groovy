package com.szypula.github.githubscreen.detail.domain

import com.szypula.github.core.usecase.UseCase
import spock.lang.Specification

class UserDetailInteractorImplTest extends Specification {

    UseCase getUserDetailUseCaseMock
    UseCase toggleFavouriteUseCaseMock

    UserDetailInteractorImpl tested

    void setup() {
        getUserDetailUseCaseMock = Mock(UseCase)
        toggleFavouriteUseCaseMock = Mock(UseCase)

        tested = new UserDetailInteractorImpl(getUserDetailUseCaseMock, toggleFavouriteUseCaseMock)
    }

    def "getUser should execute getUserDetail use case"() {
        when:
        tested.getUser()

        then:
        1 * getUserDetailUseCaseMock.execute()
    }

    def "toggleFavourite should execute toggleFavourite use case"() {
        when:
        tested.toggleFavourite()

        then:
        1 * toggleFavouriteUseCaseMock.execute()
    }
}
