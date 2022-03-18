package org.example;

import dataentities.Location;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class SerializationOfJavaObjects {

    // deserialization = transform an API response (can be in XML or in JSON format) to an instance of
    // an actual Java object.
    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        Location location =
                given().
                        when().
                        get("http://api.zippopotam.us/us/90210").
                        as(Location.class);

        Assert.assertEquals(
                "Beverly Hills",
                location.getPlaces().get(0).getPlaceName()
        );
    }

    // serializing a Java Object to a JSON request body =  we have an instance of a Java object, we want to be able to
    // automatically convert that to an XML or JSON request body so we can send data to an API instead of just
    // retrieving data from an API and then converting it into a Java object

    @Test
    public void sendLvZipCode1050_checkStatusCode_expect200() {

        Location location = new Location();
        location.setCountry("Netherlands");

        given().
                contentType(ContentType.JSON).
                body(location).
                log().body().
                when().
                post("http://localhost:9876/lv/1050").
                then().
                assertThat().
                statusCode(200);
    }
}
