package Test;

public class Test1 {
	
	
	 static int getNumber(int lnNumber) {
		try {
		return 100/lnNumber;
		}
		finally {
			return 100;
		}
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(getNumber(10));
		
	}

}
