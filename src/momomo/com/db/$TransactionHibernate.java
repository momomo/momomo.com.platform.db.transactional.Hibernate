package momomo.com.db;

import org.hibernate.Transaction;

/**
 * Instances created by {@link $TransactionOptionsHibernate}
 * 
 * @author Joseph S. 
 */
public class $TransactionHibernate extends $Transaction<$TransactionHibernate> {
    public final $TransactionManagerHibernate manager;
    public final Transaction                  delegate;

    protected $TransactionHibernate($TransactionManagerHibernate outer, Transaction delegate, boolean isNew) {
        super(outer, isNew ? null : Boolean.FALSE );
        
        this.manager  = outer;
        this.delegate = delegate;
    }
    
    /**
     * To grant access to the underlying transaction should there be a need
     */
    public Transaction delegate() {
        return delegate;
    }
    
    /**
     * To grant access to our 'transaction manager' from a transaction if there is ever a need
     */
    public $TransactionManagerHibernate manager() {
        return manager;
    }
    
    @Override
    public String toString() {
        return getClass().getName() + "@" + System.identityHashCode(manager.delegate());
    }
}
