package coupon.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class TransactionHandler {

    private final TransactionTemplate transactionTemplate;

    public TransactionHandler(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public <T> T runInNewTransaction(int transactionDefinition, TransactionalTask<T> task) {
        int originalPropagationBehavior = transactionTemplate.getPropagationBehavior();

        try {
            transactionTemplate.setPropagationBehavior(transactionDefinition);
            return transactionTemplate.execute(status -> task.run());
        } finally {
            transactionTemplate.setPropagationBehavior(originalPropagationBehavior);
        }
    }

    @FunctionalInterface
    public interface TransactionalTask<T> {
        T run();
    }
}
