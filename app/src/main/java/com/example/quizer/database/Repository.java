package com.example.quizer.database;

import android.content.Context;
import android.util.Log;


import com.example.quizer.quizModel.Answer;
import com.example.quizer.quizModel.Question;
import com.example.quizer.quizModel.QuestionAnswer;
import com.example.quizer.quizModel.Quiz;
import com.example.quizer.userCabinet.User;

import java.io.File;
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
    private final UserDAO userDAO;


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
        userDAO = helper.getUserDAO();


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

        Question question1 = createQuestion("Capital of Russia", 0, quiz);
        Question question2 = createQuestion("Capital of USA", 1, quiz);

        Answer answer1 = createAnswer("Moscow");
        Answer answer2 = createAnswer("London");
        Answer answer3 = createAnswer("Sochi");
        Answer answer4 = createAnswer("Washington");
        Answer answer5 = createAnswer("Krasnodar");

        setAnswersForQuestion(question1, answer1, answer2, answer3, answer4);
        setAnswersForQuestion(question2, answer1, answer4, answer5, answer2);

        User user = new User("Pixel",0);
        //addUser(user);


    }

    public Quiz createQuiz(String title, int size, boolean isSolved, boolean isFree) throws SQLException {
        Quiz quiz = new Quiz(title, size, isSolved, isFree);
        quizDAO.create(quiz);
        return quiz;
    }

    public Question createQuestion(String questionText, int rightAnswer, Quiz quiz) throws SQLException {
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

    public void setAnswersForQuestion(Question question, Answer... answers) throws SQLException {

        for (Answer answer : answers) {
            QuestionAnswer questionAnswer = new QuestionAnswer(question, answer);
            questionAnswerDAO.create(questionAnswer);
        }
    }

    public void addUser(User user) throws SQLException {
        userDAO.create(user);

    }

    public User getUser() throws SQLException {
        return userDAO.getUser();
    }

    public File getPhotoFile(User user){
        String filename = user.getPhotoFileName();
        File fileDir = context.getFilesDir();
        return new File(fileDir,filename);

    }


}
