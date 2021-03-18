package momomo.com.db;

import org.hibernate.Transaction;

/**
 * Instances created by {@link $TransactionOptionsHibernate}
 * 
 * @author Joseph S. 
 */
public class $TransactionHibernate extends $Transaction<$TransactionHibernate> {
    protected final $TransactionManagerHibernate manager;
    protected final Transaction                  delegate;

    protected $TransactionHibernate($TransactionManagerHibernate manager, Transaction transaction) {
        this.manager  = manager;
        this.delegate = transaction;
    }

    @Override
    protected void $commit$() {
        manager.commit(this);
    }

    @Override
    protected void $rollback$() {
        manager.rollback(this);
    }

}
