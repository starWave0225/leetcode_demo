import java.util.TreeMap;

public class MyCalendarTwo {
    TreeMap<Integer, Integer> calendar = new TreeMap<>();

    public MyCalendarTwo() {
        
    }
    
    public boolean book(int start, int end) {
        calendar.put(start, calendar.getOrDefault(start,0)+1);
        calendar.put(end, calendar.getOrDefault(end,0)-1);
        int count=0;
        for(int temp : calendar.keySet()){
            count += calendar.get(temp);
            if(count == 3){
                calendar.put(start, calendar.get(start)-1);
                calendar.put(end, calendar.get(end)+1);
                return false;
            }
        }
        return true;
    }
}