package com.szypula.github.githubscreen.detail.domain.usecase

import com.szypula.github.githubscreen.detail.domain.dao.UserDetailDAO
import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel
import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import spock.lang.Specification

class GetUserDetailUseCaseTest extends Specification {

    UserDetailDAO userDetailDAOMock
    String code

    GetUserDetailUseCase tested

    void setup() {
        userDetailDAOMock = Mock(UserDetailDAO)
        code = "code"

        tested = new GetUserDetailUseCase(Schedulers.immediate(), userDetailDAOMock, code)
    }

    def "execute should return user detail"() {
        given:
        def testSubscriber = new TestSubscriber<UserDetailDomainModel>()
        and:
        userDetailDAOMock.getUserDetail(code) >> Observable.just(domainModel())

        when:
        tested.execute().subscribe(testSubscriber)

        then:
        testSubscriber.assertValue(domainModel())
    }

    def domainModel() {
        UserDetailDomainModel.builder()
                .code(code)
                .name("")
                .logoUrl("")
                .site("")
                .build();
    }
}
