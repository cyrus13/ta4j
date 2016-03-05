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
package eu.verdelhan.ta4j.indicators;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

import eu.verdelhan.ta4j.RealTimeTradeController;
import eu.verdelhan.ta4j.TestUtil;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import eu.verdelhan.ta4j.mocks.MockTimeSeries;

public class CachedIndicatorWithRealTimeDataTest {

	 private TimeSeries series;
	
    @Test
    public void rollingSeries(){   	
    	final double[] ticks = {1,2,3};
    	final DateTime[] times = {new DateTime(2016,01,01,6,0,0), new DateTime(2016,01,01,6,15,0), new DateTime(2016,01,01,6,30,0)};
    	series = new MockTimeSeries( ticks, times);
    	final RealTimeTradeController realTimeTradeController = new RealTimeTradeController(series);
    	final ClosePriceIndicator cpIndicator = new ClosePriceIndicator(series);
    	final SMAIndicator sma = new SMAIndicator(cpIndicator, 3);
    	realTimeTradeController.addRelevantCachedIndicators(cpIndicator,sma);
    	assertEquals(2,sma.getValue(2).toDouble(),TestUtil.ESPILON);
    	realTimeTradeController.addTrade(4, 5, new DateTime(2016,01,01,6,29,0));
    	assertEquals(2.66666,sma.getValue(2).toDouble(),TestUtil.ESPILON);
    }
	
}
