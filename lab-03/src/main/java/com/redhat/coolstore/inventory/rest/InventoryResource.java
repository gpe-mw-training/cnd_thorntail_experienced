package com.redhat.coolstore.inventory.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.coolstore.inventory.model.Inventory;
import com.redhat.coolstore.inventory.service.InventoryService;

@Path("/inventory")
@ApplicationScoped
public class InventoryResource {

	  @Inject
	  private InventoryService inventoryService;

	  
	    @GET
	    @Path("/{itemId}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Inventory getInventory(@PathParam("itemId") String itemId) {

	        //
	        // TODO
	        //

	        // get the inventory object from the inventoryService. Use the given itemId.
	    		Inventory inventory = inventoryService.getInventory(itemId);

	        // if the inventory object that you retrieved is null then
	        // throw a NotFoundException
	        // else return the inventory object
	    		
	    		if (inventory == null) {
	    			throw new NotFoundException("Item id not found: " + itemId);
	    		}
	    		else {
	    			return inventory;
	    		}
	    		
	    	
	    }
	    
}