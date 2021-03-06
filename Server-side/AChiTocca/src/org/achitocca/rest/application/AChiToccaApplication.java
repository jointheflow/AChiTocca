package org.achitocca.rest.application;



import java.util.Arrays;
import java.util.HashSet;


import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.engine.application.CorsFilter;
import org.restlet.routing.Router;

public class AChiToccaApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
      
    	// Create a router Restlet that routes each call to a
        // new instance resources.
        Router router = new Router(getContext());
        
        // Defines a route for the URLs requests  
         
        router.attach("/achitocca/group/getgroups", org.achitocca.rest.resource.getGroupsOfUserResource.class);
        router.attach("/achitocca/group/create", org.achitocca.rest.resource.doCreateGroupResource.class);
        router.attach("/achitocca/group/addusers", org.achitocca.rest.resource.doAddUsersToGroupResource.class);
        router.attach("/achitocca/group/achitocca", org.achitocca.rest.resource.aChiToccaResource.class);
        router.attach("/achitocca/group/nextuser", org.achitocca.rest.resource.doNextUserResource.class);
        /*
        router.attach("/freediver/get", org.gianluca.logbook.rest.resource.FreediverGetResource.class);
        router.attach("/freediver/divesession/add", org.gianluca.logbook.rest.resource.DiveSessionAddResource.class);
        router.attach("/freediver/divesession/update", org.gianluca.logbook.rest.resource.DiveSessionUpdateResource.class);
        router.attach("/freediver/divesession/remove", org.gianluca.logbook.rest.resource.DiveSessionRemoveResource.class);
        router.attach("/freediver/divesession/get", org.gianluca.logbook.rest.resource.DiveSessionGetResource.class);
        router.attach("/freediver/divesession/dive/add", org.gianluca.logbook.rest.resource.DiveAddResource.class);
        router.attach("/freediver/divesession/dive/update", org.gianluca.logbook.rest.resource.DiveUpdateResource.class);
        router.attach("/freediver/divesession/dive/remove", org.gianluca.logbook.rest.resource.DiveRemoveResource.class);
       */
        
     // Add a CORS filter to allow cross-domain requests
        CorsFilter corsFilter = new CorsFilter(getContext(), router);
        corsFilter.setAllowedOrigins(new HashSet<String>(Arrays.asList("*")));
        corsFilter.setAllowedCredentials(true);
        
        return router;

       
    }
}
