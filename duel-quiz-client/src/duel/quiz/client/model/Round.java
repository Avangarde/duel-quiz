package duel.quiz.client.model;

/**
 *
 * @author Edward
 */
public class Round {
    
    private int roundId;
    private boolean p1Hasplayed;
    private boolean p2Hasplayed;
    private String categoryName;

    public Round(int roundId) {
        this.roundId = roundId;
    }
    
    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public boolean isP1Hasplayed() {
        return p1Hasplayed;
    }

    public void setP1Hasplayed(boolean p1Hasplayed) {
        this.p1Hasplayed = p1Hasplayed;
    }

    public boolean isP2Hasplayed() {
        return p2Hasplayed;
    }

    public void setP2Hasplayed(boolean p2Hasplayed) {
        this.p2Hasplayed = p2Hasplayed;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    
    
}
