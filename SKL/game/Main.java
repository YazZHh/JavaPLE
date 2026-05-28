// == MAIN ==
package game;

import engine.Game;
import engine.Grid;
import engine.ISU;
import engine.Entity;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_METHOD)
public class Main {
	
	@BeforeEach
	public void resetGameSingleton() {
		try {
			Field instance = Game.class.getDeclaredField("game");
			instance.setAccessible(true);
			instance.set(null, null);
		} catch (Exception e) {
			fail("Impossible de reset le singleton Game via Réflexion : " + e.getMessage());
		}
	}

	@Test
	public void test00() {
		System.out.println("Test00 :");
		Game game = new Game(2, 2);
		Grid grid = game.grid;
		game.show(System.out);
		assertEquals(grid.width(), game.width_ncell);
		assertEquals(grid.height(), game.height_ncell);
		System.out.println("Test00 : PASSED\n\n");
	}

	@Test
	public void test01() {
		System.out.println("Test01 :");
		Game game = new Game(5, 5);
		Entity ghost = new Entity("Blinky");
		ghost.show(System.out);
		assertEquals("Blinky", ghost.name);
		assertEquals(0, ghost.orientation_degree);
		assertNull(ghost.position);
		assertNull(ghost.center);
		System.out.println("Test01 : PASSED\n\n");
	}

	@Test
	public void test02() {
		System.out.println("Test02 :");
		Game game = new Game(4, 4);
		Grid grid = game.grid;
		Entity pacman = new Entity("PacMan");

		Grid.Position startPos = grid.new Position(1, 1);
		pacman.setPosition(startPos.copy());
		pacman.setCoord(startPos.toISUCoordCentered());
		grid.cellAt(startPos).add(pacman);
		
		pacman.show(System.out);
		System.out.println();
		grid.cellAt(startPos).show(System.out);
		grid.cellAt(grid.new Position(3, 2)).show(System.out);
		System.out.println();
		
		Grid.Vector v = grid.new Vector(2, 1);
		pacman.translate(v);
		System.out.println("Déplacement discret : +2 cases sur X, +1 case sur Y");
		pacman.show(System.out);
		System.out.println();
		grid.cellAt(startPos).show(System.out);
		grid.cellAt(grid.new Position(3, 2)).show(System.out);
		
		assertEquals(3, pacman.position.x);
		assertEquals(2, pacman.position.y);
		assertEquals(-1.85, pacman.center.x_cm, 0.001);
		assertEquals(-5.55, pacman.center.y_cm, 0.001);
		
		assertTrue(grid.cellAt(grid.new Position(3, 2)).entities.contains(pacman));
		assertFalse(grid.cellAt(startPos).entities.contains(pacman));
		System.out.println("Test02 : PASSED\n\n");
	}

	@Test
	public void test03() {
		System.out.println("Test03 :");
		Game game = new Game(4, 4);
		Grid grid = game.grid;
		Entity pacman = new Entity("PacMan");
		
		Grid.Position startPos = grid.new Position(0, 0);
		pacman.setPosition(startPos);
		pacman.setCoord(startPos.toISUCoordCentered());
		grid.cellAt(startPos).add(pacman);
		pacman.show(System.out);
		System.out.println();
		
		System.out.println("Déplacement continu : on avance de 4.0 cm en X et 0.0 cm en Y");
		ISU.Vector v = game.isu.new Vector(4.0, 0.0);
		pacman.translate(v);
		pacman.show(System.out);
		System.out.println();
		
		assertEquals(5.85, pacman.center.x_cm, 0.001);
		assertEquals(1, pacman.position.x);
		assertEquals(0, pacman.position.y);
		assertTrue(grid.cellAt(pacman.position).entities.contains(pacman));
		assertFalse(grid.cellAt(startPos).entities.contains(pacman));
		System.out.println("Test03 : PASSED\n\n");
	}

	@Test
	public void test04() {
		System.out.println("Test04 : Test des limites du monde sur l'axe X");
		Game game = new Game(3, 3);
		Grid grid = game.grid;
		Entity pacman = new Entity("TorusRunnerX");
		
		Grid.Position rightEdge = grid.new Position(2, 1); // Case tout à droite
		pacman.setPosition(rightEdge);
		pacman.setCoord(rightEdge.toISUCoordCentered());
		grid.cellAt(rightEdge).add(pacman);
		
		pacman.show(System.out);
		System.out.println();
		grid.cellAt(rightEdge).show(System.out);
		
		System.out.println("Fait un pas vers l'Est sur la grille (sort à droite)");
		pacman.moveEast(1);
		pacman.show(System.out);
		
		assertEquals(0, pacman.position.x);
		assertEquals(1, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(0, 1)).entities.contains(pacman));
		System.out.println("Test04 : PASSED\n\n");
	}

	@Test
	public void test05() {
		System.out.println("Test05 : Test des limites du monde sur l'axe Y");
		Game game = new Game(3, 3);
		Grid grid = game.grid;
		Entity pacman = new Entity("TorusRunnerY");
		pacman.show(System.out);
		System.out.println();
		
		Grid.Position topEdge = grid.new Position(1, 0);
		pacman.setPosition(topEdge);
		pacman.setCoord(topEdge.toISUCoordCentered());
		grid.cellAt(topEdge).add(pacman);
		
		System.out.println("Bouge de 1 case vers le Nord (sort par le haut)");
		pacman.moveNorth(1);
		pacman.show(System.out);
		
		assertEquals(1, pacman.position.x);
		assertEquals(2, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(1, 2)).entities.contains(pacman));
		System.out.println("Test05 : PASSED\n\n");
	}

	@Test
	public void test06() {
		System.out.println("Test06 : Test des rotations");
		Game game = new Game(5, 5);
		Entity pacman = new Entity("Spinner");
		pacman.show(System.out);
		System.out.println();
		
		assertEquals(0, pacman.orientation_degree);
		
		System.out.println("Tourne de 90 degrés à droite");
		pacman.turn(90);
		pacman.show(System.out);
		System.out.println();
		assertEquals(90, pacman.orientation_degree);
	
		System.out.println("Dépassement des 360 degrés");
		pacman.turn(300);
		pacman.show(System.out);
		System.out.println();
		assertEquals(30, pacman.orientation_degree);
		
		System.out.println("Gestion des angles négatifs (tourner à gauche)");
		pacman.turn(-40);
		pacman.show(System.out);
		assertEquals(350, pacman.orientation_degree); // -10 + 360 = 350
		System.out.println("Test06 : PASSED\n\n");
	}

	@Test
	public void test07() {
		System.out.println("Test07 : Test du move en cm");
		Game game = new Game(5, 5);
		Grid grid = game.grid;
		Entity pacman = new Entity("ContinuousMover");
		
		Grid.Position centerPos = grid.new Position(2, 2);
		pacman.setPosition(centerPos.copy());
		pacman.setCoord(centerPos.toISUCoordCentered()); // (9.25, 9.25)
		grid.cellAt(centerPos).add(pacman);
		pacman.show(System.out);
		System.out.println();
		
		pacman.moveEast(3.7);
		pacman.show(System.out);
		System.out.println();
		assertEquals(-5.55, pacman.center.x_cm, 0.001);
		assertEquals(3, pacman.position.x);
		assertTrue(grid.cellAt(grid.new Position(3, 2)).entities.contains(pacman));
		// x_cm = -5,55, y_cm = -9,25
		
		pacman.moveNorth(7.2);
		pacman.show(System.out);
		System.out.println();
		assertEquals(2.05, pacman.center.y_cm, 0.001);
		assertEquals(0, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(3, 0)).entities.contains(pacman));
		// x_cm = -5,55, y_cm = 2,05
		
		pacman.moveWest(5.4);
		pacman.show(System.out);
		System.out.println();
		assertEquals(7.55, pacman.center.x_cm, 0.001);
		assertEquals(2, pacman.position.x);
		assertTrue(grid.cellAt(grid.new Position(2, 0)).entities.contains(pacman));
		// x_cm = 7,55, y_cm = 2,05
		
		pacman.moveSouth(3.9);
		pacman.show(System.out);
		System.out.println();
		assertEquals(5.95, pacman.center.y_cm, 0.001);
		assertEquals(1, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(2, 1)).entities.contains(pacman));
		// x_cm = 7,55, y_cm = -5,95		
		
		System.out.println("Test07 : PASSED\n\n");
	}
	
	@Test
	public void test08() {
		System.out.println("Test08 : Test du move en ncell (cases)");
		Game game = new Game(5, 5);
		Grid grid = game.grid;
		Entity pacman = new Entity("DiscreteMover");
		
		Grid.Position centerPos = grid.new Position(2, 2);
		pacman.setPosition(centerPos.copy());
		pacman.setCoord(centerPos.toISUCoordCentered());
		grid.cellAt(centerPos).add(pacman);
		pacman.show(System.out);
		System.out.println();
		
		pacman.moveEast(1);
		System.out.println("moveEast(1)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(3, pacman.position.x);
		assertEquals(2, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(3, 2)).entities.contains(pacman));
		assertFalse(grid.cellAt(centerPos).entities.contains(pacman));
		
		pacman.moveNorth(2);
		System.out.println("moveNorth(2)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(3, pacman.position.x);
		assertEquals(0, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(3, 0)).entities.contains(pacman));
		
		pacman.moveEast(2);
		System.out.println("moveEast(2)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(0, pacman.position.x);
		assertEquals(0, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(0, 0)).entities.contains(pacman));

		pacman.moveNorth(1);
		System.out.println("moveNorth(1)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(0, pacman.position.x);
		assertEquals(4, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(0, 4)).entities.contains(pacman));

		pacman.moveWest(1);
		System.out.println("moveWest(1)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(4, pacman.position.x);
		assertEquals(4, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(4, 4)).entities.contains(pacman));
		
		pacman.moveSouth(2);
		System.out.println("moveSouth(2)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(4, pacman.position.x);
		assertEquals(1, pacman.position.y);
		assertTrue(grid.cellAt(grid.new Position(4, 1)).entities.contains(pacman));
		
		System.out.println("Test08 : PASSED\n\n");
	}
}