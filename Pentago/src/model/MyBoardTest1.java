package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyBoardTest1 {
	
	private MyBoard empty;
	private MyBoard full;

	@Before
	public void setUp() throws Exception {
		empty = new MyBoard();
		full = new MyBoard(new Block("wbwbwbwbw"), new Block("vwvwvwvwv"), new Block("wbwbwbwbw"), 
				new Block("bwbwbwbwb"), "");
	}

	@Test
	public void testCalculateUtility() {
		assertEquals(0, empty.calculateUtility('w'));
		assertEquals(0, full.calculateUtility('b'));
		
		MyBoard horiz = new MyBoard(new Block("...www..."), new Block("...ww...."), new Block(), new Block(), "");
		assertEquals(130, horiz.calculateUtility('w'));
		assertEquals(-130, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(".......bb"), new Block("......bbb"), new Block(), new Block(), "");
		assertEquals(130, horiz.calculateUtility('b'));
		assertEquals(-130, horiz.calculateUtility('w'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block(".ww......"), new Block("www......"), "");
		assertEquals(130, horiz.calculateUtility('w'));
		assertEquals(-130, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block("......bbb"), new Block("......bbw"), "");
		assertEquals(100, horiz.calculateUtility('b'));
		assertEquals(-100, horiz.calculateUtility('w'));
		
		horiz = new MyBoard(new Block("bww........."), new Block("wbb......"), new Block(), new Block(), "");
		assertEquals(0, horiz.calculateUtility('w'));
		assertEquals(0, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block("...bbb..."), new Block(), new Block(), new Block(), "");
		assertEquals(-15, horiz.calculateUtility('w'));
		assertEquals(15, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block("......b.b"), new Block("......b.."), new Block(), new Block(), "");
		assertEquals(0, horiz.calculateUtility('w'));
		assertEquals(0, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block(), new Block("www......"), "");
		assertEquals(15, horiz.calculateUtility('w'));
		assertEquals(-15, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block("....bw..."), new Block("...ww...."), "");
		assertEquals(0, horiz.calculateUtility('w'));
		assertEquals(0, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block("......w.w"), new Block("........w"), "");
		assertEquals(0, horiz.calculateUtility('w'));
		assertEquals(0, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(".ww......"), new Block("ww......."), new Block(), new Block(), "");
		assertEquals(30, horiz.calculateUtility('w'));
		assertEquals(-30, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block("...b.b..."), new Block("...bb...."), new Block(), new Block(), "");
		assertEquals(-45, horiz.calculateUtility('w'));
		assertEquals(45, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block("......bb."), new Block("......bb."), new Block(), new Block(), "");
		assertEquals(-30, horiz.calculateUtility('w'));
		assertEquals(30, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block("www......"), new Block(".w......."), "");
		assertEquals(30, horiz.calculateUtility('w'));
		assertEquals(-30, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block("...w.w..."), new Block("....ww..."), "");
		assertEquals(0, horiz.calculateUtility('w'));
		assertEquals(0, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block("......bbb"), new Block("......b.."), "");
		assertEquals(-45, horiz.calculateUtility('w'));
		assertEquals(45, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block("........b"), new Block("......bbb"), "");
		assertEquals(-45, horiz.calculateUtility('w'));
		assertEquals(45, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block(), new Block(), new Block(".......wb"), new Block("......bbb"), "");
		assertEquals(0, horiz.calculateUtility('w'));
		assertEquals(0, horiz.calculateUtility('b'));
		
		horiz = new MyBoard(new Block("......bbw"), new Block("......bb."), new Block(), new Block(), "");
		assertEquals(0, horiz.calculateUtility('w'));
		assertEquals(0, horiz.calculateUtility('b'));
		
		// column test
		MyBoard vertic = new MyBoard(new Block("w..w..w.."), new Block(), new Block("w..w....."), new Block(), "");
		assertEquals(130, vertic.calculateUtility('w'));
		
		vertic = new MyBoard(new Block("....b..b."), new Block(), new Block(".b..b..b."), new Block(), "");
		assertEquals(130, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block("..w..w..w"), new Block(), new Block("..w..w..."), new Block(), "");
		assertEquals(130, vertic.calculateUtility('w'));
		
		vertic = new MyBoard(new Block(), new Block("...b..b.."), new Block(), new Block("b..b..b.."), "");
		assertEquals(130, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block(), new Block(".w..w..w."), new Block(), new Block(".w..w...."), "");
		assertEquals(130, vertic.calculateUtility('w'));
		
		vertic = new MyBoard(new Block(), new Block(".....b..b"), new Block(), new Block("..b..b..b"), "");
		assertEquals(130, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block("b..b..b.."), new Block(), new Block("b........"), new Block(), "");
		assertEquals(45, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block(".......w."), new Block(), new Block(".w..w..w."), new Block(), "");
		assertEquals(-45, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block(".....b..b"), new Block(), new Block("..b..b..."), new Block(), "");
		assertEquals(30, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block("..b.....b"), new Block(), new Block("..b..b..."), new Block(), "");
		assertEquals(45, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block(), new Block("...w..w.."), new Block(), new Block("...w..w.."), "");
		assertEquals(-30, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block(), new Block(".b..b..b."), new Block(), new Block("....b...."), "");
		assertEquals(30, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block(), new Block("..b.....b"), new Block(), new Block(".....b..b"), "");
		assertEquals(0, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block("b..b..b.."), new Block(), new Block(), new Block(), "");
		assertEquals(15, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block("....w..w."), new Block(), new Block(".w......."), new Block(), "");
		assertEquals(-15, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block("........b"), new Block(), new Block("..b..b..."), new Block(), "");
		assertEquals(15, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block(), new Block(), new Block(), new Block("b..b..b.."), "");
		assertEquals(15, vertic.calculateUtility('b'));
		
		vertic = new MyBoard(new Block(), new Block("..w.....w"), new Block(), new Block(".....w..."), "");
		assertEquals(0, vertic.calculateUtility('b'));
		
		// diagonals tests
		MyBoard diag = new MyBoard(new Block("b...b...b"), new Block(), new Block(), new Block("b...b...."), "");
		assertEquals(-130, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block(".w...w..."), new Block("......w.."), new Block(), new Block(".w...w..."), "");
		assertEquals(100, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block("...w...w."), new Block(), new Block("..w......"), new Block("...w...w."), "");
		assertEquals(100, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block(), new Block("..w.w.w.."), new Block("..w.w...."), new Block(), "");
		assertEquals(130, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block("........b"), new Block(".b.b....."), new Block(".b.b....."), new Block(), "");
		assertEquals(-100, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block(), new Block(".....b.b."), new Block(".....b.b."), new Block("b........"), "");
		assertEquals(-100, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block("....b...b"), new Block(), new Block(), new Block("b...b...b"), "");
		assertEquals(-130, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block(), new Block("....w.w.."), new Block("..w.w.w.."), new Block(), "");
		assertEquals(130, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block("b...b...b"), new Block(), new Block(), new Block("b........"), "");
		assertEquals(-45, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block(".b...b..."), new Block("......b.."), new Block(), new Block(".b......."), "");
		assertEquals(-30, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block("...w...w."), new Block(), new Block("..w......"), new Block("...w....."), "");
		assertEquals(30, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block(".....w..."), new Block("......w.."), new Block(), new Block(".w...w..."), "");
		assertEquals(30, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block("b.......b"), new Block(), new Block(), new Block("b...b...."), "");
		assertEquals(-45, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block("....b...b"), new Block(), new Block(), new Block("b...b...."), "");
		assertEquals(-30, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block("....b...."), new Block(), new Block(), new Block("b...b...b"), "");
		assertEquals(-30, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block(), new Block("..w.w...."), new Block("..w.w...."), new Block(), "");
		assertEquals(30, diag.calculateUtility('w'));
		
		diag = new MyBoard(new Block(), new Block("..w.w.w.."), new Block("....w...."), new Block(), "");
		assertEquals(30, diag.calculateUtility('w'));
		
		// complex
		MyBoard all = new MyBoard(new Block("...b....b"), new Block("....w.ww."), new Block("b.wb.wb.w"), new Block("bw.bbb..."), "");
		assertEquals(30, all.calculateUtility('b'));
		
		all = new MyBoard(new Block("wwwwwb.wb"), new Block(".w..b.b.."), new Block("w.bwb.b.b"), new Block("bbb.bb..b"), "");		
		assertEquals(145, all.calculateUtility('b'));
		
		all = new MyBoard(new Block("b...wwbbw"), new Block(".bww..wb."), new Block("bbwb..www"), new Block(".bbwb.w.b"), "");
		assertEquals(-45, all.calculateUtility('b'));
	}

	@Test
	public void testIsLegalMove() {
		assertEquals(true, empty.isLegalMove(1, 5));
		assertEquals(false, full.isLegalMove(2, 5));
	}

	@Test
	public void testMakeMove() {
		assertEquals(new MyBoard(new Block("w........"), new Block(), new Block(), new Block(), ""), empty.makeMove(1, 1, 'w'));
	}

	@Test
	public void testRotate() {
		MyBoard all = new MyBoard(new Block("b...wwbbw"), new Block(".bww..wb."), new Block("bbwb..www"), new Block(".bbwb.w.b"), "");
		assertEquals(new MyBoard(new Block("b.bbw.ww."), new Block(".bww..wb."), new Block("bbwb..www"), new Block(".bbwb.w.b"), ""), 
				all.rotate(1, 'r'));
	}

	@Test
	public void testCheckWin() {
		MyBoard all = new MyBoard(new Block("b...wwbbw"), new Block(".bww..wb."), new Block("bbwb..www"), new Block(".bbwb.w.b"), "");
		assertEquals("", all.checkWin());
		
		all = new MyBoard(new Block("wwwwwb.wb"), new Block(".w..b.b.."), new Block("w.bwb.b.b"), new Block("bbb.bb..b"), "");
		assertEquals("b", all.checkWin());
	}

}
