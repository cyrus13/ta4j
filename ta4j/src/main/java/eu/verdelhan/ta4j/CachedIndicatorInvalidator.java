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

import java.util.HashSet;
import java.util.Set;

import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class CachedIndicatorInvalidator {

	private final Set<CachedIndicator> cachedIndicatorsMonitored = new HashSet<>();
	
	public CachedIndicatorInvalidator() {
		super();
	}
	
	public void addCachedIndicator(CachedIndicator cachedIndicator){
		cachedIndicatorsMonitored.add(cachedIndicator);
	}
	
	public void invalidateCachedIndicatorsAfterIndex(int index){
		for (CachedIndicator indicator: cachedIndicatorsMonitored){
			indicator.invalidateCachedResultsAfterIndex(index);
		}
	}
	
}
