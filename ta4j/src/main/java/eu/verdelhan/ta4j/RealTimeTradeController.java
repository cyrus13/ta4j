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

import org.joda.time.DateTime;

import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class RealTimeTradeController{

	private final TimeSeries timeSeries;
	private CachedIndicatorInvalidator invalidator = new CachedIndicatorInvalidator();
	
	public RealTimeTradeController(TimeSeries timeSeries) throws IllegalArgumentException {
		super();
		if (timeSeries.getEnd()<0){
			throw new IllegalArgumentException("Time Series needs to be initialized with some ticks");
		}
		this.timeSeries = timeSeries;
	}
	
	public void addTrade(double tradeAmount, double tradePrice, DateTime tradeTime){
		if (shouldCreateNewTick(tradeTime)){
			if (timeSeries.getEnd()>0){
				invalidator.invalidateCachedIndicatorsAfterIndex(timeSeries.getEnd()-1);
			}
			final Tick tick = new Tick(timeSeries.getTimePeriod(), timeSeries.getLastTick().getEndTime().plus(timeSeries.getTimePeriod()));
			timeSeries.addTick(tick);
			
		}
		
		invalidator.invalidateCachedIndicatorsAfterIndex(timeSeries.getEnd());
		timeSeries.getLastTick().addTrade(tradeAmount, tradePrice);
		
	}
	
	private boolean shouldCreateNewTick(DateTime tradeTime){
		if (timeSeries.getEnd() <0){
			return true;
		}else{
			return !isCurrentTradeInLastTickPeriod(tradeTime);
		}
	}
	
	private boolean isCurrentTradeInLastTickPeriod(DateTime tradeTime){
		final Tick lastTick =  timeSeries.getLastTick();
		if (!tradeTime.isAfter(lastTick.getEndTime())){
			return true;
		}else{
			return false;
		}
	}
	
	public void addRelevantCachedIndicator(CachedIndicator cachedIndicator){
		invalidator.addCachedIndicator(cachedIndicator);
	}
	
	
	public void addRelevantCachedIndicators(CachedIndicator... cachedIndicators){
		for (CachedIndicator cachedIndicator:cachedIndicators){
			invalidator.addCachedIndicator(cachedIndicator);
		}
	}
}
