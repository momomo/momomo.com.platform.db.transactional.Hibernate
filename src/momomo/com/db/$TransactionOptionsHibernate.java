package momomo.com.db;

import momomo.com.Lambda;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;

import java.sql.Connection;

import static momomo.com.db.$TransactionOptions.Propagation.NEW;
import static momomo.com.db.$TransactionOptions.Propagation.SUPPORT;

/**
 * Instances created by {@link $TransactionalHibernate}
 *
 * @author Joseph S.
 */
public class $TransactionOptionsHibernate extends $TransactionOptions<$TransactionOptionsHibernate, $TransactionHibernate> {
    final $TransactionalHibernate outer;
    
    /////////////////////////////////////////////////////////////////////
    // The options
    /////////////////////////////////////////////////////////////////////
    
    Lambda.V1E<Connection, ?> withConnection;
    
    public $TransactionOptionsHibernate withConnection(Lambda.V1E<Connection, ?> lambda) {
        this.withConnection = lambda; return THIS();
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    public $TransactionOptionsHibernate($TransactionalHibernate outer) {
        this.outer = outer;
    }
    
    @Override
    public $TransactionHibernate create() {
        return create(propagation);
    }
    
    /**
     * Just so we can recurse
     */
    protected $TransactionHibernate create(Propagation propagation) {
        $TransactionManagerHibernate manager     = outer.transactionManager(NEW.equals(propagation));
        Transaction                  transaction = manager.transaction();
        boolean                      isNew       = !transaction.isActive();
        
        if ( isNew ) {
            // Set only if readOnly is not already set, propagation support default is readOnly = true 
            if ( readOnly == null && SUPPORT.equals(propagation) ) {
                super.readOnly(true);
            }
            
            // Not possible to update these if there is already an ongoing transaction so we need to do it before we transaction.begin()
            manager.withConnection(connection -> {
                if (isolation != null) {
                    connection.setTransactionIsolation(isolation.delegate);
                }
                
                if (readOnly != null) {
                    connection.setReadOnly(readOnly);
                }
            });
        }
        
        // Not new, we need to double check that this already active transaction is not read only when we actually invoked requireTransaction, and thus requiring a write capable transaction
        else if ( !SUPPORT.equals(propagation) && manager.isReadOnly() ) {
            // Either require or new for propagation and we are already readonly
            // Can only be resolved through a new session.
            return create(NEW);
        }
        
        // Execute user supplied withConnection if any
        if ( withConnection != null ) {
            manager.withConnection(withConnection);
        }
        
        // Has to come after withConnection
        // Transaction transaction = manager.getTransaction();
        
        if ( timeout != null ) {
            transaction.setTimeout(timeout);
        }
        
        // Some settings has to be applied before transaction begin, and some after it has been created, so we do this here.
        if ( isNew ) {
            transaction.begin();
        }
        
        return new $TransactionHibernate(manager, transaction, isNew);
    }
    
    private void test($TransactionManagerHibernate manager) {
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) manager.delegate.getSessionFactory();
    
        // With our changes a and b should be the same
        
        Session a = sessionFactory.openSession();
        Session b = sessionFactory.getCurrentSession();
    
        // With our changes c and d should be the same
        
        Session c = sessionFactory.openSession();
        Session d = sessionFactory.getCurrentSession();
    }
    
}
