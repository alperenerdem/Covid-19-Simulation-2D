import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Integer.max;

public class People extends JPanel implements ActionListener,Observer,Mediator  {

    /**
     * ArrayList for person objects
     */
    protected ArrayList<Person> personList=new ArrayList<>();


    /**
     * stopFlag will be one when user stopped simulation
     */
    private int stopFlag=0;
    private int infectedCount=0;
    private int deadCount=0;
    private int hospitalizedCount=0;
    private int healedCount=0;
    private JLabel infos;



    private Queue<Person> hospital = new LinkedList<>();
    /**
     * Infected people is waiting their turn for healing in hospitalQueue
     */
    private Queue<Person> hospitalQueue = new LinkedList<>();

    private final PersonType healthy=new Healthy();
    private final PersonType interacted=new Interacted();
    private final PersonType infected=new Infected();
    private final PersonType untouchable=new Untouchable();
    private final PersonType hospitalized=new Hospitalized();
    private final PersonType dead=new Dead();

    private double spreadingFactor=0.9;
    private double mortalityRate=0.7;
    private int maskRate=9;

    public PersonType getUntouchableType() {
        return untouchable;
    }
    public PersonType getHealthyType() {
        return healthy;
    }
    public PersonType getInteractedType() {
        return interacted;
    }
    public PersonType getInfectedType() {
        return infected;
    }
    public PersonType getHospitalizeType() {
        return hospitalized;
    }
    public PersonType getDeadType() {
        return dead;
    }


    private final Timer timer=new Timer(15,this);

    /**
     * @param infos includes information about counts of dead,alive,infected etc..
     */
    public People(JLabel infos){

        this.setBackground(new Color(146, 168, 124));
        this.infos=infos;
        this.setSize(1000,600);
        this.setLocation(100,0);

        timer.start();
        addSickPerson(1);
    }

    /**
     * @param i person count which we wanted to add canvas
     */
    public void addPerson(int i){
        int x=0;
        while(x<i){
            personList.add(new Person(this));
            x++;
        }



    }

    /**
     * @param i sick person count which we wanted to add canvas
     */
    public void addSickPerson(int i){
        int x=0;
        setInfectedCount(getInfectedCount() +i);
        while(x<i){
            Person temp=new Person(this);
            temp.setPersonType(getInfectedType());
            temp.setInfectionSec(System.currentTimeMillis());
            temp.setInfecting(true);
            temp.setInfected(true);
            temp.setColor(temp.getPersonType().getColor());
            getHospitalQueue().add(temp);
            personList.add(temp);
            x++;
        }


    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //this.setBackground(Color.blue);
        int x=0;

        int len=personList.size();
        while(x<len) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setColor(personList.get(x).getColor());
            g2D.fillRect(personList.get(x).getX(), personList.get(x).getY(), 5, 5);
            x++;
        }
    }

    /**
     * check if there is any collision between people
     */
    public void checkCollision(){
        int x=0,y;
        int len=personList.size()-1;
        while(x<len-1){
            y=x;
            if(personList.get(x).getPersonType().getType()!= Types.Interacted&& personList.get(x).getPersonType().getType()!= Types.Untouchable) {
                while (y < len) {
                    if(personList.get(y + 1).getPersonType().getType()!= Types.Interacted&& personList.get(x).getPersonType().getType()!= Types.Untouchable) {
                        if (checkDistance(personList.get(x), personList.get(y + 1))) {
                            collision(personList.get(x), personList.get(y + 1));
                        }
                    }
                    y = y + 1;
                }
            }
            x=x+1;
        }
    }

    /**
     * check current distance between person and person1
     * @param person
     * @param person1
     * @return if distance under social distance return true
     */
    public boolean checkDistance(Person person, Person person1) {
        int x= person.getX() - person1.getX();
        int y= person.getY() - person1.getY();
        int distance= (int) Math.sqrt((x*x) + (y*y));
        if(distance< person1.getSocialDistance() || distance < person.getSocialDistance()){
            return true;
        }
        return false;
    }

    /**
     *if there is a collision, make calculations and decide how long they will stay together
     * and one of them is infected and other not, decide healthy person will be infected or not.
     * @param person
     * @param person1
     */
    public void collision(Person person, Person person1) {

        long start = System.currentTimeMillis();
        person.setTempSec(start);
        person1.setTempSec(start);
        int x = person.getX() - person1.getX();
        int y = person.getY() - person1.getY();
        int distance = (int) Math.sqrt((x * x) + (y * y));
        int maxSec = max(person.getSocialSeconds(), person1.getSocialSeconds());
        person.setInteractSeconds(maxSec);
        person1.setInteractSeconds(maxSec);
        double chance = (double) ((int) (Math.random() * 100)) / 100;
        double mask1=0.2,mask2=0.2;
        if((int)(Math.random() * 10)> getMaskRate())
            mask1=1.0;
        if((int)(Math.random() * 10)> getMaskRate())
            mask2=1.0;

        if (person.getPersonType().getType() == Types.Infected && person1.getPersonType().getType() == Types.Healthy) {

            if (chance < (getSpreadingFactor() * (1 + maxSec / 10) * mask1 * mask2 * (1 - distance / 10))) {
                setInfectedCount(getInfectedCount() + 1);
                person1.setInfecting(true);
            }
        }
        else if (person1.getPersonType().getType() == Types.Infected && person.getPersonType().getType() == Types.Healthy) {

            if (chance < (getSpreadingFactor() * (1 + (maxSec / 10)) *mask1 * mask2 * (1 - (distance / 10)))) {
                setInfectedCount(getInfectedCount() +1);
                person.setInfecting(true);
            }
        }
            person.setPersonType(interacted);
            person1.setPersonType(interacted);

    }

    /**
     * Check hospitalQueue, if there is empty space in hospital take infected patients to Hospital
     * and heal them.
     */
    public void Hospital(){
        while(getHospitalQueue().size()>0 && getHospitalQueue().peek().getPersonType().getType()== Types.Dead){
            getHospitalQueue().remove();
        }
        long end=System.currentTimeMillis();
        if(getHospitalQueue().size()>0&& getHospitalizedCount() <(int)((getDeadCount() +personList.size()+ getHospitalizedCount())/100)){

            if(25<(end - getHospitalQueue().peek().getInfectionSec()) / 1000F) {
                Person temp = getHospitalQueue().remove();
                hospital.add(temp);
                temp.setHospitalSec(System.currentTimeMillis());
                temp.setPersonType(getHospitalizeType());
                setHospitalizedCount(getHospitalizedCount() + 1);
                personList.remove(temp);
            }
        }
        if(hospital.size()>0){
            float sec=(end - hospital.peek().getHospitalSec()) / 1000F;
            if(sec>10){
                Person temp=hospital.remove();
                temp.setPersonType(getHealthyType());
                temp.setColor(temp.getPersonType().getColor());
                setHospitalizedCount(getHospitalizedCount() -1);
                setInfectedCount(getInfectedCount() -1);
                setHealedCount(getHealedCount() +1);
                temp.setInfecting(false);
                temp.setInfected(false);
                personList.add(temp);

            }
        }
    }

    /**
     * update canvas
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {



        String info="<html>"+((int)personList.size()+ getDeadCount() + getHospitalizedCount()) +" Population"+"<br/>"+((int)personList.size()+ getHospitalizedCount()) +
                " Alive"+"<br/>"+(personList.size()+ getHospitalizedCount() - getInfectedCount())+
                " Healthy"+"<br/>"+getInfectCount()+" Infected"+"<br/>"+ getHospitalizedCount() +" Hospitalized"+"<br/>"
                + getHealedCount() +" Healed"+"<br/>"+ getDeadCount() +" Dead"+"</html>";

        infos.setText(info);
        int x=0;
        if(stopFlag==0) {
            checkCollision();
            Hospital();
            while (x < personList.size()) {

                personList.get(x).updateLocation();
                x++;
            }
            long end = System.currentTimeMillis();




        }

        repaint();
    }

    @Override
    public void restart() {
        setHealedCount(0);
        setInfectedCount(0);
        setDeadCount(0);
        setHospitalizedCount(0);
        personList.removeAll(personList);
        hospital.removeAll(hospital);
        getHospitalQueue().removeAll(getHospitalQueue());
        addSickPerson(1);
    }

    @Override
    public void stopStart() {
        if(stopFlag==1){
            stopFlag=0;
        }
        else{
            stopFlag=1;
        }
    }

    @Override
    public void upMortality() {
        if(getMortalityRate() <0.89)
            setMortalityRate(getMortalityRate() +0.1);
    }

    @Override
    public void downMortality() {
        if(getMortalityRate() >0.11)
            setMortalityRate(getMortalityRate() -(float)0.1);
    }

    @Override
    public void upSpread() {
        if(getSpreadingFactor() <1.0)
            setSpreadingFactor(getSpreadingFactor() +0.1);
    }

    @Override
    public void downSpread() {
        if(getSpreadingFactor() >0.51)
            setSpreadingFactor(getSpreadingFactor() -0.1);
    }

    @Override
    public void upMask() {
        if(getMaskRate() <10)
            setMaskRate(getMaskRate() +1);
    }

    @Override
    public void downMask() {
        if(getMaskRate() >0)
            setMaskRate(getMaskRate() -1);
    }

    public String getInfectCount() {
        return Integer.toString(getInfectedCount());
    }

    public int getInfectedCount() {
        return infectedCount;
    }

    public void setInfectedCount(int infectedCount) {
        this.infectedCount = infectedCount;
    }

    public int getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(int deadCount) {
        this.deadCount = deadCount;
    }

    public int getHospitalizedCount() {
        return hospitalizedCount;
    }

    public void setHospitalizedCount(int hospitalizedCount) {
        this.hospitalizedCount = hospitalizedCount;
    }

    public int getHealedCount() {
        return healedCount;
    }

    public void setHealedCount(int healedCount) {
        this.healedCount = healedCount;
    }

    public Queue<Person> getHospitalQueue() {
        return hospitalQueue;
    }

    public double getSpreadingFactor() {
        return spreadingFactor;
    }

    public void setSpreadingFactor(double spreadingFactor) {
        this.spreadingFactor = spreadingFactor;
    }

    public double getMortalityRate() {
        return mortalityRate;
    }

    public void setMortalityRate(double mortalityRate) {
        this.mortalityRate = mortalityRate;
    }

    public int getMaskRate() {
        return maskRate;
    }

    public void setMaskRate(int maskRate) {
        this.maskRate = maskRate;
    }
}
