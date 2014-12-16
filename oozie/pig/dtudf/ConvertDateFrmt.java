package dtudf;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.util.WrappedIOException;
import java.util.*;
import java.io.IOException;
public class ConvertDateFrmt extends EvalFunc<String> {

public String exec(Tuple input) throws IOException {
String formatedDate="";

        if (input == null || input.size() == 0)
            return null;
        try{
            String str = (String)input.get(0);

	DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = (Date)formatter.parse(str);
                                 Calendar cal = Calendar.getInstance();
                                 cal.setTime(date);
                                 formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +         cal.get(Calendar.YEAR);

        }catch(Exception e){
            throw WrappedIOException.wrap("Caught exception processing input row ", e);
        }
		return formatedDate ;
	}
}
	
