import java.awt.*;


public class Person {
    People people;
    private PersonType personType;
    private Color color=null;

    private boolean infecting=false;
    private boolean infected=false;

    private int x = 0;
    private int y = 0;
    private int xVector ;
    private int yVector ;

    private long tempSec;
    private long infectionSec;
    private long hospitalSec;

    private int interactSeconds;
    private int socialDistance;
    private int socialSeconds;



    public Person(People people) {
        this.people = people;
        if(people.personList.size()==0){
            setPersonType(people.getInfectedType());
            setInfectionSec(System.currentTimeMillis());
            setInfecting(true);
        }
        else
            setPersonType(people.getHealthyType());
        setColor(getPersonType().getColor());

        this.xVector=(int)(Math.random() * 5);
        this.yVector=(int)(Math.sqrt(5*5-this.xVector*this.xVector));
        if(Math.random() * 10>5){
            this.xVector=-this.xVector;
        }
        if(Math.random() * 10>5){
            this.yVector=-this.yVector;
        }

        setSocialSeconds(1+(int)(Math.random() * 4));
        setSocialDistance(5+(int)(Math.random() * 5));
        setX((int)(Math.random() * 1000));
        setY((int)(Math.random() * 600));
    }



    public void changeVector(){
        if(this.getX() >1000){
            this.xVector=-1*(int)(Math.random() * 5);

            if(this.yVector<0)
                this.yVector=-1*(int)(Math.sqrt(5*5-this.xVector*this.xVector));
            else
                this.yVector=(int)(Math.sqrt(5*5-this.xVector*this.xVector));
        }
        if(this.getX() <0){
            this.xVector=(int)(Math.random() * 5);

            if(this.yVector<0)
                this.yVector=-1*(int)(Math.sqrt(5*5-this.xVector*this.xVector));
            else
                this.yVector=(int)(Math.sqrt(5*5-this.xVector*this.xVector));
        }
        if(this.getY() >600){
            this.yVector=-1*(int)(Math.random() * 5);

            if(this.xVector<0)
                this.xVector=-1*(int)(Math.sqrt(5*5-this.yVector*this.yVector));
            else
                this.xVector=(int)(Math.sqrt(5*5-this.yVector*this.yVector));
        }
        if(this.getY() <0){
            this.yVector=(int)(Math.random() * 5);

            if(this.xVector<0)
                this.xVector=-1*(int)(Math.sqrt(5*5-this.yVector*this.yVector));
            else
                this.xVector=(int)(Math.sqrt(5*5-this.yVector*this.yVector));
        }
    }

    public void updateLocation() {

        if(getPersonType().getType()!= Types.Interacted) {

            this.setX(this.getX() + this.xVector);
            this.setY(this.getY() + this.yVector);
            if (this.getX() >= 1000 || this.getX() <= 0 || this.getY() >= 599 || this.getY() <= 0) {
                this.changeVector();
            }
        }
        else{
            long end = System.currentTimeMillis();
            float sec=(end - getTempSec()) / 1000F;

            if(sec> getInteractSeconds()){
                setTempSec(end);

                setPersonType(people.getUntouchableType());
            }
        }

        if(getPersonType().getType()== Types.Untouchable){
            long end = System.currentTimeMillis();
            float sec=(end - getTempSec()) / 1000F;
            if(sec>0.5){
                if(!isInfecting()){
                    setPersonType(people.getHealthyType());
                }
                else{
                    if(!isInfected())
                        setInfectionSec(System.currentTimeMillis());
                    setPersonType(people.getInfectedType());
                    if(!isInfected())
                        people.getHospitalQueue().add(this);
                    setInfected(true);

                }
                setColor(getPersonType().getColor());

            }
        }
        else if(getPersonType().getType()== Types.Infected){


            long end = System.currentTimeMillis();

            float sec=(end - getInfectionSec()) / 1000F;

            if(sec>(100*(1- people.getMortalityRate()))){

                setPersonType(people.getDeadType());
                people.personList.remove(this);
                people.getHospitalQueue().remove(this);
                people.setInfectedCount(people.getInfectedCount() -1);
                people.setDeadCount(people.getDeadCount() +1);

            }

        }

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getTempSec() {
        return tempSec;
    }

    public void setTempSec(long tempSec) {
        this.tempSec = tempSec;
    }

    public long getInfectionSec() {
        return infectionSec;
    }

    public void setInfectionSec(long infectionSec) {
        this.infectionSec = infectionSec;
    }

    public long getHospitalSec() {
        return hospitalSec;
    }

    public void setHospitalSec(long hospitalSec) {
        this.hospitalSec = hospitalSec;
    }

    public int getInteractSeconds() {
        return interactSeconds;
    }

    public void setInteractSeconds(int interactSeconds) {
        this.interactSeconds = interactSeconds;
    }

    public int getSocialDistance() {
        return socialDistance;
    }

    public void setSocialDistance(int socialDistance) {
        this.socialDistance = socialDistance;
    }

    public int getSocialSeconds() {
        return socialSeconds;
    }

    public void setSocialSeconds(int socialSeconds) {
        this.socialSeconds = socialSeconds;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isInfecting() {
        return infecting;
    }

    public void setInfecting(boolean infecting) {
        this.infecting = infecting;
    }

    public boolean isInfected() {
        return infected;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }
}

