package relax.sn.com.relax4.entity;

/**
 * Created by John on 2018/5/16.
 */
public class QuestionInfo {
    private int id;
    private String question;
    private String answera;
    private String answerb;
    private String answerc;
    private String answerd;
    private String explaination;

    private int selectedAnswer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswera() {
        return answera;
    }

    public void setAnswera(String answera) {
        this.answera = answera;
    }

    public String getAnswerb() {
        return answerb;
    }

    public void setAnswerb(String answerb) {
        this.answerb = answerb;
    }

    public String getAnswerc() {
        return answerc;
    }

    public void setAnswerc(String answerc) {
        this.answerc = answerc;
    }

    public String getAnswerd() {
        return answerd;
    }

    public void setAnswerd(String answerd) {
        this.answerd = answerd;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    @Override
    public String toString() {
        return "QuestionInfo [id=" + id + ", question=" + question
                + ", answera=" + answera + ", answerb=" + answerb
                + ", answerc=" + answerc + ", answerd=" + answerd
                + ", explaination=" + explaination + ", selectedAnswer="
                + selectedAnswer + "]";
    }
}
