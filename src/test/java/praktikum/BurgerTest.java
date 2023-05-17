package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    @Mock
    Bun bunMock;
    @Mock
    Ingredient ingredientMock;
    @Spy
    Burger burger = new Burger();

    @Test
    public void setBunsReturnsCorrectBun(){

        burger.setBuns(bunMock);
        Mockito.verify(burger).setBuns(bunMock);
        Mockito.verify(burger, Mockito.times(1)).setBuns(bunMock);
    }

    @Test
    public void addIngredientSuccess(){

        burger.addIngredient(ingredientMock);
        Mockito.verify(burger).addIngredient(ingredientMock);
        Mockito.verify(burger, Mockito.times(1)).addIngredient(ingredientMock);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredientMock, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientSuccess(){

        burger.addIngredient(ingredientMock);
        burger.removeIngredient(0);
        assertEquals(0, burger.ingredients.size());
    }

    @Test
    public void moveIngredientSuccess(){

        Ingredient first = Mockito.mock(Ingredient.class);
        Ingredient second = Mockito.mock(Ingredient.class);

        burger.addIngredient(first);
        burger.addIngredient(second);
        burger.addIngredient(ingredientMock);
        burger.moveIngredient(burger.ingredients.indexOf(ingredientMock), 0);
        assertEquals(3, burger.ingredients.size());
        assertEquals(ingredientMock, burger.ingredients.get(0));
    }

    @Test
    public void getPriceReturnCorrectPrice(){

        float bunPrice = 500;
        float ingredientPrice = 250;

        Mockito.when(bunMock.getPrice()).thenReturn(bunPrice);
        Mockito.when(ingredientMock.getPrice()).thenReturn(ingredientPrice);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock);

        assertEquals((bunPrice * 2) + ingredientPrice, burger.getPrice(), 0);
    }

    @Test
    public void getReceiptReturnCorrectReceipt() {
        String bunName = "Булочка с кунжутом";
        String ingredientName = "Котлетка сочная";
        float bunPrice = 500;
        float ingredientPrice = 250;

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock);
        burger.addIngredient(ingredientMock);

        Mockito.when(ingredientMock.getName()).thenReturn(ingredientName);
        Mockito.when(bunMock.getName()).thenReturn(bunName);
        Mockito.when(bunMock.getPrice()).thenReturn(bunPrice);
        Mockito.when(ingredientMock.getPrice()).thenReturn(ingredientPrice);
        Mockito.when(ingredientMock.getType()).thenReturn(IngredientType.FILLING);

        StringBuilder expected = new StringBuilder()
                .append(String.format("(==== Булочка с кунжутом ====)%n"))
                .append(String.format("= filling Котлетка сочная =%n"))
                .append(String.format("= filling Котлетка сочная =%n"))
                .append(String.format("(==== Булочка с кунжутом ====)%n"))
                .append(String.format("%nPrice: 1500,000000%n"));

        assertEquals("Неправильный текст", expected.toString(), burger.getReceipt());
    }
}
