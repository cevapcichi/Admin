package de.munich.iteratec.admin;


public class Question {

    private String question, questionType;
    private String answer1, answer2, answer3, answer4;
    private String imageDescription1, imageDescription2, imageDescription3, imageDescription4;
    private String rating1, rating2, rating3, rating4, rating5;
    private Long count1, count2, count3, count4, count5;


    public Question(){

    }

    public Question(String question, String questionType, String answer1, Long count1, String answer2, Long count2, String answer3, Long count3, String answer4, Long count4){
        this.question = question;
        this.questionType = questionType;
        this.answer1 = answer1;
        this.count1 = count1;
        this.answer2 = answer2;
        this.count2 = count2;
        this.answer3 = answer3;
        this.count3 = count3;
        this.answer4 = answer4;
        this.count4 = count4;
    }

    public Question(String question, String questionType, String rating1, Long count1, String rating2, Long count2, String rating3, Long count3, String rating4, Long count4, String rating5, Long count5){
        this.question = question;
        this.questionType = questionType;
        this.rating1 = rating1;
        this.count1 = count1;
        this.rating2 = rating2;
        this.count2 = count2;
        this.rating3 = rating3;
        this.count3 = count3;
        this.rating4 = rating4;
        this.count4 = count4;
        this.rating5 = rating5;
        this.count5 = count5;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getRating1() {
        return rating1;
    }

    public void setRating1(String rating1) {
        this.rating1 = rating1;
    }

    public String getRating2() {
        return rating2;
    }

    public void setRating2(String rating2) {
        this.rating2 = rating2;
    }

    public String getRating3() {
        return rating3;
    }

    public void setRating3(String rating3) {
        this.rating3 = rating3;
    }

    public String getRating4() {
        return rating4;
    }

    public void setRating4(String rating4) {
        this.rating4 = rating4;
    }

    public String getRating5() {
        return rating5;
    }

    public void setRating5(String rating5) {
        this.rating5 = rating5;
    }

    public String getImageDescription1() {
        return imageDescription1;
    }

    public void setImageDescription1(String imageDescription1) {
        this.imageDescription1 = imageDescription1;
    }

    public String getImageDescription2() {
        return imageDescription2;
    }

    public void setImageDescription2(String imageDescription2) {
        this.imageDescription2 = imageDescription2;
    }

    public String getImageDescription3() {
        return imageDescription3;
    }

    public void setImageDescription3(String imageDescription3) {
        this.imageDescription3 = imageDescription3;
    }

    public String getImageDescription4() {
        return imageDescription4;
    }

    public void setImageDescription4(String imageDescription4) {
        this.imageDescription4 = imageDescription4;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Long getCount1() {
        return count1;
    }

    public void setCount1(Long count1) {
        this.count1 = count1;
    }

    public Long getCount2() {
        return count2;
    }

    public void setCount2(Long count2) {
        this.count2 = count2;
    }

    public Long getCount3() {
        return count3;
    }

    public void setCount3(Long count3) {
        this.count3 = count3;
    }

    public Long getCount4() {
        return count4;
    }

    public void setCount4(Long count4) {
        this.count4 = count4;
    }

    public Long getCount5() {
        return count5;
    }

    public void setCount5(Long count5) {
        this.count5 = count5;
    }
}
