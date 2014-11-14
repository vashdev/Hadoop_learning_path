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

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.Arrays;

/**
 * Secondary sort mapper.
 * @author Jee Vang
 *
 */
public class SsMapper extends Mapper<LongWritable, Text, StockKey, DoubleWritable> {

	private static final Log _log = LogFactory.getLog(SsMapper.class);
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] tokens = value.toString().split(",");
		
                   System.out.println("Array contents "+ Arrays.toString(tokens));
		String symbol = tokens[0].trim();
System.out.println("Symbol "+symbol);
		Long timestamp = Long.parseLong(tokens[1].trim());
System.out.println("timestamp "+timestamp);

		Double v = Double.parseDouble(tokens[2].trim());
		
System.out.println("double "+v);

		StockKey stockKey = new StockKey(symbol, timestamp);
		DoubleWritable stockValue = new DoubleWritable(v);
		
		context.write(stockKey, stockValue);
		_log.debug(stockKey.toString() + " => " + stockValue.toString());
	}
}
