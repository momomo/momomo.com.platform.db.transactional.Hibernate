package momomo.com.db;

import momomo.com.Ex;
import momomo.com.Lambda;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;

public final class $TransactionManagerHibernate implements $TransactionManager<$TransactionHibernate, $TransactionOptionsHibernate> {
    protected final Session delegate;

    public $TransactionManagerHibernate(Session delegate) {
        this.delegate = delegate;
    }

    @Override
    public void commit($TransactionHibernate transaction) {
        transaction.delegate.commit();
    }

    @Override
    public void rollback($TransactionHibernate transaction) {
        transaction.delegate.rollback();
    }

    public Transaction getTransaction() {
        return delegate.getTransaction();
    }
    
    public void withConnection(Lambda.V1E<Connection, ?> lambda) {
        delegate.doWork(connection -> {
            try {
                lambda.call(connection);
            } catch (Throwable e) {
                throw Ex.runtime(e);
            }
        });
    }
    
    public Boolean isReadOnly() {
        return delegate.doReturningWork(c -> {
            return c.isReadOnly();
        });
    }
}
