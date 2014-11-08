package demo;
 import java.io.*;
import java.text.*;
import java.util.Date;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.io.WritableComparable;

public class PaymentKey implements WritableComparable<PaymentKey> {

	private Integer ln;
	private String pd;
	
	/**
	 * Constructor.
	 */
	public PaymentKey() { }
	
	 
	public PaymentKey(Integer lnid, String payd) {
		this.ln = lnid;
		this.pd = payd;
	}
	
	@Override
	public String toString() {
		return Integer.toString(ln)+"=>"+pd;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		
		ln =  in.readInt();
		pd=  WritableUtils.readString(in);
		 
	}

	@Override
	public void write(DataOutput out) throws IOException {
	 
		out.writeInt(ln);
		WritableUtils.writeString(out,pd);
	}

	@Override
	public int compareTo(PaymentKey o) {
		int result =   ln.compareTo(o.ln);
		if(0 == result) {
			try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			 
			Date date1 = sdf.parse(this.pd);
        	Date date2 = sdf.parse(o.pd);
        	 
			result =  date1.compareTo(date2);
			}catch(ParseException ex){
	    		ex.printStackTrace();
	    	}
		}
		return result;
	}

	public Integer getln() {
		return ln;
	}

	public void setln(int ln) {
		this.ln = ln;
	}

	public String getpd() {
		return pd;
	}

	public void setpd(String pd) {
		this.pd = pd;
	}

	
}
