<!----
-->

##### A library to execute database command in transactions without having to use annotations based on Hibernate libraries. No Spring! 

##### Dependencies 
* **[`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Core)** 
* **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)**
* **[`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional)**

##### Maven dependencies available on maven central [search.maven.org](https://search.maven.org/search?q=com.momomo)
##### Dependency   
```xml
<dependency>
  <groupId>com.momomo</groupId>
  <artifactId>momomo.com.platform.db.base.transactional.Hibernate</artifactId>
  <version>2.1.8</version>
</dependency>                                                      
```                         
##### Repository
```xml
<repository>
    <id>maven-central</id>
    <url>http://repo1.maven.org/maven2</url>
</repository>
```        

##### Our other repositories                          

* **[`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Core)**  
Is essentially what makes the our the core of several of momomo.com's public releases and contains a bunch of Java utility.

* **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)**  
Contains a bunch of `functional interfaces` similar to `Runnable`, `Supplier`, `Function`, `BiFunction`, `Consumer` `...` and so forth all packed in a easily accessed and understood intuitive pattern.    
**`Lambda.V1E`**, **`Lambda.V2E`**, **`Lambda.R1E`**, **`Lambda.R2E`**, ...  are used plenty in our libraries.

* **[`momomo.com.platform.Return`](https://github.com/momomo/momomo.com.platform.Return)**  
An intuitive library that allows you to return multiple return values with defined types on the fly from any method rather than being limited to the default maximum of one.

* **[`momomo.com.platform.Nanotime`](https://github.com/momomo/momomo.com.platform.Nanotime)**  
Allows for nanosecond time resolution when asking for time from Java Runtime in contrast with **`System.currentTimeMillis()`**.

### Background

#### On [Lambda](https://github.com/momomo/momomo.com.platform.Lambda)

What makes this possible or even a reality is the existence of **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** which we've now had in our posession for over 10 years.  
There is nothing magical about it, yet something similar is lacking in presence in the Java world.    
   
Similar libraries might have recently made the scene but not quite as powerful nor as intuitive, we think. 

We finally released **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** to *actually* be able to release **[`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)** and **[`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring)**. 

**[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** is the most used and important library internally and is the essence and most important factor of what this library does. 

Sure we could have switched our lambdas to **`Supplier`**, **`Consumer`** and so forth, *whatever those names mean*, but to destroy our code just to make our code 100% Java organic?   Not a great idea. 

It is just a bunch of interface classes after all contained in one file, and *nothing expensive* for you to add on.

#### On Spring

Most of the industry today relies on the use of Spring **`@Transactional(propagation = Propagation.REQUIRES_NEW)`** and **`@Transactional(propagation = Propagation.REQUIRES)`** **`@Transactional(propagation = Propagation.SUPPORTS)`**.  

We did too, and frequently run into several very common issues. 
 
To get the *transactional features* using *Spring annotations* the code needs to be:
   
   &nbsp; a. Placed into a method of its own. So code has to be extracted and pollute the outer class scope.   
   &nbsp; &nbsp; &nbsp; Further, the method will have to declare a bunch of parametnanoers for you to pass whatever you needed in the previous scope.   
   &nbsp; &nbsp; &nbsp; Parameter and method pollution. Not great.  
   &nbsp;        
   &nbsp; b. The method has to be **`public`** and so can not be **`protected`** or **`private`** when that might be the appropiate choice.   
   &nbsp; &nbsp; &nbsp; A **`protected`** and **`private`** method tells your class, subclasses something and outer world something else. They are important as communicators.  
   &nbsp;    
   &nbsp; c. The method **has to be invoked** using Springs injected beans, so a class or 'service' can not even invoke its own method directly.  
   &nbsp; &nbsp; &nbsp; This is risky since someone might do the wrong thing, invoke the method, expect a certain behaviour and not get it.  
   &nbsp; &nbsp;   
   &nbsp; d. You need a spring managed bean or use them. You need to inject it whereever you need to get transactional.     
   &nbsp; &nbsp; &nbsp; Spring will hijack and show you stacktraces that are a frequent time waster to parse. Nothing is clear about them.   
   &nbsp; &nbsp; &nbsp; Your exception stacktraces look like a labyrinth and you spend time finding the tiny bug you have which is the most costly part of using Spring hands down since it hijacks your entire Java platform.        

Now this to us, introduces a bunch of issues as code is difficult enough to organize **well** using the standard restrictions the `Java` language has alone and is now affecting the way you can code further 
by forcing code that lives perfectly well within a method to be extracted and then somehow find itself to a Spring bean rather than stay where it is most relevant, where it was, just because you needed some code run in a transaction.
The extraction can not be **`private`** but now has to be made **`public`** and not only that, they can not be invoked directly unless invoked from the injected **`proxying`** bean.

Repeat this enough times, and your code starts to look like Chernobyl, all because you need to code the **`Spring`** way. **`Spring`** enforces a subset of Java to work well, rather than add to Java. It is therefore a lesser, limited version of Java. Start using Spring and all your code now has to use Spring to invoke anything.

Spring is not all that bad, there is plenty of material on how to setup things using Spring, which makes it easy to setup most of the time but it is not the dream of any hardcore Java developer.
   
You give up a lot of your Java powers to be on that ecosystem and once you start injecting or annotating, they got you hooked.   
Spring works hard to lock you into their eco system. So does Hibernate / RedHat / JBoss. They will all eventually sell you support and you will need it.
     
Spring is a bit kinder in their code though where Hibernate by default has **`private`** access on almost all of their code. That makes it hard to extend and/ord modify behaiviour, so Spring is not all that bad, but once you start injecting, good luck breaking loose. It took us years to do so and there is no reason to be injecting stuff in the first place, and the amount of restrictions that this entails is far too many to sacrifice. 

Our code, including the building of the **`SessionFactory`** and/or **`EntityManager`** as well as being able to run code in **transactions** can all be run from a **`static void main`**.
 
Can your Spring code do that? **`static void main`** only and run? **1 sec** launch?    
A `static void main` can startup in less than a `half a second` within your editor, while the injected stuff Spring pulls, requires a lot of ***orchestration***, an initiator, scanning and what not before allowing you do anything. 

Sure, you use **`Spring Boot`**, it runs in a **`static void main`** you say! Yes, but it starts a server to give you access and everything has to run through that. Not easy.  

#### On Hibernate
Now, using Hibernate as is, is good, however, not much flavour gets added. Spring actually adds a some neccessary functionality, and their better transaction / session manager.   
Hibernates **`transaction manager`** / **`sessionfactory`** is not great. Their **`thread`** implementation severely lacking. But we fixed its shortcomings.  
    
But most people do not even know how to setup Hibernate without Spring. Without **`XML`**. It is not an easy topic to find info about. 
See our **[`$SessionConfig.java`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfig.java)** implementation for that.   
It is not all that easy, nor is it easy to google anything on Hibernate and not get a Spring answer today. 
 
> Remember the Javascript answers on **`StackOverflow.com`** not long ago? They used to be all **`jQuery`** related.   
 
Creating transactions using Hibernate is possible but requires **programmatic insight** and is prone to repetition and redundancy which leads more potential errors and mistakes. 

Futher, we repeat the hiberante option **`thread`** or actually **`org.hibernate.context.internal.ThreadLocalSessionContext`** implementation is very simplictic in nature and severely limited in terms of capacity.
 
We have our own tweaked implementations, with long descriptive names  
   * **[`$SessionConfigContextListRecommended`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextListRecommended.java)** Prevents wrapping of **`openSession()`** calls as well as tracks them from **[`momomo.com.db.$SessionFactory`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/sessionFactory/$SessionFactory.java)** by adding the **`new session`** on top of a stack. When the **`new session`** completes, we remove it and the one previously on top becomes the one to be used for **`currentSession()`** / `requireSession()`** calls.
   
   * **[`$SessionConfigContextSingleCrazyInsane`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextSingleCrazyInsane.java)** Prevents wrapping of **`openSession()`** calls as well as tracks them from **[`momomo.com.db.$SessionFactory`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/sessionFactory/$SessionFactory.java)** by terminating / rolling back any previously attached `session` and eating away any exception possibly thrown as a result, logging it only instead of throwing the exception. 
   
   * **[`$SessionConfigContextSingleCrazySane`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextSingleCrazySane.java)** Prevents wrapping of **`openSession()`** calls as well as tracks them from **[`momomo.com.db.$SessionFactory`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/sessionFactory/$SessionFactory.java)** by terminating / rolling back any previously attached `session` by rolling it back yet throwing any exception that might arise rather than eating it.  
              
   * **[`$SessionConfigContextSingleCrazyLaxed`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextSingleCrazyLaxed.java)** Prevents wrapping of **`openSession()`** calls as well as tracks them from **[`momomo.com.db.$SessionFactory`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/sessionFactory/$SessionFactory.java)** by detaching previously tracked **`session`** silently, not terminating them but allowing them to continue to live, in hopes that the developer takes responsibility of manually terminating them instead.
      
   * **[`$SessionConfigContextUntrackedLeastRecommended`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextUntrackedLeastRecommended.java)** Prevents wrapping of **`openSession()`** calls but does not track **`openSession()`** calls like the other ones do because it uses Hibernates own **`ThreadLocalSessionContext`** where we basicaly just override **`ThreadLocalSessionContext`** to prevent the wrapping of the **`Session`** upon a call to **`currentSesssion()`** which for some reason by default returns a proxied, wrapped and limited **dumb proof** **`Session`** that's lacking on so many levels.
     
   Basically it will behave almost identical to **[`$SessionConfigContextSingleCrazyInsane`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextSingleCrazyInsane.java)** but here we ensure that the implementation used is Hibernates own but with two difference. 1) We only override their method to prevent wrapping. 2) **[`$SessionConfigContextSingleCrazyInsane`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextSingleCrazyInsane.java)** is tracked from **[`momomo.com.db.$SessionFactory`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/sessionFactory/$SessionFactory.java)**. This one is not.    

#### On this library

It is an easy to use, **annotation free**, **`Transactional API`**, that offers a vast amount of methods to **create**, or **reuse** transactions by either delegating to existing implementations in the case of **`Spring`** or add a bit of additional flavour in the case of **`Hibernate`** only version to match **`Spring's`** transaction capabilities. 

It is a fully customizable, flexible, overridable, optionable and all the other **able** thing the world has to offer. Simply put, it is very capable.    

What we've done is written a base **[`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional)** which is then *currently* implemented with two flavors
  
   1. **[`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring)** For those currently stuck or happy with Spring.      
   It makes use of Springs **`PlatformTransactionManager`** as the tool to perform your transactions using **our API** which basically setups the 
   transaction and then delegates it to Spring to make the transaction work as you intended. 
   &nbsp;  
   This works great and have been **tested extensively** as this library came before we switched to our now **Hibernate only** library to get off the injection, beans and annotation trail.
   
   2. **[`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)**  
      Our camp and our **recommendation to all new adopters**, and for those that do not want to use **`Spring`**.    
      
      We broke loose from Spring setting up our **`EntityManagerFactory`** which in reality just wraps a Hibernate **`SessionFactory`** as well as broke loose from relying on **`SpringPlatformTransactionManager`** 
      by basically writing our own transaction manager *(not that difficult)* using **`Hibernate`** **`Session`** transaction mechanisms. 
      
  3. ***Coming soon*** and already in a somewhat acceptable condition is the JDBC only transactional API. It already provides similar mechanisms, but is not fully developed as the other two.  
     See **[`momomo.com.db.$DatabaseTransactional`](https://github.com/momomo/momomo.com.platform.db.base/tree/master/src/momomo/com/db/$DatabaseTransactional.java)** for the current state of the JDBC version.  
     It is not a tit for tat implementation, but gives us a limited transactional API, that is similar.        

What we get is a unified **`Transactional API`** that can be setup and invoked from anyplace, and you could one day even switch from **`Spring`** to **`Hibernate`** and retain your functionality.

#### Is that all? 
Not at all. We also provide a bunch of other things, such as the simplicy of setting up your hibernate **`SessionFactory`**, as well as **`Migrations`** (think *liquibase*, *flyway*), as well as a bunch of 
session related utility related to entities, persisting, finding, saving, building and more.  

All of this can execute from a **`static void main`**. **Zero** xml. **Zero** complexity. 

----

### Getting started

The best way we can showcase things, is simply to show you the code that is fully functional and which you can download, navigate and/or run immediately yourself from our example application by visiting our **[`momomo.com.example.app.Crypto`](https://github.com/momomo/momomo.com.example.app.Crypto)** which details almost all of it, and can be checked out and includes a fully working and executable `static void main` where we setup database entities as well as demonstrate other parts of our database related libraries and their usage.  

Relevant extracts from the **[`momomo.com.example.app.Crypto`](https://github.com/momomo/momomo.com.example.app.Crypto)**


```java
// We demand a new transaction                                                                   

Crypto.repository.newTransaction(() -> {
    return super.save(entity); 
});
```

```java
// We demand the continuation of a transaction if there is one, or starting a new one if there is none
                                      
Crypto.repository.requireTransaction(() -> {
    return super.save(entity);
});                              
```    

```java                                                                                               
// A read only transaction if there is not an existing one already in which case we requireTransaction basically
 
List<Polkadot> = Crypto.repository.supportTransaction(() -> {
    return super.list(); 
});   
```

```java
// We can disable auto commit so we commit when we want or not at all

Crypto.repository.requireTransaction(tx -> {
    tx.autocommit(false);

    super.save(entity);

    tx.commit();
});
```                                               

```java
// Another way to disable automatic commit

Crypto.repository.requireTransaction(() -> {
    super.save(entity);
}, false /** commit false**/ );
```

```java
Crypto.repository.requireTransaction(tx -> {
    // We can hook in to do something once the commit is succesful

    tx.afterCommit(() -> {
        // Send email perhaps when we exit the transaction after succesfully committing!
 
        // We have now inserted the value in our database successfully!

        // We do not want to send the email unless we actually have 100% inserted the stuff so this is a handy method to use for that 
    });

    super.save(entity);
});
```

```java
// We can return something from inside the transaction

Etherum e = Crypto.repository.requireTransaction(() -> {
    return super.save(entity); // We repeat the return demo but by returning an entity
});
```

```java
// We can return again, showcasing that you can return anything really

String returns = Crypto.repository.requireTransaction(() -> {
    super.save(entity);

    return "we can return anything from the transactional lambda";
});
```

```java
// We do not need to use a lambda to get access to tx to execute things more freely                    

$TransactionHibernate tx = Crypto.repository.requireTransaction();
super.save(entity);
tx.autocommit(false);
tx.afterCommit   (()-> {});
tx.afterRollback (()-> { /* A crime has been committed! Report error to the FBI! */ });
tx.rollback();
tx.commit();
```                                     

```java
// Getting a transaction that we can pass around and execute manually with or without a lambda

$TransactionHibernate tx = Crypto.repository.requireOptions()
    .propagation($TransactionOptions.Propagation.NEW)
    .isolation($TransactionOptions.Isolation.REPEATABLE_READ)
    .timeout(1000)
    .create()
;                                     

tx.execute(()-> {
    save(entity);
});

tx.execute(()-> {
    save(entity);

    tx.commit();
}, false /** don't commit **/ );
```                 

```java
// We can rollback                                                          

Crypto.repository.newTransaction(tx -> {
    super.save(entity);

    tx.rollback();

    // note, now, the autocommit won't occur, as we have rolled back and basically discarded the entire transaction.  
});
```

```java
// We show that exceptions will bubble up to the caller
                                 
try {
    Crypto.repository.requireTransaction(() -> {
        throw new IOException();
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E) after rolling back. If there is a rollback exception, a $DatabaseRollbackException will be thrown instead. Will not commit.  
}
```

```java
// We can return as well as throwing exceptions

try {
    File file = Crypto.repository.requireTransaction(() -> {
        if ( false ) {
            throw new IOException();
        }
        return new File("");
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E) after rolling back. If there is a rollback exception, a $DatabaseRollbackException will be thrown instead. Will not commit.
}
```                                 

```java
// We can get access to the actual session and retain 100% control

Session s1 = Crypto.repository.requireSession();
Session s2 = Crypto.repository.newSession();
```                                            

```java
// We can build the transaction properties and set various things ourselves

Crypto.repository.requireOptions()
    .propagation($TransactionOptions.Propagation.NEW)
    .isolation($TransactionOptions.Isolation.REPEATABLE_READ)
    .timeout(1000)
    .create()
    .execute((tx)-> {
        tx.autocommit(false);
        tx.afterCommit(()-> {});
        tx.afterRollback(()-> {});

        // ... 
    })
;
```

```java
// Similar to previous example but without chaining the options. 
  
$TransactionOptionsHibernate options = Crypto.repository.requireOptions();
// ... options.propagation(...)
// ... options.create().execute(...)
```

```java
// More options made visibile
                                      
Crypto.repository.requireOptions()
    .timeout(1000)

    // Notice the withConnection option being used! Full access! 
    .withConnection((java.sql.Connection connection) -> {
        connection.setReadOnly(true);
        connection.setCatalog("catalog");
        connection.setTransactionIsolation(1);
        connection.clearWarnings();
        connection.createStatement();
        connection.setTypeMap(new HashMap<>());
        connection.setHoldability(1);
        connection.setSavepoint();
        // ...
    })
    .create()
    .execute(tx -> {
        tx.autocommit(false);

        save(entity);

        tx.commit();
    })
;
```                                           

```java
/**
 * Bunch of complex and nested transactions. 
 * 
 * Take a look at the comments within!
 */                             
public void populate(int multiplier) {
    newTransaction((tx1) -> {
        insert(Time.stamp(), 11);
        insert(Time.stamp(), 12);
        
        // Start a new transaction within
        newTransaction(tx2 -> {
            insert(Time.stamp(), -21);
            insert(Time.stamp(), -22);
            
            // Another one
            newTransaction(tx3 -> {
                insert(Time.stamp(), 31);
                insert(Time.stamp(), 32);
            });
            
            // Continue on the previous last active one (same as tx2) 
            requireTransaction(tx4 -> {
                insert(Time.stamp(), -41);
                insert(Time.stamp(), -42);
            });
            
            // Neither -21 ... or -41 ... will get into db since that tx rolledback
            tx2.rollback();
            
            // The same as tx1, no issues there. Should be in.   
            requireTransaction(tx5 -> {
                insert(Time.stamp(), 51);
                insert(Time.stamp(), 52);
                
                // The same as tx1 and tx5, no issues there. Should enter db.    
                requireTransaction(($TransactionHibernate tx6) -> {
                    insert(Time.stamp(), 61);
                    insert(Time.stamp(), 62);
                    
                    // New transaction, will be in    
                    newTransaction(($TransactionHibernate tx7) -> {
                        insert(Time.stamp(), 71);
                        insert(Time.stamp(), 72);
                    });
                    
                    // New transaction, won't be in since it is rolled back    
                    newTransaction(($TransactionHibernate tx8) -> {
                        insert(Time.stamp(), -81);
                        insert(Time.stamp(), -82);
                        
                        tx8.rollback();
                    });
                });
            });
        });
    });
}              
```

### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.
