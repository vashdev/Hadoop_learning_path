/**
 * Copyright 2012 Jee Vang 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License. 
 */
package demo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

/**
 * Stock key. This key is a composite key. The "natural"
 * key is the symbol. The secondary sort will be performed
 * against the timestamp.
 * @author Jee Vang
 *
 */
public class StockKey implements WritableComparable<StockKey> {

	private String symbol;
	private Long timestamp;
	
	/**
	 * Constructor.
	 */
	public StockKey() { }
	
	/**
	 * Constructor.
	 * @param symbol Stock symbol. i.e. APPL
	 * @param timestamp Timestamp. i.e. the number of milliseconds since January 1, 1970, 00:00:00 GMT
	 */
	public StockKey(String symbol, Long timestamp) {
		this.symbol = symbol;
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return (new StringBuilder())
				.append('{')
				.append(symbol)
				.append(',')
				.append(timestamp)
				.append('}')
				.toString();
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		symbol = WritableUtils.readString(in);
		timestamp = in.readLong();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, symbol);
		out.writeLong(timestamp);
	}

	@Override
	public int compareTo(StockKey o) {
		int result = symbol.compareTo(o.symbol);
		if(0 == result) {
			result = timestamp.compareTo(o.timestamp);
		}
		return result;
	}

	/**
	 * Gets the symbol.
	 * @return Symbol.
	 */
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the timestamp.
	 * @return Timestamp. i.e. the number of milliseconds since January 1, 1970, 00:00:00 GMT
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
