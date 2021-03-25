<!----
-->

## momomo.com.platform.db.transactional.Hibernate

###### A library to execute database command in transactions without having to use annotations based on Hibernate libraries. No Spring! 

#### Dependencies 
* [`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Core) 
* [`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)
* [`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional)

##### Maven dependencies available on maven central [search.maven.org](https://search.maven.org/search?q=com.momomo)
##### Dependency   
```xml
<dependency>
  <groupId>com.momomo</groupId>
  <artifactId>momomo.com.platform.db.base.transactional.Hibernate</artifactId>
  <version>2.1.7</version>
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

* [`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Lambda)  
Is essentially what makes the our the core of several of momomo.com's public releases and contains a bunch of `Java` utility.

* [`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)  
Contains a bunch of `functional interfaces` similar to `Runnable`, `Supplier`, `Function`, `BiFunction`, `Consumer` `...` and so forth all packed in a easily accessed 
and understood intuitive pattern.  
`Lambda.V1E`, `Lambda.V2E`, `Lambda.R1E`, `Lambda.R2E` are used plenty in examples below.

* [`momomo.com.platform.Return`](https://github.com/momomo/momomo.com.platform.Return)  
An intuitive library that allows you to return multiple return values with defined types on the fly from any method rather than being limited to the default maximum of one.

* [`momomo.com.platform.Nanotime`](https://github.com/momomo/momomo.com.platform.Nanotime)  
Allows for nanosecond time resolution when asking for time from Java Runtime in contrast with `System.currentTimeMillis()`.

* [`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)  
A library to execute database command in transactions without having to use annotations based on Hibernate libraries. No Spring!

### Background

#### On [Lambda](https://github.com/momomo/momomo.com.platform.Lambda)

What makes this possible or even a reality is the existence of [`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda) which we've now had in our posession for over 10 years.  
There is nothing magical about it, yet something similar is lacking in presence in the Java world.    
   
Similar libraries might have recently made the scene but not quite as powerful nor as intuitive, we think. 

We finally released [`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda) to *actually* be able to release [`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate) and [`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring). 

[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda) is the most used and important library internally and is the essence and most important factor of what this library does. 

Sure we could have switched our lambdas to `Supplier`, `Consumer` and so forth, *whatever those names mean*, but to destroy our code just to make our code 100% Java organic?   Not a great idea. 

It is just a bunch of interface classes after all contained in one **file**, *nothing expensive* for you to add on.

----         

#### On Spring

Most of the industry today relies on the use of Spring `@Transactional(propagation = Propagation.REQUIRES_NEW)` and `@Transactional(propagation = Propagation.REQUIRES)` `@Transactional(propagation = Propagation.SUPPORTS)`.  

We did too, and frequently run into several very common issues. 
 
To get the *transactional features* using *Spring annotations* the code needs to be:
   
   &nbsp; a. Placed into a method of its own. So code has to be extracted and pollute the outer class scope.   
   &nbsp; &nbsp; &nbsp; Further, the method will have to declare a bunch of parametnanoers for you to pass whatever you needed in the previous scope.   
   &nbsp; &nbsp; &nbsp; Parameter and method pollution. Not great.  
   &nbsp;        
   &nbsp; b. The method has to be `public` and so can not be `protected` or `private` when that might be the appropiate choice.   
   &nbsp; &nbsp; &nbsp; A `protected` and `private` method tells your class, subclasses something and outer world something else. They are important as communicators.  
   &nbsp;    
   &nbsp; c. The method **has to be invoked** using Springs injected beans, so a class or '`service`' can not even invoke its own method directly.  
   &nbsp; &nbsp; &nbsp; This is risky since someone might do the wrong thing, invoke the method, expect a certain behaviour and not get it.  
   &nbsp; &nbsp;   
   &nbsp; d. You need a spring managed bean or use them. You need to inject it whereever you need to get transactional.     
   &nbsp; &nbsp; &nbsp; Spring will hijack and show you stacktraces that are a frequent time waster to parse. Nothing is clear about them.   
   &nbsp; &nbsp; &nbsp; Your exception stacktraces look like a labyrinth and you spend time finding the tiny bug you have which is the most costly part of using Spring hands down since it hijacks your entire Java platform.        

Now this to us, introduces a bunch of issues as code is difficult enough to organize **well** using the standard restrictions the `Java` language has alone and is now affecting the way you can code further 
by forcing code that lives perfectly well within a method to be extracted and then somehow find itself to a Spring bean rather than stay where it is most relevant, where it was, just because you needed some code run in a transaction.
The extraction can not be `private` but now has to be made `public` and not only that, they can not be invoked directly unless invoked from the injected `proxying` bean.

Repeat this enough times, and your code starts to look like Chernobyl, all because you need to code the Spring way. Spring enforces a subset of Java to work well, rather than add to Java. It is therefore a lesser, limited version of Java. 
Start using Spring and all your code now has to use Spring to invoke anything.

Well, Spring is not all that bad, there is plenty of material on how to setup things using Spring, which makes it easy to setup most of the time but it is not the dream of any hardcore Java developer.   
You give up a lot of your Java powers to be on that ecosystem and once you start injecting or annotating, they got you hooked.   
Spring works hard to lock you into their eco system. So does Hibernate / RedHat / JBoss. They will all eventually sell you support and you will need it.     
Spring is a bit kinder in their code though where Hibernate by default has private access on almost all of their code. That makes it hard to extend and/ord modify behaiviour, so Spring is not all that bad. 
But once you start injecting, good luck breaking loose. It took us years to do so. There is no reason to be injecting stuff in the first place, and the amount of restrictions that this entails is far too many. 

Our code, including the building of the `SessionFactory` and/or `EntityManager`, as well as being able to run code in **transactions** can all be run from a `static void main`. 
Can your Spring code do that? `static void main` only and run?   
A `static void main` can startup in less than a `half a second` within your editor, while the injected stuff Spring pulls, requires a lot of `orchestration`, an initiator, scanning and what not before allowing you do anything. 

Sure, you use `Spring Boot`, it runs in a `static void main` you say! Yes, but it starts a `server` to give you access and everything has to run through that. Not easy.  

----

#### On Hibernate
Now, using Hibernate as is, is good, however, not much flavour gets added. Spring actually adds a some neccessary functionality, and their better transaction / session manager.   
Hibernates `transaction manager` / `sessionfactory` is not great. Their `thread` implementation severely lacking. But we fixed its shortcomings.  
    
But most people do not even know how to setup Hibernate without Spring. Without `XML`. It is not an easy topic to find info about. 
See our [$SessionConfig.java](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfig.java) implementation for that.   
It is not all that easy, nor is it easy to google anything on Hibernate and not get a Spring answer today. 
 
&nbsp; &nbsp; >> Remember the Javascript answers on `StackOverflow.com` not long ago? They used to be all `jQuery` related.   
 
Creating transactions using Hibernate is possible but requires **programmatic insight**, and is prone to repetition. 

These repetions leads to redundancy, which leads potential errors and mistakes. 

Futher, we repeat, `thread` or actually `org.hibernate.context.internal.ThreadLocalSessionContext` implementation is deeply, deeply flawed. 
We have our own tweaked implementations, with long descriptive names  
   * [ThreadLocalSessionContextRecommended](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigThreadLocalSessionContextRecommended.java)         
   * [ThreadLocalSessionContextCrazySane](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigThreadLocalSessionContextUnwrappedTrackedSingleCrazySane.java)         
   * [ThreadLocalSessionContextCrazyLaxed](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigThreadLocalSessionContextUnwrappedTrackedSingleCrazyLaxed.java)         
   * [ThreadLocalSessionContextCrazyInsane](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$SessionConfigThreadLocalSessionContextUnwrappedTrackedSingleCrazyInsane.java)  
   Uses Hibernates own `ThreadLocalSessionContext` which is insane on many levels. Comments are within the class but we baically just override `ThreadLocalSessionContext` to 
   prevent the wrapping of the `Session` upon a call to `currrentSesssion()` which for some reason returns a proxied, wraooebd and limited `dumb proof` `Session` which is seriously lacking on so many levels.

----

#### On *this* library, what does it do?

It is an easy to use, **annotation free**, **transactional API**, that offers a vast amount of methods to **create**, or **reuse** transactions by either delegating to existing implementations in the case of `Spring` or add a bit of additional flavour in the case of `Hibernate` only version to match `Spring`'s transaction capabilities. 

It is a fully customizable, flexible, overridable, optionable and all the other **able** thing the world has to offer. Simply put, it is very capable.    

What we've done is written a base [`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional) which is then *currently* implemented with two flavors
  
   1. [`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring) For those currently stuck or happy with Spring.      
   It makes use of Springs `PlatformTransactionManager` as the tool to perform your transactions using **our API** which basically setups the 
   transaction and then delegates it to Spring to make the transaction work as you intended. 
   &nbsp;  
   This works great and have been **tested extensively** as this library came before we switched to our now **Hibernate only** library to get off the injection, beans and annotation trail.
   
   2. [`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)  
      Our camp and our **recommendation to all new adopters**, and for those that do not want to use `Spring`.    
      
      We broke loose from Spring setting up our `EntityManagerFactory` which in reality just wraps a Hibernate `SessionFactory` as well as broke loose from relying on `SpringPlatformTransactionManager` 
      by basically writing our own transaction manager *(not that difficult)* using `Hibernate` `Session` transaction mechanisms. 
      
  3. ***Coming soon*** and already in a somewhat acceptable condition is the JDBC only transactional API. It already provides similar mechanisms, but is not fully developed as the other two.  
     See [momomo.com.db.$DatabaseTransactional](https://github.com/momomo/momomo.com.platform.db.base/tree/master/src/momomo/com/db/$DatabaseTransactional.java) for the current state of the JDBC version.  
     It is not a tit for tat implementation, but gives us a limited transactional API, that is similar.        

What we get is a unified `Transactional` **API** that can be setup and invoked from anyplace, and you could one day even switch from `Spring` to `Hibernate` and retain your functionality.

----

#### Is that all? 
Not at all. We also provide a bunch of other things, such as the simplicy of setting up your hibernate `SessionFactory`, as well as `Migrations` (think *liquibase*, *flyway*), as well as a bunch of 
session related utility related to entities, persisting, finding, saving, building and more.  

All of this can execute from a **`static void main`**. **Zero** xml. **Zero** complexity. 

----

#### WE ARE STILL WRITING THIS DOCUMENTAION, IT IS NOT COMPLETE! COMING SOON.  

As of now, we recommend you to visit our Crypto example application which details almost all of it. We just have to include it here as well soon.

#### [`CLICK HERE TO VISIT CRYPTO EXAMPLE APP WITH MOST OF THE DOCUMENTAION IN IT`](https://github.com/momomo/momomo.com.example.app.Crypto)

---- 




### Getting started

The best way we can show case things, is simply to show you the code, which is fully functional, and which you can download, navigate and/or run immediately yourself.

The code for the application can be found in this repository, but below we've **included the readme** of that repository.    

### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.
