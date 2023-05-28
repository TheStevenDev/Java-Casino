public class Cavallo {
    private String name;
    private int stars;

    public Cavallo(String name, int stars){
        this.name=name;
        this.stars=stars;
    }

    public String getName(){
        return this.name;
    }

    public int getStars(){
        return this.stars;
    }

    public void setName(String newName){
        this.name=newName;
    }

    public void setStars(int newStars){
        this.stars=newStars;
    }




}
