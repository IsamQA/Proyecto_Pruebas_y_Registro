import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
	@Test
	public void nullThrowException(){
		assertThrows(IllegalArgumentException.class, () ->new Horse(null,1,1));
	}

	@Test
	public void nameNoNullMessage(){
		try{
			new Horse(null,1,1);
		}catch (IllegalArgumentException e){
			assertEquals("Name cannot be null.",e.getMessage());
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {" ", "	", "\t\t","\n\n\n\n" })
	public void blankNameMessage(String blankName){
		assertThrows(IllegalArgumentException.class,()-> new Horse(blankName,1,1));
	}

	@ParameterizedTest
	@ValueSource(strings = {" ", "	", "\t\t","\n\n\n\n" })
	public void nameNoBlankMessage(String blankName){
		try{
			new Horse(blankName,1,1);
		}catch (IllegalArgumentException e){
			assertEquals("Name cannot be blank.",e.getMessage());
		}
	}

	@Test
	public void negativeSpeedThrowException(){
		assertThrows(IllegalArgumentException.class, () ->new Horse("test",-1,1));
	}

	@Test
	public void negativeSpeedMessage(){
		try{
			new Horse("test",-1,1);
		}catch (IllegalArgumentException e){
			assertEquals("Speed cannot be negative.",e.getMessage());
		}
	}

	@Test
	public void negativeDistanceThrowException(){
		assertThrows(IllegalArgumentException.class, () ->new Horse("test",1,-1));
	}

	@Test
	public void negativeDistanceMessage(){
		try{
			new Horse("test",1,-1);
		}catch (IllegalArgumentException e){
			assertEquals("Distance cannot be negative.",e.getMessage());
		}
	}

	@Test
	public void getName() {
		String horseName= "Dash";
		Horse horse= new Horse(horseName,1,1);

		assertEquals(horseName, horse.getName());
	}

	@Test
	public void getSpeed() {
		double horseSpeed= 1;
		Horse horse= new Horse("test",horseSpeed,1);

		assertEquals(horseSpeed, horse.getSpeed());
	}

	@Test
	public void getDistance() {
		double horseDistance= 1;
		Horse horse= new Horse("test",1,horseDistance);

		assertEquals(horseDistance, horse.getDistance());
	}
	@Test
	public void zeroDistanceTwoParameters(){
		Horse horse= new Horse("test",1);

		assertEquals(0, horse.getDistance());
	}
	@Test
	public void getRandomDoubleMethodCalledInMoveMethod(){
		try(MockedStatic<Horse> mockedStatic= mockStatic(Horse.class)){
			new Horse("test", 1, 1).move();

			mockedStatic.verify(()->Horse.getRandomDouble(0.2,0.9));
		}

	}
	@ParameterizedTest
	@ValueSource(doubles = { 0.1, 0.2, 0.5, 1.0, 111.11, 222.222 , 0.0 })
	public void move(double values){
		double speed= 20;
		double distance= 200;
		try(MockedStatic<Horse> mockedStatic= mockStatic(Horse.class)){
			Horse horse= new Horse("test", speed, distance);
			mockedStatic.when(()->Horse.getRandomDouble(0.2, 0.9)).thenReturn(values);

			horse.move();

			assertEquals(distance + speed * values, horse.getDistance());
		}
	}

}
