import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

class Taxi{
    int time;
    int no;
    int earning;
    ArrayList<Character> from =  new ArrayList<>();
    ArrayList<Character> To = new ArrayList<>();
    ArrayList<Integer> PickTime = new ArrayList<>();
    ArrayList<Integer> dropTime = new ArrayList<>();
    ArrayList<Integer> earningPerBooking = new ArrayList<>();
    ArrayList<Integer> BookingID = new ArrayList<>();
    ArrayList<Integer> CustomerID = new ArrayList<>();
    public Taxi(int x,int y){
        no = x;
        time = y;
    }
}
class Booking{
    public void createBooking(int custID,int BookingID,Character pickPoint,Character destPoint,int PickTime,HashMap<Character,ArrayList<Taxi>> Station,Taxi t) {
        System.out.println("Taxi can be allotted");
        t.BookingID.add(BookingID);
        t.CustomerID.add(custID);
        t.time = PickTime+(Math.abs((int)pickPoint-(int)destPoint));
        t.PickTime.add(PickTime);
        t.dropTime.add(t.time);
        t.from.add(pickPoint);
        t.To.add(destPoint);
        t.earning = 200+((Math.abs((int)pickPoint-(int)destPoint)-1)*150);
        t.earningPerBooking.add(200+((Math.abs((int)pickPoint-(int)destPoint)-1)*150));
        System.out.println("Taxi - "+t.no+" is allotted");
        Station.get(destPoint).add(t);
        System.out.println(t.time);
    }
    public void checkTaxi(int custID,int BookingID,Character pickPoint,Character destPoint,int PickTime,HashMap<Character,ArrayList<Taxi>> Station){
        for(int i=0;i<3;i++){
            if(i==0){
                ArrayList<Taxi> cur = Station.get(pickPoint);
                for(int j=0;j<cur.size();j++){
                    Taxi t = cur.get(j);
                    if(t.time<=PickTime){
                        createBooking(custID,BookingID,pickPoint,destPoint,PickTime,Station,t);
                        cur.remove(j);
                        return;
                    }
                }
            }
            else{
                ArrayList<Taxi> cur = Station.get((char)(pickPoint+i));
                for(int j=0;j<cur.size();j++){
                    Taxi t = cur.get(j);
                    if(t.time<pickPoint){
                        createBooking(custID,BookingID,pickPoint,destPoint,PickTime,Station,t);
                        cur.remove(j);
                        return;
                    }
                }
                cur = Station.get((char)(pickPoint-i));
                for(int j=0;j<cur.size();j++){
                    Taxi t = cur.get(j);
                    if(t.time<pickPoint){
                        createBooking(custID,BookingID,pickPoint,destPoint,PickTime,Station,t);
                        cur.remove(j);
                        return;
                    }
                }
            }
        }
    }
}
public class TaxiBooking {
    public static void main(String[] args) throws IOException {
        BufferedReader br  =  new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Provide no. of Taxi");
        int noOfTaxi = Integer.parseInt(br.readLine());
        HashMap<Character, ArrayList<Taxi>> Station  = new HashMap<>();
        for(int  i=0;i<6;i++){
            Station.put((char)('A'+i),new ArrayList<>());
        }
        for(int i=0;i<noOfTaxi;i++){
            Station.get('A').add(new Taxi(i+1,0));
        }
        int  noOfBooking = 0;
        while(noOfBooking<Integer.MAX_VALUE) {
            System.out.println("1 for booking and 2 for taxi details");
            int in = Integer.parseInt(br.readLine());
            if (in == 1) {
                noOfBooking++;
                System.out.println("Customer ID:");
                int CustID = Integer.parseInt(br.readLine());
                int BookID = noOfBooking;
                System.out.print("Pickup Point: ");
                Character pickPoint = br.readLine().charAt(0);
                System.out.print("Destination Point: ");
                Character destPoint = br.readLine().charAt(0);
                System.out.println("Pickup Time: ");
                int pickTime = Integer.parseInt(br.readLine());
                Booking b = new Booking();
                b.checkTaxi(CustID,BookID,pickPoint,destPoint,pickTime,Station);
            }else{
                for(Character  c : Station.keySet()){
                    ArrayList<Taxi> cur = Station.get(c);
                    for(Taxi t : cur){
                        System.out.println("Taxi - "+t.no+" Total Earning : "+t.earning);
                        for(int i=0;i<t.PickTime.size();i++) {
                            System.out.println(t.BookingID.get(i)+" "+t.CustomerID.get(i)+" "+t.from.get(i)+" "+t.To.get(i)+" "+
                                    t.PickTime.get(i)+" "+t.dropTime.get(i)+" "+t.earningPerBooking.get(i));
                        }
                    }
                }
            }

        }
    }
}
