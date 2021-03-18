package momomo.com.db;

import momomo.com.Lambda;
import org.hibernate.Transaction;

import java.sql.Connection;

import static momomo.com.db.$TransactionOptions.Propagation.SUPPORT;

/**
 * Instances created by {@link $TransactionalHibernate}
 * 
 * @author Joseph S.
 */
public class $TransactionOptionsHibernate extends $TransactionOptions<$TransactionHibernate, $TransactionOptionsHibernate> {
    final $TransactionalHibernate outer;
    
    /////////////////////////////////////////////////////////////////////
    // The options
    /////////////////////////////////////////////////////////////////////
    
    Lambda.V1E<Connection, ?> withConnection;
    
    public void withConnection(Lambda.V1E<Connection, ?> lambda) {
        this.withConnection = lambda;
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////

    public $TransactionOptionsHibernate($TransactionalHibernate outer) {
        this.outer = outer;
    }

    @Override
    public $TransactionHibernate create() {
        $TransactionManagerHibernate manager = outer.transactionManager( Propagation.NEW.equals(propagation) );
        
        if ( !manager.hasActiveTransaction() ) {
            
            // Set only if readOnly is not already set, propagation support default is readOnly = true 
            if ( readOnly == null && SUPPORT.equals(propagation) ) {
                super.readOnly(true);
            }
            
            // Not possible to update these if there is already an ongoing transaction
            manager.withConnection(connection -> {
                if ( isolation != null ) {
                    connection.setTransactionIsolation(isolation.delegate);
                }
        
                if ( readOnly != null ) {
                    connection.setReadOnly(readOnly);
                }
            });
        }
    
        // Execute user supplied withConnection if any
        if ( withConnection != null ) {
            manager.withConnection(withConnection);
        }
    
        // Has to come after withConnection
        Transaction transaction = manager.getTransaction();
        
        if ( timeout != null ) {
            transaction.setTimeout(timeout);
        }
    
        // Some settings has to be applied before transaction begin, and some after it has been created, so we do this here.
        if ( !transaction.isActive() ) {
            transaction.begin();
        }
    
        return new $TransactionHibernate(manager, transaction);
    }
    
}
