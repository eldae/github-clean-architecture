package com.szypula.github.githubscreen.list.domain.usecase

import com.szypula.github.githubscreen.list.domain.dao.UsersDAO
import com.szypula.github.githubscreen.list.domain.model.UserDomainModel
import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import spock.lang.Specification

class GetFavouriteUsersUseCaseTest extends Specification {

    UsersDAO usersDAOMock;
    GetFavouriteUsersUseCase tested;

    void setup() {
        usersDAOMock = Mock(UsersDAO)
        tested = new GetFavouriteUsersUseCase(Schedulers.immediate(), usersDAOMock)
    }

    def "execute should return only favourite sorted users"() {
        given:
        def testSubscriber = new TestSubscriber<List<UserDomainModel>>()
        and:
        usersDAOMock.getAll() >> Observable.just(users)

        when:
        tested.execute().subscribe(testSubscriber)

        then:
        testSubscriber.assertValue(sortedUsers)

        where:
        users                                                                 | sortedUsers
        [domainModelUnFav("A")]                                               | []
        [domainModelFav("A")]                                                 | [domainModelFav("A")]
        [domainModelFav("A"), domainModelUnFav("B"), domainModelFav("C")]     | [domainModelFav("A"), domainModelFav("C")]
        [domainModelUnFav("B"), domainModelUnFav("A"), domainModelUnFav("C")] | []
        [domainModelFav("C"), domainModelFav("B"), domainModelFav("A")]       | [domainModelFav("A"), domainModelFav("B"), domainModelFav("C")]
    }

    def domainModelFav(String id) {
        UserDomainModel.builder().name("name" + id).logoURL("").code("code" + id).isFavourite(true).build()
    }

    def domainModelUnFav(String id) {
        UserDomainModel.builder().name("name" + id).logoURL("").code("code" + id).isFavourite(false).build()
    }
}
