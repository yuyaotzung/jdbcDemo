import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class mytest {

	public static void main(String args[]) {
		//durationCheckYear();
		
		//durationCheckMonth();
		
		durationCheckSeasion();
		
	}

	
	private static void durationCheckYear() {
		LocalDate before;
		try {
			before = LocalDate.parse("2021-05-30");
			
			try {
				LocalDate after = LocalDate.parse("2023-04-30"); 
				
				System.out.println(before+" vs "+after);
				
				if(before.isAfter(after)) {
					System.out.println("XX日不可超過YY日");
					return;
				}
				
			    Period period = Period.between(before, after);
			    int diffYear = period.getYears();
			    int diffMonths = period.getMonths();
			    int diffDays = period.getDays();
			    
			    System.out.println("=====================年繳======================");
			    diffYear = after.getYear() - before.getYear() ;
			    LocalDate afterMinusYears = after.minusYears(diffYear);
			    LocalDate beforePlusYears = before.plusYears(diffYear);
			    System.out.println(beforePlusYears+" vs "+ afterMinusYears);
			    if(after.isLeapYear() && after.getMonthValue() == 2 && !afterMinusYears.isEqual(before)) {
			    	// 2019-02-28 vs 2020-02-29
					System.out.println("不符合週期規則");
					return;	
			    }
			    if(before.isLeapYear() && !beforePlusYears.isEqual(after)) {
			    	// 2020-02-29 vs 2021-02-28
					System.out.println("不符合週期規則");
					return;			
			    }
			    if(!before.isEqual(afterMinusYears) && !after.isEqual(beforePlusYears)) {
					System.out.println("不符合週期規則");
					return;				    	
			    }
			} 
			catch (Exception e) {
				System.out.println("after is an Invalid date");
				return;
			}
			

		} 
		catch (Exception e) {
			System.out.println("before is an Invalid date");
			return;
		}
	}
	
	private static void durationCheckSeasion() {
		LocalDate before;
		try {
			before = LocalDate.parse("2019-08-30");// 2020-01-30(PASS) 2020-01-31(PASS)
			LocalDate lastDayofBefore = before.with(TemporalAdjusters.lastDayOfMonth());
			
			try {
				LocalDate after = LocalDate.parse("2020-02-29"); // 2021-02-28
				LocalDate lastDayofAfter = after.with(TemporalAdjusters.lastDayOfMonth());
				if(before.isAfter(after)) {
					System.out.println("XX日不可超過YY日");
					return;
				}
				
			    Period period = Period.between(before, after);
			    System.out.println(before+" vs "+after + "," + period );
			    int diffDays = period.getDays();
			    if(after.getMonthValue()==2 && after.isEqual(lastDayofAfter)) {
			    	int plusMonths = period.getMonths()>0 && diffDays==0 ? period.getMonths():period.getMonths()+1;
			    	if(plusMonths%3>0) {
						System.out.println("不符合週期規則");
						return;	
			    	}
			    	else {
				    	LocalDate beforePlusMonths = before.plusMonths(plusMonths);
				    	System.out.println(beforePlusMonths);
				    	if(beforePlusMonths.isEqual(after)) {
							System.out.println("PASS");
							return;	
				    	}
				    	else {
							System.out.println("不符合週期規則");
							return;				    		
				    	}
			    	}
			    }
			    else {
			    	int plusMonths = period.getMonths();
			    	if(after.isEqual(lastDayofAfter) && before.isEqual(lastDayofBefore) && diffDays > 0) {
			    		plusMonths++;
			    	}
			    	if(plusMonths%3>0) {
						System.out.println("不符合週期規則");
						return;	
			    	}		    	
			    }
			    
			    
				System.out.println("PASS");
				return;				    
//			    System.out.println(period.plusMonths(3L));
//			    System.out.println(period.withDays(15));
			    // 輸出：P2Y3M44D
			    //System.out.println(Period.of(2, 3, 44));
			} 
			catch (Exception e) {
				System.out.println("after is an Invalid date");
				return;
			}
		} 
		catch (Exception e) {
			System.out.println("before is an Invalid date");
			return;
		}
	}	
	
	private static void durationCheckMonth() {
		LocalDate before;
		try {
			before = LocalDate.parse("2019-12-28");// 2020-01-30(PASS) 2020-01-31(PASS)
			LocalDate lastDayofBefore = before.with(TemporalAdjusters.lastDayOfMonth());
			
			try {
				LocalDate after = LocalDate.parse("2020-02-28"); // 2021-02-28
				LocalDate lastDayofAfter = after.with(TemporalAdjusters.lastDayOfMonth());
				LocalDate tmp2 = after.minusMonths(1L);
				if(before.isAfter(after)) {
					System.out.println("XX日不可超過YY日");
					return;
				}
				
			    Period period = Period.between(before, after);
			    System.out.println(before+" vs "+after + "," + period );
			    int diffDays = period.getDays();
			    if(after.getMonthValue()==2 && after.isEqual(lastDayofAfter)) {
			    	int plusMonths = period.getMonths()>0 && diffDays==0 ? period.getMonths():period.getMonths()+1;
			    	LocalDate beforePlusMonths = before.plusMonths(plusMonths);
			    	System.out.println(beforePlusMonths);
			    	if(beforePlusMonths.isEqual(after)) {
						System.out.println("PASS");
						return;	
			    	}
			    	else {
						System.out.println("不符合週期規則");
						return;				    		
			    	}
			    }
			    else {
				    if(diffDays != 0 ) {
						if(after.isEqual(lastDayofAfter) && before.isEqual(lastDayofBefore)) {
							System.out.println("都是月底-PASS");
							return;	
						}
						else {
							System.out.println("不符合週期規則");
							return;							
						}
			    	
				    }			    	
			    }
			    
			    
				System.out.println("PASS");
				return;				    
//			    System.out.println(period.plusMonths(3L));
//			    System.out.println(period.withDays(15));
			    // 輸出：P2Y3M44D
			    //System.out.println(Period.of(2, 3, 44));
			} 
			catch (Exception e) {
				System.out.println("after is an Invalid date");
				return;
			}
		} 
		catch (Exception e) {
			System.out.println("before is an Invalid date");
			return;
		}
	}
	
	private static void extractedDes() {
		Map<String, Date> map = getDateMap();
	    for (Map.Entry<String, Date> tmp : map.entrySet()) {
	        System.out.println(tmp.getKey());
	        System.out.println(tmp.getValue());
	    }
	}
	
	public static Map<String, Date> getDateMap() {
	    Map<String, Date> map = new LinkedHashMap<>();
	    Calendar c = Calendar.getInstance();
	    for (int i = 0; i < 36; i++) {
	        map.put(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" , c.getTime());
	        c.add(Calendar.MONTH,-1);
	    }
	    return map;
	}
	
	private static void extracted() {
		List<User> users = new ArrayList<User>();
		User user1 = new User("1", "Mike", "SD","ESUN");
		User user2 = new User("2", "Benny","IT", "ESUN");
		User user3 = new User("3", "Rechar","SD", "KGI");
		User user4 = new User("4", "Mike","SD", "IBM");
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		List<User> filtered = users.stream() 
				  .filter(CommonUtils.distinctByKey(p -> String.format("%s%s%s", p.getUserName(),"_",p.getUserType())))
				  .collect(Collectors.toList());		
		filtered.forEach(f->System.out.println(f.getUserId()+"_"+f.getUserName()));

		boolean b = users.stream().anyMatch(u -> u.getUserType().equals(u.getUserGroup()));
		System.out.println(b);
		// p.getUserName()+"_"+p.getUserType()
		
		List<String> userIds = users.stream().map(User::getUserId).collect(Collectors.toList());
		userIds.stream().forEach(System.out::print);
	}
}
