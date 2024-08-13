import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {
	@Test
	public void nullHippodromeThrowException(){
		assertThrows(IllegalArgumentException.class, ()-> new Hippodrome(null));
	}

	@Test
	public void hippodromeNoNullMessage(){
		try{
			new Hippodrome(null);
		}catch (IllegalArgumentException e){
			assertEquals("Horses cannot be null.",e.getMessage());
		}
	}

	@Test
	public void blankHippodromeThrowException(){
	assertThrows(IllegalArgumentException.class, ()-> new Hippodrome(new ArrayList<>()));
	}

	@Test
	public void blankHippodromeMessage(){
		try{
			new Hippodrome(new ArrayList<>());
		}catch (IllegalArgumentException e){
			assertEquals("Horses cannot be empty.",e.getMessage());
		}
	}

	@Test
	public void getHorses(){
		List<Horse> horses= new ArrayList<>();
		for (int i = 0; i < 30 ; i++) {
			horses.add(new Horse("test"+i ,i,i));
		}
		Hippodrome hippodrome= new Hippodrome(horses);
		assertEquals(horses, hippodrome.getHorses());
	}

	@Test
	public void move() {
		List<Horse> horses= new ArrayList<>();
		for (int i = 0; i < 50 ; i++) {
			horses.add(mock(Horse.class));
		}
		new Hippodrome(horses).move();

		for (Horse horse: horses){
			verify(horse).move();
		}
	}

	@Test
	public void getWinner(){
		Horse horse1= new Horse("horse1",1,1.123);
		Horse horse2= new Horse("horse2",1,3.123);
		Horse horse3= new Horse("horse3",1,2.0);
		Horse horse4= new Horse("horse4",1,2.23);
		Hippodrome hippodrome= new Hippodrome(List.of(horse1, horse2, horse3, horse4));

		assertSame(horse2, hippodrome.getWinner());
	}
}
