=================
Events Management
=================

## Install and run

Clone the url 
```
git clone git clone https://github.com/victorbucutea/events
```

And build the project with Maven
```
mvn clean install
```

The unit tests will take ~20 min. This is due to the starting and stopping of the embedded glassfish server. In order to save time, you can just skip unit tests
```
mvn clean install -DskipTests=true
```

## Arhitectural Decisions
Glassfish application server because:
* It comes with an embeddable EJB container, so unit&integration testing is much easier
* The current gameduell infrastructure is largely based on glassfish, so it is a natural choice for this exercise.


Stateful paginator bean because: 

* It keeps the current page number in its own state, so the caller can just iterate through the result pages using the iterator pattern.
* If this were a stateless bean, the caller will need to keep track of the current page and the page size.
* Stateful beans are prone to increase memory footprint, but they are safe to use with any client. Because we do not know what type of client we will have
  and we cannot rely on it to keep track of the page (e.g a UI client can only hold the current page for as long as the http session is active) a stateful
  bean will have to do the job.
* Can be easily configured to provide functionality like 'Number of results', 'Current Page', going back to previous page ,etc
* Keeping track of paging in the client creates boilerplate code.


Singleton registered JMX bean because:

 * Custom MBeans have been removed in Glassfish 3.1, so the can only be declared programatically ( https://www.java.net/forum/topic/glassfish/glassfish/custom-mbeans )
 * Declaring it programatically we have no more appserver-specific configuration files.
 * Its automatic (@Startup) registration and unregistration at the MBeanServer. This solves the redeployment issues like multiple registrations of the same bean 
 * Singleton is able to cache state and expose it on demand 
 * Singleton can be injected into other components like EJBs, Interceptors, CDI 
 * Easier for the JMX MBean to poll for the current filter
 
 
Event Service will poll JMX bean for the current filters:

* We need to configure all instances of the EventService EJB to use a new query. We can do that by:
   * Having a value stored in JNDI which the EventService EJB can interogate ( reliable, but bad practice )
   * Have the EventService EJB poll the JMX bean (which is also a Singleton) for the filters ( this is a scalable, maintainable and safe approach )
* Using a notify mechanism for the JMX bean to notify the EventService EJB, would not be safe. Without a bit of hacking, we can not control all instances of the EJB present in memory