package momomo.com.db;

import momomo.com.annotations.informative.Protected;
import momomo.com.db.sessionmanager.$SessionManagerNewRequire;
import org.hibernate.Session;

/**
 * @author Joseph S.
 */
public interface $TransactionalHibernate extends $Transactional<$TransactionHibernate, $TransactionOptionsHibernate>, $SessionManagerNewRequire {
    
    @Protected default $TransactionManagerHibernate transactionManager() {
        return transactionManager(session());
    }

    @Protected default $TransactionManagerHibernate transactionManager(boolean open) {
        return transactionManager(session(open));
    }

    @Protected default $TransactionManagerHibernate transactionManager(Session session) {
        return new $TransactionManagerHibernate(session);
    }

    @Protected @Override default $TransactionOptionsHibernate options() {
        return new $TransactionOptionsHibernate(this);
    }
}
