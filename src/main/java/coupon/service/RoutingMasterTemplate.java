package coupon.service;

import coupon.repository.RepositoryMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RoutingMasterTemplate {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T apply(RepositoryMethod<T> repositoryMethod) {
        return repositoryMethod.execute();
    }

}
