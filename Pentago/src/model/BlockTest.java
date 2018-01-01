package model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BlockTest {
	
	private Block empty;
	private Block full;
	private Block horiz;
	private Block vertic;
	private Block diag;

	@Before
	public void setUp() {
		empty = new Block();
		full = new Block("123456789");
		horiz = new Block("...456...");
		vertic = new Block(".2..5..8.");
		diag = new Block("1...5...9");
	}

	@Test
	public void testIsLegalMove() {
		assertEquals(true, empty.isLegalMove(5));
		assertEquals("Legal move, full", false, full.isLegalMove(2));
		assertEquals("Legal move, horiz", true, horiz.isLegalMove(1));
		assertEquals("Legal move, horiz", false, horiz.isLegalMove(4));
		assertEquals("Legal move, vertic", true, vertic.isLegalMove(3));
		assertEquals("Legal move, vertic", false, vertic.isLegalMove(8));
		assertEquals("Legal move, diag", true, diag.isLegalMove(7));
		assertEquals("Legal move, diag", false, diag.isLegalMove(9));
	}

	@Test
	public void testMove() {
		assertEquals("Move, empty", new Block("b........"), empty.move(1, 'b'));
		assertEquals("Move, horiz", new Block(".w.456..."), horiz.move(2, 'w'));
		assertEquals("Move, vertic", new Block(".2b.5..8."), vertic.move(3, 'b'));
		assertEquals("Move, diag", new Block("1...5w..9"), diag.move(6, 'w'));
	}

	@Test
	public void testRotateLeft() {
		assertEquals("Rotate left, empty", new Block(), empty.rotateLeft());
		assertEquals("Rotate left, full", new Block("369258147"), full.rotateLeft());
		assertEquals("Rotate left, horiz", new Block(".6..5..4."), horiz.rotateLeft());
	}

	@Test
	public void testRotateRight() {
		assertEquals("Rotate right, empty", new Block(), empty.rotateRight());
		assertEquals("Rotate right, full", new Block("741852963"), full.rotateRight());
		assertEquals("Rotate right, vertic", new Block("...852..."), vertic.rotateRight());
	}

}
