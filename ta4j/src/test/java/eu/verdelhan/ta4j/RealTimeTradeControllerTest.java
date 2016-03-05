/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Marc de Verdelhan & respective authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.verdelhan.ta4j;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

public class RealTimeTradeControllerTest {

	
	@Test(expected=IllegalArgumentException.class)
	public void emptyTimeSerices(){
		final TimeSeries ts = new TimeSeries(Collections.<Tick>emptyList());
		new RealTimeTradeController(ts);
	}
	
	@Test
	public void addTradesNewTick(){
		final Tick tick = new Tick(Period.minutes(15), new DateTime(2016,01,2,8,59,59));
		final TimeSeries ts = new TimeSeries(new ArrayList<Tick>(Arrays.asList(tick)));
		final RealTimeTradeController controller = new RealTimeTradeController(ts);
		
		controller.addTrade(1, 1.45, new DateTime(2016,01,2,9,0,0));
		assertEquals(1,ts.getEnd());
		assertEquals(1,ts.getLastTick().getAmount().toDouble(),TestUtil.ESPILON);
		assertEquals(1.45, ts.getLastTick().getClosePrice().toDouble(),TestUtil.ESPILON);
		assertEquals(1.45, ts.getLastTick().getMaxPrice().toDouble(),TestUtil.ESPILON);
		assertEquals(1.45, ts.getLastTick().getMinPrice().toDouble(),TestUtil.ESPILON);

		
		controller.addTrade(3, 1.55, new DateTime(2016,01,2,9,14,59));
		
		assertEquals(1,ts.getEnd());
		assertEquals(4,ts.getLastTick().getAmount().toDouble(),TestUtil.ESPILON);
		assertEquals(1.55, ts.getLastTick().getClosePrice().toDouble(),TestUtil.ESPILON);
		assertEquals(1.55, ts.getLastTick().getMaxPrice().toDouble(),TestUtil.ESPILON);
		assertEquals(1.45, ts.getLastTick().getMinPrice().toDouble(),TestUtil.ESPILON);
		
		controller.addTrade(1, 1.4, new DateTime(2016,01,2,9,15,0));
		
		assertEquals(2,ts.getEnd());
		assertEquals(1,ts.getLastTick().getAmount().toDouble(),TestUtil.ESPILON);
		assertEquals(1.4, ts.getLastTick().getClosePrice().toDouble(),TestUtil.ESPILON);
		assertEquals(1.4, ts.getLastTick().getMaxPrice().toDouble(),TestUtil.ESPILON);
		assertEquals(1.4, ts.getLastTick().getMinPrice().toDouble(),TestUtil.ESPILON);
	}
	
}
