package com.example.quizer.database;

import android.content.Context;
import android.util.Log;


import com.example.quizer.model.Answer;
import com.example.quizer.model.Question;
import com.example.quizer.model.QuestionAnswer;
import com.example.quizer.model.Quiz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository repository;
    private Context context;
    private final QuizDAO quizDAO;
    private final QuestionDAO questionDAO;
    private final AnswerDAO answerDAO;
    private final QuestionAnswerDAO questionAnswerDAO;


    public static Repository getInstance(Context context) {
        if (repository == null) {
            try {
                repository = new Repository(context);
            } catch (SQLException e) {
                Log.e("MyERROR", "getInstance: " + e.getMessage());
            }
        }
        return repository;
    }

    private Repository(Context context) throws SQLException {

        this.context = context.getApplicationContext();

        //getting all DAOs
        DatabaseHelper helper = new DatabaseHelper(context);
        quizDAO = helper.getQuizDAO();
        questionDAO = helper.getQuestionDAO();
        answerDAO = helper.getAnswerDAO();
        questionAnswerDAO = helper.getQuestionAnswerDAO();


        if (quizDAO.getQuizList().size() == 0) {
            initDatabase();
        }
    }

    public List<Quiz> getQuizList() {
        try {
            return quizDAO.getQuizList();
        } catch (SQLException e) {
            Log.e("DATABASE ERROR", "getQuizList: ERROR");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Quiz getQuiz(String title) {
        try {
            return quizDAO.getQuizByTitle(title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Question> getQuestionsByQuiz(Quiz quiz) {
        try {
            return questionDAO.getQuestionsByQuiz(quiz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Answer> getAnswerByQuestion(Question question) {
        List<Answer> answers = new ArrayList<>();
        try {
            List<QuestionAnswer> questionAnswerList = questionAnswerDAO.getQuestionAnswerByQuestion(question);
            for (QuestionAnswer questionAnswer : questionAnswerList) {
                answers.add(questionAnswer.getAnswer());
            }
            return answers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void updateQuiz(Quiz quiz) {
        try {
            quizDAO.update(quiz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initDatabase() throws SQLException {
        //Filling database with some data
       Quiz quiz = createQuiz("First Quiz", 5, false, false);
       // Quiz quiz = new Quiz("First Quiz", 5, false, false);


        Question question1 =  createQuestion("Capital of Russia", 0, quiz);
        Question question2 =  createQuestion("Capital of USA", 1, quiz);
       // quizDAO.create(quiz);
        //questionDAO.create(question1);
        //questionDAO.create(question2);
        //quiz.addQuestion(question1);
       // quiz.addQuestion(question2);
       // quizDAO.update(quiz);

        Answer answer1 = createAnswer("Moscow");
        Answer answer2 = createAnswer("London");
        Answer answer3 = createAnswer("Sochi");
        Answer answer4 = createAnswer("Washington");
        Answer answer5 = createAnswer("Krasnodar");

        /*answerDAO.create(answer1);
        answerDAO.create(answer2);
        answerDAO.create(answer3);
        answerDAO.create(answer4);
        answerDAO.create(answer5);*/

        setAnswersForQuestion(question1,answer1,answer2,answer3,answer4);
        setAnswersForQuestion(question2,answer1,answer4,answer5,answer2);

        /*QuestionAnswer question1Answer1 = new QuestionAnswer(question1, answer1);
        QuestionAnswer question1Answer2 = new QuestionAnswer(question1, answer2);
        QuestionAnswer question1Answer3 = new QuestionAnswer(question1, answer3);
        QuestionAnswer question1Answer4 = new QuestionAnswer(question1, answer4);
        questionAnswerDAO.create(question1Answer1);
        questionAnswerDAO.create(question1Answer2);
        questionAnswerDAO.create(question1Answer3);
        questionAnswerDAO.create(question1Answer4);

        QuestionAnswer question2Answer1 = new QuestionAnswer(question2, answer1);
        QuestionAnswer question2Answer2 = new QuestionAnswer(question2, answer4);
        QuestionAnswer question2Answer3 = new QuestionAnswer(question2, answer5);
        QuestionAnswer question2Answer4 = new QuestionAnswer(question2, answer3);
        questionAnswerDAO.create(question2Answer1);
        questionAnswerDAO.create(question2Answer2);
        questionAnswerDAO.create(question2Answer3);
        questionAnswerDAO.create(question2Answer4);*/
    }

    public Quiz createQuiz(String title,int size,boolean isSolved, boolean isFree) throws SQLException {
        Quiz quiz = new Quiz(title, size, isSolved,isFree);
        quizDAO.create(quiz);
        return quiz;
    }
    public Question createQuestion(String questionText,int rightAnswer,Quiz quiz) throws SQLException {
        Question question = new Question(questionText, rightAnswer, quiz);
        questionDAO.create(question);
        quiz.addQuestion(question);
        quizDAO.update(quiz);
        return question;
    }

    public Answer createAnswer(String answerText) throws SQLException {
        Answer answer = new Answer(answerText);
        answerDAO.create(answer);
        return answer;
    }
    public void setAnswersForQuestion(Question question,Answer... answers) throws SQLException {

        for(Answer answer: answers){
            QuestionAnswer questionAnswer = new QuestionAnswer(question,answer);
            questionAnswerDAO.create(questionAnswer);
        }
    }



}
