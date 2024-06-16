package org.example;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class GeodeTest {
    private ClientCache cache;
    private Region<String, String> region;
    private Region<CustomerKey, Customer> customerRegion;

    @Before
    public void connect() {
        this.cache = new ClientCacheFactory()
                .addPoolLocator("localhost", 10334)
                .create();
        this.region = cache.<String, String>
                        createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
                .create("regionA");
        this.customerRegion = cache.<CustomerKey, Customer>
                        createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
                .create("regionB");
    }

    @Test
    public void testConnect() {
        this.region.put("A", "Hello");
        this.region.put("B", "Vipul");

        assertEquals("Hello", region.get("A"));
        assertEquals("Vipul", region.get("B"));
    }

    @Test
    public void whenPutMultipleValuesAtOnce_thenValuesSavedSuccessfully() {

        Supplier<Stream<String>> keys = () -> Stream.of("A", "B", "C", "D", "E");
        Map<String, String> values = keys.get()
                .collect(Collectors.toMap(Function.identity(), String::toLowerCase));

        this.region.putAll(values);

        keys.get()
                .forEach(k -> assertEquals(k.toLowerCase(), this.region.get(k)));
    }

    /**
     * This method requires to pass jar file while running geode
     */
    @Test
    public void whenPutCustomKey_thenValuesSavedSuccessfully() {
        CustomerKey key = new CustomerKey(123);
        Customer customer = new Customer(key, "Vipul", "Tailor", 35);

        this.customerRegion.put(key, customer);

        Customer storedCustomer = this.customerRegion.get(key);
        assertEquals("Vipul", storedCustomer.getFirstName());
        assertEquals("Tailor", storedCustomer.getLastName());
    }
}
