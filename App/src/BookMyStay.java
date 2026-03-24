

    abstract class Room{
        protected  int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;
        int room;
        int total;

        public Room(int numberOfBeds,int squareFeet,double pricePerNight,int total){
            this.numberOfBeds=numberOfBeds;
            this.squareFeet=squareFeet;
            this.pricePerNight=pricePerNight;
            this.total=total-numberOfBeds;
            displayRoomDetails();
        }
        public void displayRoomDetails(){
            System.out.println("Beds: "+this.numberOfBeds);
            System.out.println("Size: "+this.squareFeet+" sqft");
            System.out.println("Price per Night: "+this.pricePerNight);
            System.out.println("Available: "+this.total);


        }
    }
    class singleRoom extends Room{
        public singleRoom(){
            System.out.println("Single Room:");
            super(1,250,1500.0,6);
        }
    }
    class doubleRoom extends Room{
        public doubleRoom(){
            System.out.println("Double Room:");
            super(2,400,2500.0,5);
        }
    }
    class suiteRoom extends Room{
        public suiteRoom(){
            System.out.println("Suite Room:");
            super(3,750,5000.0,5);
        }
    }

    public class BookMyStay {
        public static void main(String[] args){
            System.out.println("Hotel Room Initialization");
            singleRoom s=new singleRoom();
            doubleRoom d=new doubleRoom();
            suiteRoom e=new suiteRoom();
        }
    }


