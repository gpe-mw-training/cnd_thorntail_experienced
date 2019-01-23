package com.redhat.coolstore.inventory.service;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.redhat.coolstore.inventory.model.Inventory;

@ApplicationScoped
public class InventoryService {

    @PersistenceContext(unitName = "primary")
    private EntityManager entityManager;
    
    public Inventory getInventory(String itemId) {

    	  return entityManager.find(Inventory.class, itemId);

   	}    
}
