package com.szypula.github.githubscreen.list.domain.usecase

import com.szypula.github.githubscreen.list.domain.dao.UsersDAO
import com.szypula.github.githubscreen.list.domain.model.UserDomainModel
import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import spock.lang.Specification
import spock.lang.Unroll

class GetAllUsersUseCaseTest extends Specification {

    UsersDAO usersDAOMock;

    GetAllUsersUseCase tested

    void setup() {
        usersDAOMock = Mock(UsersDAO)
        tested = new GetAllUsersUseCase(Schedulers.immediate(), usersDAOMock)
    }

    @Unroll
    def "execute should sort users"() {
        given:
        def testSubscriber = new TestSubscriber<List<UserDomainModel>>()
        and:
        usersDAOMock.getAll() >> Observable.just(users)

        when:
        tested.execute().subscribe(testSubscriber)

        then:
        testSubscriber.assertValue(sortedUsers)

        where:
        users                                                  | sortedUsers
        []                                                     | []
        [domainModel("A")]                                     | [domainModel("A")]
        [domainModel("A"), domainModel("B"), domainModel("C")] | [domainModel("A"), domainModel("B"), domainModel("C")]
        [domainModel("B"), domainModel("A"), domainModel("C")] | [domainModel("A"), domainModel("B"), domainModel("C")]
        [domainModel("C"), domainModel("B"), domainModel("A")] | [domainModel("A"), domainModel("B"), domainModel("C")]
        [domainModel("C"), domainModel("A"), domainModel("B")] | [domainModel("A"), domainModel("B"), domainModel("C")]
    }

    def domainModel(String id) {
        UserDomainModel.builder().name("name" + id).logoURL("").code("code" + id).build()
    }
}
