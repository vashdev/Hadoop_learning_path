package myudf;
public class TestDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConvertDate  cd = new ConvertDate ("Wed May 21 00:00:00 EDT 2008,91002");
	 String s = cd.FrmtDt();
		System.out.println(s);

	}

}
