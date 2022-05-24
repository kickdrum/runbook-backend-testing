package com.runbook.backend.testing;

import com.runbook.backend.testing.dao.ProductRepository;
import com.runbook.backend.testing.dto.Circle;
import com.runbook.backend.testing.dto.ProductDto;
import com.runbook.backend.testing.entity.ProductEntity;
import com.runbook.backend.testing.service.ProductService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@DataJpaTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class GoodTestingPracticesTest {

    private static ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeClass
    public static void setup() {
        productService = new ProductService();

    }

    /**
     *  It is unnecessary to write your own catch blocks that exist only to
     *  pass a test because the JUnit framework takes care of the situation for you.
     */
    // Don't do this
    @Test
    public void testWithExceptionBad() {
        boolean wasExceptionThrown = false;
        try{
            new ProductDto().functionThrowsException();
        }
        catch (final Exception e){
            wasExceptionThrown = true;
        }
        assertTrue(wasExceptionThrown);

    }

    // Do this instead
    @Test(expected = IOException.class)
    public void testWithException() throws Exception {
        new ProductDto().functionThrowsException();
    }

    /**
     *  If you are going to use variables in an equals assertion, prefix the variables
     *  with “actual” and “expected”. This increases the readability and clarifies the
     *  intention of the variable. Moreover, it’s harder to mix them up in the equals assertion.
     */
    //Don't do this
    @Test
    public void testCalculateAreaBadVariables() {
        double area1 = Circle.calculateArea(1d);
        double area2 = 3.141592653589793;
        assertEquals(area2, area1, 0);
    }

    // Do this instead - Use the prefixes “actual*” and “expected*”
    @Test
    public void testCalculateAreaIdealVariables() {
        double actualArea = Circle.calculateArea(1d);
        double expectedArea = 3.141592653589793;
        assertEquals(expectedArea, actualArea, 0);
    }

    /**
     *  Don’t repeat the logic under test when verifying the expected result of
     *  tests. Instead, use known, hard-coded, pre-calculated values.
     */
    // Don't do this
    @Test
    public void testCalculateAreaBad() {
        double actualArea = Circle.calculateArea(2d);
        double expectedArea = 3.141592653589793 * 2 * 2;
        assertEquals(expectedArea, actualArea, 0);
    }

    // Do this instead - expectedValues should always be hard-coded
    @Test
    public void testCalculateArea() {
        double actualArea = Circle.calculateArea(2d);
        double expectedArea = 12.566370614359172;
        assertEquals(expectedArea, actualArea, 0);
    }

    /**
     *  One Assert Per Test Method:
     *  For a unit test to be effective, keep one use case at a time,
     *  that is to have only one assertion in the tests.
     *
     */
    //Don't do this
    @Test()
    public void testProductBad() {
        productService.setProductRepository(productRepository);
        ProductEntity television = new ProductEntity(1, "2022-05-22", "television");
        ProductEntity laptop = new ProductEntity(2, "2022-04-22", "laptop");

        insertIntoDatabase(television);
        insertIntoDatabase(laptop);

        assertEquals(television.getName(), productRepository.findProductEntityByName("television").getName());
        assertEquals(laptop.getDate(), productRepository.findProductEntityByDate("2022-04-22").getDate());

    }

    //Do this instead
    @Test
    public void testProductByDateCreated() {
        productService.setProductRepository(productRepository);
        ProductEntity laptop = new ProductEntity(2, "2022-04-22", "laptop");
        insertIntoDatabase(laptop);
        assertEquals(laptop.getDate(), productRepository.findProductEntityByDate("2022-04-22").getDate());
    }

    //Do this instead
    @Test
    public void testProductByName() {
        productService.setProductRepository(productRepository);
        ProductEntity television = new ProductEntity(1, "2022-05-22", "television");
        insertIntoDatabase(television);
        assertEquals(television.getName(), productRepository.findProductEntityByName("television").getName());

    }

    /**
     * Don’t Hide the Relevant Parameters (in Helper Functions):
     * You should use helper functions for creating data and assertions, but you have to parameterize them.
     * Define a parameter for everything that is important for the test and needs to be controlled by the test.
     * Don’t force the reader to jump to a function definition in order to understand the test.
     */
    // Don't do this
    @Test
    public void testProductWithHelperFunctionBad() {
        // Using helper functions
        insertIntoDatabase();
        ProductDto actualProducts = requestProductsByName();
        ProductDto expectedProduct = new ProductDto(1, "2022-03-01", "television");
        assertEquals(actualProducts.getName(), expectedProduct.getName());
    }

    // Do this instead
    @Test
    public void testProductWithHelperFunctions() {
        productService.setModelMapper(modelMapper);
        insertIntoDatabase(new ProductEntity(4, "2022-03-05", "chair"));
        ProductDto actualProducts = productService.getProductByName("chair");
        ProductDto expectedProduct = new ProductDto(4, "2022-03-05", "chair");
        assertEquals(actualProducts.getName(), expectedProduct.getName());
    }

    /**
     *  Mapping code is a common example where the logic in tests is rewritten. So let’s
     *  assume our tests contain a method convertToDto() which result is used to assert
     *  that a returned DTO contains the same values as the entities that have been inserted
     *  at the beginning of the test. In this case, you’ll most likely end up rewriting the
     *  production logic in the test code, which can contain bugs.
     */
    // Don't do this
    @Test
    public void testProductProdCodeBad() {
        ProductEntity inputEntity = new ProductEntity(23, "2022-09-01", "table");
        insertIntoDatabase(inputEntity);

        ProductDto actualDTO = productService.getProductByName("table");

        // convertToDto() contains the same mapping logic as the production code
        ProductDto expectedDTO = productService.convertToDto(inputEntity);
        assertEquals(actualDTO.getName(), expectedDTO.getName());
    }

    // Do this instead
    @Test
    public void testProduct() {
        insertIntoDatabase(new ProductEntity(23, "2022-09-01", "table"));
        ProductDto actualDTO = productService.getProductByName("table");

        ProductDto expectedDTO = new ProductDto(23, "2022-09-01", "table");
        assertEquals(actualDTO.getName(), expectedDTO.getName());
    }


    private ProductDto requestProductsByName() {
        return new ProductDto(1, "2022-03-01", "television");
    }

    private void insertIntoDatabase() {
        productRepository.save(new ProductEntity(1, "2022-05-05", "television"));
    }

    private void insertIntoDatabase(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }


}
