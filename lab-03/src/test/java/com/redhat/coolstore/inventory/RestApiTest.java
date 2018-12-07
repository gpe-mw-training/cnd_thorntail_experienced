package com.redhat.coolstore.inventory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

@RunWith(Arquillian.class)
public class RestApiTest {

    private static String port = System.getProperty("arquillian.swarm.http.port", "18080");

    private Client client;

    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        Properties properties = new Properties();
        properties.put("swarm.http.port", port);
        return new Swarm(properties).withProfile("local");
    }

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, RestApplication.class.getPackage())
                .addAsResource("project-local.yml", "project-local.yml")
                .addAsResource("META-INF/test-persistence.xml",  "META-INF/persistence.xml")
                .addAsResource("META-INF/test-load.sql",  "META-INF/test-load.sql");
    }

    @Before
    public void before() throws Exception {
        client = ClientBuilder.newClient();
    }

    @After
    public void after() throws Exception {
        client.close();
    }

    
    @Test
    @RunAsClient
    public void testGetInventory() throws Exception {

        // create a target to the inventory endpoint
        WebTarget target = client.target("http://localhost:" + port).path("/inventory").path("/123456");

        // call the endpoint using the GET method. Passing `accepts` header for JSON
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        // confirm success HTTP status code of 200
        assertThat(response.getStatus(), equalTo(new Integer(200)));

        // Parse string as a JSON object
        JsonObject value = Json.parse(response.readEntity(String.class)).asObject();

        // check contents of JSON payload
        assertThat(value.getString("itemId", null), equalTo("123456"));
        assertThat(value.getString("location", null), equalTo("location"));
        assertThat(value.getInt("quantity", 0), equalTo(new Integer(99)));
        assertThat(value.getString("link", null), equalTo("link"));

    }  
    
    @Test
    @RunAsClient
    public void testGetInventorWhenItemIdDoesNotExist() throws Exception {

        // create a target to the inventory endpoint
        WebTarget target = client.target("http://localhost:" + port).path("/inventory").path("/999999");

        // call the endpoint with a non-existent `itemId`. Use the GET method. Passing `accepts` header for JSON
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        // confirm HTTP status code of 404
        assertThat(response.getStatus(), equalTo(new Integer(404)));

    }    
}