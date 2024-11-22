<!----
-->

##### A library to execute database commands in transactions without  having to use annotations based on Hibernate libraries. No Spring! 

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
  <version>5.0.2</version>
</dependency>                                                      
```                         
##### Repository
```xml
<repository>
    <id>maven-central</id>
    <url>http://repo1.maven.org/maven2</url>
</repository>
```        

##### Our other, highlighted [repositories](https://github.com/momomo?tab=repositories)                          

* **[`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Core)** Is essentially what makes the our the core of several of momomo.com's public releases and contains a bunch of Java utility.

* **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** Contains a bunch of `functional interfaces` similar to `Runnable`, `Supplier`, `Function`, `BiFunction`, `Consumer` `...` and so forth all packed in a easily accessed and understood intuitive pattern that are used plenty in our libraries. **`Lambda.V1E`**, **`Lambda.V2E`**, **`Lambda.R1E`**, **`Lambda.R2E`**, ...

* **[`momomo.com.platform.Obj`](https://github.com/momomo/momomo.com.platform.Obj)** Intuitive library that makes it easier for you to return multiple well defined objects  on the fly from any method, any time rather than being limited to the default maximum of one.
 
* **[`momomo.com.platform.Nanotime`](https://github.com/momomo/momomo.com.platform.Nanotime)** Allows for nanosecond time resolution when asking for time from Java Runtime in contrast with **`System.currentTimeMillis()`**.

* **[`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring)** A library to execute database commands in transactions without  having to use annotations based on Spring libraries.

### Background

#### On [Lambda](https://github.com/momomo/momomo.com.platform.Lambda)

What makes this possible or even a reality is the existence of **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** which we've now had in our posession for over 10 years. There is nothing magical about it, yet something similar is lacking in presence in the Java world.    
   
Similar libraries might have recently made the scene but not quite as powerful nor as intuitive, we think. 

We finally released **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** to *actually* be able to release **[`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)** and **[`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring)**. 

**[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** is the most used and important library internally and is the essence and most important factor of what this library does. 

Sure we could have switched our lambdas to **`Supplier`**, **`Consumer`** and so forth, *whatever those names mean*, but to destroy our code just to make our code 100% Java organic?   Not a great idea. 

It is just a bunch of interface classes after all contained in one file, and *nothing expensive* for you to add on.

#### On Spring

Most of the industry today relies on the use of Spring **`@Transactional(propagation = Propagation.REQUIRES_NEW)`**, **`@Transactional(propagation = Propagation.REQUIRES)`** and **`@Transactional(propagation = Propagation.SUPPORTS)`**.  

We did too and frequently run into several very common issues. 
 
To get the *transactional features* using *Spring annotations* the code needs to be:
   
  1. Placed into a method of its own. So code has to be extracted and pollute the outer class scope.   
  Further, the method will have to declare a bunch of parametnanoers for you to pass whatever you needed in the previous scope.   
   Parameter and method pollution. Not great.  
  
  2. The method has to be **`public`** and so can not be **`protected`** or **`private`** when that might be the appropiate choice.   
   A **`protected`** and **`private`** method tells your class, subclasses something and outer world something else. They are important as communicators.  
   
  3. The method **has to be invoked** using Springs injected beans, so a class or 'service' can not even invoke its own method directly.  
   This is risky since someone might do the wrong thing, invoke the method, expect a certain behaviour and not get it.  
   
  4. You need a spring managed bean or use them. You need to inject it whereever you need to get transactional. Spring will hijack and show you stacktraces that are a frequent time waster to parse. Nothing is clear about them. Your exception stacktraces look like a labyrinth and you spend time finding the tiny bug you have which is the most costly part of using Spring hands down since it hijacks your entire Java platform.

Now this to us, introduces a bunch of issues as code is difficult enough to organize **well** using the standard restrictions the `Java` language has alone and is now affecting the way you can code further 
by forcing code that lives perfectly well within a method to be extracted and then somehow find itself to a Spring bean rather than stay where it is most relevant, where it was, *just because you needed some code run in a transaction*.
The extraction can not be **`private`** but also has to be made **`public`**, and not only that, they can not be invoked directly unless invoked from an injected **`proxying`** bean, so forget `static`, and forget creating the instances yourself, separating logic into several classes.  

Repeat this enough times, and your code starts to look like *Chernobyl* all because you need to code the **`Spring`** way. **`Spring`** allows you to *only use a subset* of Java to work well, *rather than add* to Java. It is therefore by definition, *a lesser*, and more limited version of Java. Start using Spring and all your code now has to use Spring to invoke anything. 

But Spring is not all that bad, there is plenty of material on how to setup things using Spring, which makes it easy to setup most of the time but it is not the dream of any hardcore Java developer. You give up a lot of your Java powers to be on that ecosystem and once you start injecting or annotating, they got you hooked and they work hard to lock you into their eco system. So does Hibernate / RedHat / JBoss. They will eventually all sell you support.
     
Spring is a bit kinder in their code though where Hibernate by default has **`private`** access on almost all of their code. That makes it hard to extend and/ord modify behaiviour, so Spring is not all that bad, but once you start injecting, good luck breaking loose! It took us years to do so and there is *no good reason* to be injecting stuff in the first place and the amount of restrictions that this entails is far too many to accept. 

Our code, including the building of the **`SessionFactory`** and/or **`EntityManager`** as well as being able to run code in **transactions** can all be run from a **`static void main`**.
 
Can your Spring code do that? **`static void main`** only and run? **1 sec** launch? A `static void main` can startup in less than a `half a second` within your editor, while the injected stuff Spring pulls requires a lot of ***orchestration***, an initiator, scanning and what not before allowing you do anything.   

#### On Hibernate
Now, using Hibernate as is, is better according to us. However, not much flavour gets added. Spring actually adds a some neccessary functionality, and their better transaction / session manager.   
Hibernates **`transaction manager`** / **`sessionfactory`** is not great. Their **`thread`** implementation severely lacking. But we fixed its shortcomings.  
    
But most people do not even know how to setup Hibernate without Spring and without **`XML`**. It is not an easy topic to find info about actually. But we did the implementation in our **[`$SessionConfig.java`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfig.java)**. It is not all that easy as you can see, nor is it easy to google anything on Hibernate and not get a Spring answer today. 
 
> Remember the Javascript answers on **`StackOverflow.com`** not long ago? They used to be all **`jQuery`** related.   
 
Creating transactions using Hibernate is possible but requires ***programmatic insight*** and is prone to repetition and redundancy which leads to more potential errors and mistakes. 

Futher, we repeat the `Hibernate` option **`thread`** or actually **`org.hibernate.context.internal.ThreadLocalSessionContext`** implementation is very simplictic in nature and severely limited in terms of capacity and we have provided our own tweaked implementations, with **long and suggestive** names:  
   * **[`$SessionConfigContextListRecommended`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextListRecommended.java)** Prevents wrapping of **`openSession()`** calls as well as tracks them from **[`momomo.com.db.$SessionFactory`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/sessionFactory/$SessionFactory.java)** by adding the **`new session`** on top of a stack. When the **`new session`** completes, we remove it and the one previously on top becomes the one to be used for **`currentSession()`** / **`requireSession()`** calls.
   
   * **[`$SessionConfigContextSingleCrazyInsane`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextSingleCrazyInsane.java)** Prevents wrapping of **`openSession()`** calls as well as tracks them from **[`momomo.com.db.$SessionFactory`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/sessionFactory/$SessionFactory.java)** by terminating / rolling back any previously attached `session` and eating away any exception possibly thrown as a result, logging it only instead of throwing the exception. 
   
   * **[`$SessionConfigContextSingleCrazySane`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextSingleCrazySane.java)** Prevents wrapping of **`openSession()`** calls as well as tracks them from **[`momomo.com.db.$SessionFactory`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/sessionFactory/$SessionFactory.java)** by terminating / rolling back any previously attached `session` by rolling it back yet throwing any exception that might arise rather than eating it.  
              
   * **[`$SessionConfigContextSingleCrazyLaxed`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextSingleCrazyLaxed.java)** Prevents wrapping of **`openSession()`** calls as well as tracks them from **[`momomo.com.db.$SessionFactory`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/sessionFactory/$SessionFactory.java)** by detaching previously tracked **`session`** silently, not terminating them but allowing them to continue to live, in hopes that the developer takes responsibility of manually terminating them instead.
      
   * **[`$SessionConfigContextUntrackedLeastRecommended`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigContextUntrackedLeastRecommended.java)** Prevents wrapping of **`openSession()`** calls but does not track **`openSession()`** calls like the other ones do because it uses Hibernates own **`ThreadLocalSessionContext`** where we basicaly just override **`ThreadLocalSessionContext`** to prevent the wrapping of the **`Session`** upon a call to **`currentSesssion()`** which for some reason by default returns a proxied, wrapped and limited **dumb proof** **`Session`** that's lacking on so many levels.  
   &nbsp;  
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

## Getting started

The entire thing is really much much simpler to understand and navigate if you just **check out the entire repository**, **navigate and/or run** the examples. But we will try to guide you here as well.

We've decided to develop a **Crypto** related application!

Start by looking at

* **[`Crypto.java`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/Crypto.java)** Contains code for setting up the postgresql `database` and the Hibernate **`SessionFactory`** as well as provides a **`CryptoTransactional`** and **`CryptoRepository`** which will be used in our examples later and is the implementation used in our running examples.    
  
  Objects are created and separated intentionally to show you the different areas of responsibility.     

* **[`CryptoMinimal.java`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/extra/CryptoMinimal.java)** is very similar to **[`Crypto`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/Crypto.java)** but is densed downed to show you what the minimal working configuration would actually look like.

* **[`CryptoLargest.java`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/extra/CryptoLargest.java)** is very similar to **[`Crypto`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/Crypto.java)** but contains more **examples and comments** on some things we can modify when setting up our `database` and the **`SessionFactory`**.  
  
```java                                               
// This is all code required to get started!

public class CryptoMinimal {
    
    /////////////////////////////////////////////////////////////////////
    
    private static final SessionFactory                SESSION_FACTORY = new CryptoSessionConfig().create();
    public  static final CryptoTransactionalRepository REPOSITORY      = new CryptoTransactionalRepository();
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class CryptoDatabase implements $DatabasePostgres {
        @Override public String name() {
            return "crypto_database_name_based_on_hibernate_libraries";
        }
        
        @Override public String password() {
            return "postgres";
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class CryptoSessionConfig extends $SessionConfig<CryptoDatabase> {
        private CryptoSessionConfig() {
            super(new CryptoDatabase());
        }
        
        @Override protected String[] packages() {
            return new String[]{ "momomo/com/example/app/entities" }; // The package to scan for entities 
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Note, both a repository and a transactional instance class in one! 
     */
    public static final class CryptoTransactionalRepository implements $SessionManagerRepository, $TransactionalHibernate {
        @Override public SessionFactory sessionFactory() {
            return SESSION_FACTORY;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
}
```  
   
Now that you've seen it, glanced it, consumed it, as well as having glanced **[`Crypto`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/Crypto.java)** which is the one we actually use, you may ***proceed***.

---

### Demonstration of the `Transactional` API 

Link to **[`$TransactionalHibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate/tree/master/src/momomo/com/db/$TransactionalHibernate.java)** and for the lazy, here are the method signatures in **[`$Transactional`](https://github.com/momomo/momomo.com.platform.db.transactional/tree/master/src/momomo/com/db/$Transactional.java)**:

![Transactional API signatures](https://github.com/momomo/momomo.com.yz.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/signatures.transactional.2021.04.07.V1.jpg?raw=true)

### `Part 1` - **[`Bitcoin.java`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Bitcoin.java)** 

We start by looking at our **first entity** **[`Bitcoin`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Bitcoin.java)**

```java                                                       
@Entity ... public ... final class Bitcoin implements $Entity {
    @Id
    private UUID      id;
    private Timestamp time;
    private double    usd; // Represents the price in usd                  

```                                           

``` ... ```

```java
public static final Service S = new Service(); public static final class Service {    
   /** 
    * Insert method that creates a bitcoin, then requires a transaction, and then saves it within the transaction, which then commits if it was the one to start it.  
    */                  
    public Bitcoin insert(Timestamp time, double usd) {
        // This "very very expensive" creation need not to run inside the transaction 
        Bitcoin entity = new Bitcoin().setId(UUID.randomUUID()).setTime(time).setUsd(usd);

        // Now, require the transaction and execute save within, also return the saved entity. 
        return Crypto.repository.requireTransaction(() -> {
            return Crypto.repository.save(entity);
        });
    }
}
```

In method `insert()` we have 

```java
// We create the entity

Bitcoin entity = new Bitcoin()
    .setId(UUID.randomUUID())
    .setTime(time)
    .setUsd(usd)
;
```                          

```java
// Then we require a write capable 'transaction' which means create a 'new transaction' 
// if there is not already an ongoing one for this thread 

return Crypto.repository.requireTransaction((tx) -> {
    return Crypto.repository.save(entity);
});
```                                                                                                                            

The **`save(entity)`** call will execute within a **transaction** and when it terminates it will **commit** transaction if it **was the one who started it**.

The **`save()`** call could really be your own normal logic. If you use `Spring` you would use whatever you where you using before, likely using an **`EntityManager`** and calling **`em.save(entity)`**, and if **`Hibernate`** likey **`session.saveOrUpdate(entity)`**. 

Here we used our already created and **capable** repository which will eventually call `session.saveOrUpdate(entity)` and to ensure that it was saved properly, it checks it was assigned an `id` as it should which is not always the case. 

When using our `save()` we will also check to see if the entity has implemented **[`$Entity.Events`](https://github.com/momomo/momomo.com.platform.db.base.jpa/tree/master/src/momomo/com/db/entities/$Entity.java)** where your entity may implement `beforeSave()` and/or `afterSave()` logic which will be triggered before and/or after a `save()`. We use this sometimes to *set default values** on some fields or generate values based on other fields *to set a third*. 

> Note!
> 
> To be able to use our repository we currently require that your `entities` implement our empty **[`$Entity`](https://github.com/momomo/momomo.com.platform.db.base.jpa/tree/master/src/momomo/com/db/entities/$Entity.java)** interface.
>   
> We can see that this is no longer really required as the interface is indeed empty but this was a safety mechanism for our internal code once to ensure control as means of declaration but we will eventually remove this requirement from our `Repository` implementation to support any entity. 
>
> The Transactional API does not have that requirement, but the **`repository.save(..)`**, **`repository.find(...)`** currently do.    

We can now invoke 
 
```java
Bitcoin.S.insert(Time.stamp(), 10000.1)
```

from anyplace, even from a plain **`static void main`**.  

So given the following in **[`Bitcoin.Service`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Bitcoin.java)**:

```java
public Bitcoin insert(Timestamp time, double usd) {
    Bitcoin entity = new Bitcoin().setId(Randoms.UUID()).setTime(time).setUsd(usd);

    return Crypto.repository.requireTransaction((tx)-> {
        return save(entity);
    });
}                                                  

public void populate(int multiply) {
    // Multiple transactions each started inside the insert method
    insert(Time.stamp(), multiply * 1);
    insert(Time.stamp(), multiply * 2);
    
    // Two at once, insert call will just continue using this created transaction
    Crypto.repository.requireTransaction(() -> {
        insert(Time.stamp(), multiply * 3);
        insert(Time.stamp(), multiply * 4);
    });
}              
```

we can execute 

```java
public static void main(String[] args) {
    Bitcoin.S.populate();
}
```         

which will **trigger** the database generation, **scan** for entity classes in the *configured packages*, setup the **`SessionFactory`** and get you a transaction to eventually create and **save** entities to the database.

### `Part 2` - **[`Polkadot.java`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Polkadot.java)** 

You saw **`requireTransaction(()->{ ... })`** in **[`Bitcoin`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Bitcoin.java)** but let us see *what else* we can do. 
 
 First however, we rewrite **[`Bitcoin`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Bitcoin.java)** class by making it **prettier** by utilitizing the already created inner class **[`Crypto.CryptoService`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/Crypto.java#L129)** at the **bottom** of **[`Crypto`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/Crypto.java)**. 
  
  The implementation is **minimal**, **simple** and **straightforward** to declare and use. 

```java
public static abstract class CryptoService<T extends $EntityId> extends $Service<T> implements $TransactionalHibernate {
    @Override public CryptoRepository repository() { return CRYPTO.REPOSITORY; }
}
```

We make use of this class in a another class **[`Polkadot`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Polkadot.java)** that now contains a **minimal** version of the logic found in **[`Bitcoin`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Bitcoin.java)** with the following changes listed:
1. The entity class now extends **`$EntityIdUUID`** which will generate an UUID identifier for us, so need to manually set that part.  
2. **[`Polkadot.Service`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Polkadot.java)** now **`extends`** **[`Crypto.CryptoService<Polkadot>`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/Crypto.java#L129)** which will gives access to a bunch of methods, such as **`save(..)`**, **`list()`**, **`validate()`**, **`findByField(...)`**, **`findByEntity(...)`**, **`reqireTransaction(...)`**, **`newTransaction(...)`**, **`supportTransaction(...)`** and many more without the need for external reference for access like in **[`Bitcoin`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Bitcoin.java)** where we did **`Crypto.repository.requireTransaction(...)`**, now we can simply do **`requireTransaction(...)`**.   

```java                                      
// This you saw in Bitcoin.java

Crypto.repository.requireTransaction(() -> {
    return Crypto.repository.save(entity);
});                            
```

```java
// This is what we now do through inheritance now
 
requireTransaction(() -> {
    return save(entity);
});
```                          

### `Part 3` - **[`Etherum.java`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Etherum.java)**                                 

Let us now take a look at sample code found in our **dummy class** **[`Etherum.Service`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Etherum.java)** which **`extends`** **[`Crypto.CryptoService<Etherum>`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/Crypto.java#L129)** containing just example code that is really never invoked. 

```java
// Given 

Etherum entity = new Etherum()
    .setId(UUID.randomUUID())
    .setTime(time)
    .setUsd(usd)
;
```

```java
// We can demand a new transaction regardless of an existing one using        

requireTransaction(() -> {
    return save(entity);
});
```    

```java
// A read only transaction

List<Polkadot> = supportTransaction(() -> {
    return list(); // super.list() available from the super class $Service 
});   
```

```java
// We can disable auto commit

requireTransaction(($TransactionHibernate transaction) -> {
    // Disable autocommit, so we commit when we want or not at all
    transaction.autocommit(false);

    save(entity);

    transaction.commit();
});
```                                               

```java
// Another way to disable automatic commit
                                                              
requireTransaction((tx) -> {
    save(entity);     
    
   tx.commit();
}, false /** commit false**/ );
```

```java
// We can hook in to do something once the commit is succesful

requireTransaction((tx) -> {
    tx.afterCommit(() -> {
        // Send email perhaps when we exit the transaction after succesfully committing!
 
        // We have now inserted the value in our database successfully!

        // We do not want to send the email unless we actually have 100% inserted the stuff so this is a handy method to use for that 
    });

    save(entity);
});
```            

```java
// Rolling back inside a lambda possible             

newTransaction((tx) -> {
    save(entity);

    tx.rollback();

    // note, now, the autocommit won't occur, as we have rolled back and basically discarded the entire transaction.  
});
```

```java
// We can return something from inside the transaction, like an entity

Etherum e = requireTransaction(() -> {
    return save(entity); // We repeat the return demo but by returning an entity
});
```

```java
// Return again, showcasing that you can return anything really
 
String returns = requireTransaction(() -> {
    save(entity);

    return "we can return anything from the lambda";
});
```

```java
// A lambda less example, if we do not want to execute things inside a lambda but desire more freedom?
                                           
$TransactionHibernate tx = requireTransaction();
save(entity);
tx.autocommit(false);
tx.afterCommit  (()-> {});
tx.afterRollback(()-> { /* A crime has been committed! Report error to the FBI! */ });
tx.rollback();
tx.commit();
...
```          

```java
// We show that exceptions will bubble up to the caller
                                 
try {
    requireTransaction(() -> { throw new IOException(); });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E) after rolling back. 
    // If there is a rollback exception, a $DatabaseRollbackException will be thrown instead. 
    // Will not commit.  
}
```

```java
// We can return and throw exceptions
                       
try {
    File file = requireTransaction(() -> {
        if ( true ) { return new File(""); }   

        throw new IOException();                       
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E) after rolling back. 
    // If there is a rollback exception, a $DatabaseRollbackException will be thrown instead.
    // Will not commit.
}
```                                 

```java
// We can get access to the actual `session` and retain 100% control
                   
Session s1 = requireSession();
Session s2 = newSession();
```                                            

```java
// We can build the transaction properties and set various things ourselves

requireOptions()
    .propagation(Propagation.NEW)
    .isolation(Isolation.REPEATABLE_READ)
    .timeout(1000)
    .create()
    .execute((tx)-> {
        tx.autocommit(false);
        tx.afterCommit(()-> {});
        tx.afterRollback(()-> {});

        // ... 
    });
```

```java
// Similar to previous example but without chaining  
$TransactionOptionsHibernate options = requireOptions();
// ... options.propagation(...)
// ... options.create()
```

```java
// Getting a transaction that we can execute

$TransactionHibernate tx = requireOptions()
    .propagation(Propagation.NEW)
    .isolation(Isolation.REPEATABLE_READ)
    .timeout(1000)
    .create()
;
tx.execute(()-> {
    save(entity);
});

tx.execute(()-> {
    save(entity);

    tx1.commit();
}, false /** don't commit **/ );
```                                      

```java
// More options made visible
                                      
requireOptions()
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

### `Part 4` - **[`Stellar.java`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Stellar.java)**  

While in **[`Bitcoin`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Bitcoin.java)**, **[`Etherum`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Etherun.java)**, **[`Polkadot`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Polkadot.java)** services, we required the transaction inside the insert method, in **[`Stellar`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Stellar.java)**, we no longer make use of `requireTransaction()` there because we figured it was better design to *place that burden* on the caller to know the call needs a transaction to reduce boiler plate further.

Using `Spring`, the caller would have had to extract those parts to fit neatly into a method while the caller for us would simply wrap them inside a lambda.  

So rather than doing: 

```java
public Stellar insert(Timestamp time, double usd) {
    return requireTrasaction( () -> save( create().setTime(time).setUsd(usd) ) ); 
}
```

in **[`Stellar.Service`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Stellar.java)** we simply do:    

```java
public Stellar insert(Timestamp time, double usd) {
    return save( create().setTime(time).setUsd(usd) ); 
}
```

Doing this we expect the caller to do the **`requireTransaction()`** call for us.  

In **[`Stellar.Service`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/app/entities/Stellar.java)** we can also find more complex working example:  

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
We also added a couple more methods, just as examples on what else we can do, although never invoked:  

```java
// Return all the historic data within polkadot table

public List<Stellar> historic() {
    return supportTransaction(()-> {
        return super.list();
    });
}            
```

```java
// Return the data within time range

public List<Stellar> range(Timestamp from, Timestamp to) {
    return supportTransaction(()-> {
        return list(criteria()
            .add( Restrictions.ge(Cons.time, from) )
            .add( Restrictions.le(Cons.time, to  ) )
        );
    });
}
```

### `Part 5` - **`public static void main`**  

If we now look at **[`PUBLIC_STATIC_VOID_MAIN`](https://github.com/momomo/momomo.com.example.app.Crypto.based.on.hibernate.libraries/tree/master/src/momomo/com/example/extra/PUBLIC_STATIC_VOID_MAIN.java)** we can find a `static void main` and some code ready to run the entire thing.

```java
public static void main(String[] args) {
    Bitcoin.S.populate(1);
    Polkadot.S.populate(1);
    Stellar.S.populate(1);

    // We disable autocommit using false, and commit manually 
    {
        Crypto.repository.requireTransaction(tx-> {
            Bitcoin.S.populate(1000);
            
            tx.commit();
            
        }, false /** disable autocommit **/);
    }

    // We rollback from inside the lambda
    {
        Crypto.repository.requireTransaction(tx -> {
            Bitcoin.S.populate(-10000);
        
            tx.rollback();
        });
    }

    // We rollback from 'free' mode
    {
        $Transaction tx = Crypto.repository.requireTransaction();
        Bitcoin.S.populate(-100000);
        
        tx.rollback();
    }
}
```

When we run this static void main we will eventually find the **following in our database**:  

![Generated tables](https://github.com/momomo/momomo.com.yz.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.tables.2021.04.09.V2.hibernate.jpg?raw=true)                

   * ***bitcoin table***  
   ![Bitcoin table](https://github.com/momomo/momomo.com.yz.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.bitcoin.table.2021.04.05.V1.jpg?raw=true)        
   
   * ***polkadot table***  
   ![Polkadot table](https://github.com/momomo/momomo.com.yz.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.polkadot.table.2021.04.03.V2.jpg?raw=true)        
   
   * ***stellar table***  
   ![Stellar table](https://github.com/momomo/momomo.com.yz.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.stellar.table.2021.04.03.V2.jpg?raw=true)        

### License
The full license can be found here [`MoL9`](https://raw.githubusercontent.com/momomo/momomo.com.yz.licenses/master/MoL9?raw=true?raw=true)

### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.
