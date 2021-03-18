<!---
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

* [`momomo.com.platform.Nanotime`](https://github.com/momomo/momomo.com.platform.Nanotime)  
Allows for nanosecond time resolution when asking for time from Java Runtime in contrast with `System.currentTimeMillis()`.

### Background

First understand that what makes this possible or even a reality is the existence of [`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda) which we've now had in our posession for over 10 years.
There is nothing magical about it, yet something similar is lacking in presence in the Java world. Similar libraries might have recently made the scene but not quite as powerful nor as intuitive, we think. 

We finally released [`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda) to actually be able to release [`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate) and [`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring). 

[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda) is the most used and important library we use and is the essence and most important factor of what this library does. 

Sure we could have switched our lambdas to `Supplier`, `Consumer` and so forth, whatever those names mean, but to destroy our code just to make our code 100% Java organic? We think not. It is just a bunch of interface classes after all, nothing expensive for you to add on.         

#### Spring firstly

Most of the industry today relies on the use of Spring `@Transactional(propagation = Propagation.REQUIRES_NEW)` and frequently run into several issues.  
 
To get the transactional behaviour using Spring the code needs to be:
   
   &nbsp; a. Placed into a method of its own.  
   &nbsp; b. The method has to be `public` so can not be `protected` or `private` when that might be more appropiate.   
   &nbsp; c. The method has to be invoked using Springs injected beans, so a class or '`service`' can not even invoke its own method directly.

Now this to us, introduces a bunch of issues as code is difficult enough to organize well using the restrictions `Java` has alone and is now affecting the way you can code further 
by forcing code that lives perfectly well within a method to be extracted and then somehow find itself to a Spring bean rather than stay where it is most relevant, where it was just because you needed some code run in a transaction.  
The extraction can not be `private` but now has to be made `public` and not only that, they can not be invoked directly unless invoked from the injected `proxying` bean.

Repeat this enough times, and your code starts to look like chernobyl, all because you need to code the Spring way.   

All your code now has to use Spring to invoke anything. Your exception stacktraces look like a mess, you spend time finding the bug which is the most costly part of Spring, since it hijacked your entire Java platform.    

Sure, there is plenty of material on how to setup things using Spring, which makes it easy to setup most of the time, but it is not the dream of any hardcore Java developer. 
You give up a lot of your Java powers to be on that ecosystem and they can sell you support.     

You should know by now that Spring works hard to lock you into their eco system. One you start injecting, good luck breaking loose. It took us years. There is no reason to be injecting stuff in the first place, and the amount of restrictions that this entails is far too many to be sacrificing. 

Our code, including the building of the `SessionFactory` and/or `EntityManager`, as well as being able to run code in **transactions** can all be run from a `static void main`. Can your Spring code do that?   
A `static void main` can startup in less than a second, while the injected stuff Spring pulls, requires a lot of orchestration, an initiator, scanning and what not before allowing you do anything. 

Sure, you use `Spring Boot`, it runs in a `static void main` you say! Yes, but it starts a `server` to give you access and everything has to run through that.

It took us years to break loose and we have come a long way since. 

#### Hibernate secondly
Now, using Hibernate as is, is great, however, not much flavour gets added. Most people do not even know how to setup Hibernate without Spring. 
We have code for that as well. Creating transactions using Hibernate is possible but requires programmatic insight, and is prone to repetition. These repetions leads to redundancy, mistakes and potential errors.  

#### Momomo lastly
What we've done is written a base [`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional) which is then currently implemented with two flavors:  
   1. [`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring)  
   For those currently stuck or happy with Spring. Most people do not have our complaints.    
   &nbsp;      
   It uses Springs `PlatformTransactionManager` as the tool to perform your transactions using **our API** which basically setups the 
   transaction and then delegates it to Spring to make the transaction work as you intended. 
   &nbsp;  
   This works great and have been tested extensively as this library came before the **Hibernate only** library.   
   2. [`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)  
      For those that do not want to use Spring for anything. This is our camp.  
      
      We broke loose from Spring setting up our `EntityManagerFactory` which in reality just wraps a Hibernate `SessionFactory` as well 
      as broke loose from relying on `SpringPlatformTransactionManager` and rewrote the required parts using `Hibernate` transaction `API`. 

What we get is a unified `Transactional` **API** that can be setup and invoked from anyplace, and you could one day even switch from Spring and retain your functionality.   
      
### Getting started






### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.
