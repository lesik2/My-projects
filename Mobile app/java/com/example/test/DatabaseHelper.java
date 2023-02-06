package com.example.test;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import Utils.Util;
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME_USER + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.KEY_ROLE + " INTEGER, "
                + Util.KEY_LOGIN + " TEXT UNIQUE, "
                + Util.KEY_PASSWORD + " TEXT, "
                + Util.KEY_NAME + " TEXT, "
                + Util.KEY_SURNAME + " TEXT" + " )";

        String CREATE_TEST_TABLE = "CREATE TABLE " + Util.TABLE_NAME_TEST + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.KEY_NUMBER_QUESTIONS + " TEXT, "
                + Util.KEY_TIMER + " TEXT, "
                + Util.KEY_NAME + " TEXT" + " )";

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + Util.TABLE_NAME_QUESTIONS + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.KEY_OPTION1 + " TEXT UNIQUE, "
                + Util.KEY_OPTION2 + " TEXT UNIQUE, "
                + Util.KEY_OPTION3 + " TEXT UNIQUE, "
                + Util.KEY_OPTION4 + " TEXT UNIQUE, "
                + Util.KEY_QUESTION + " TEXT, "
                + Util.KEY_USER_SELECTED_ANSWER + " TEXT, "
                + Util.KEY_ID_QUESTION_TEST + " INTEGER, "
                + Util.KEY_ANSWER + " TEXT" + " )";

        String CREATE_RESULTS_TABLE = "CREATE TABLE " + Util.TABLE_NAME_RESULTS + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.KEY_USER_ID + " INTEGER, "
                + Util.KEY_NUMBER_TEST + " INTEGER, "
                + Util.KEY_RESULTS + " TEXT" + " )";

//выполнение запрсов к бд
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TEST_TABLE);
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_RESULTS_TABLE);

        db.execSQL("INSERT INTO " + Util.TABLE_NAME_USER + " (" + Util.KEY_ROLE+ ", " + Util.KEY_LOGIN
                + ", " + Util.KEY_PASSWORD + ", " + Util.KEY_NAME + ", "+Util.KEY_SURNAME+ ") VALUES (1,'admin','admin','Алексей','Пухальский');");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME_TEST);
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME_QUESTIONS);
        onCreate(db);
    }
//добавление пользователя
    public void addUser(User user) {
        //открываем бд для записи
        SQLiteDatabase db = this.getWritableDatabase();
        //создаем словарь при помощи метода put
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_ROLE, user.getRole());
        contentValues.put(Util.KEY_LOGIN, user.getLogin());
        contentValues.put(Util.KEY_PASSWORD, user.getPassword());
        contentValues.put(Util.KEY_NAME, user.getName());
        contentValues.put(Util.KEY_SURNAME, user.getSurname());
        //добавление записи в бд
        db.insert(Util.TABLE_NAME_USER, null, contentValues);
        db.close();

    }
    //обновление даннных пользователя
    public int updateUser(User user,int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.KEY_LOGIN, user.getLogin());
        contentValues.put(Util.KEY_PASSWORD, user.getPassword());
        contentValues.put(Util.KEY_NAME, user.getName());
        contentValues.put(Util.KEY_SURNAME, user.getSurname());
        //обновление записи пользователя в бд
      return  db.update(Util.TABLE_NAME_USER,contentValues,Util.KEY_ID + "=?",new String[]{String.valueOf(id)});
    }
    // получение пользователя из бд
    public User getUser(int id) {
        //открываем бд для чтения
        SQLiteDatabase db = this.getReadableDatabase();
        //получение данных из бд
        Cursor cursor = db.query(Util.TABLE_NAME_USER, new String[]{Util.KEY_ID,Util.KEY_ROLE, Util.KEY_LOGIN, Util.KEY_PASSWORD, Util.KEY_NAME, Util.KEY_SURNAME},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        //переещение к первому объекту
        cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5)
        );
        db.close();
        return user;
    }
    //получение пользователей из бд
    public List<User> getUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> users=new ArrayList<>();
        String  selectAllUsers="Select * from "+Util.TABLE_NAME_USER;
        Cursor cursor=db.rawQuery(selectAllUsers,null);
        if(cursor.moveToFirst())
        {
            do {
                User user=new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setRole(Integer.parseInt(cursor.getString(1)));
                user.setLogin(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setName(cursor.getString(4));
                user.setSurname(cursor.getString(5));

                users.add(user);
            }while(cursor.moveToNext());
        }
        db.close();
        return users;

    }
     //количество тестов в бд
    public int getNotes()
    {
        int number;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAllTests = "Select * from " + Util.TABLE_NAME_TEST;
        Cursor cursor = db.rawQuery(selectAllTests, null);
        if(cursor.moveToFirst())
        {
            //количество тестов в бд
             number=cursor.getCount();
        }
        else
        {
            return 0;
        }

        db.close();
        return number;

    }
    //запись теста в бд
    public void createTest(Test test) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_ID, test.getId());
        contentValues.put(Util.KEY_NUMBER_QUESTIONS, test.getSize());
        contentValues.put(Util.KEY_TIMER, test.getTimer());
        contentValues.put(Util.KEY_NAME, test.getName());
        db.insert(Util.TABLE_NAME_TEST, null, contentValues);

        for (int i = 0; i < test.getQuestionsList().size(); i++) {
            ContentValues contentValues1 = new ContentValues();
            contentValues1.put(Util.KEY_OPTION1, test.getQuestionsList().get(i).getOption1());
            contentValues1.put(Util.KEY_OPTION2, test.getQuestionsList().get(i).getOption2());
            contentValues1.put(Util.KEY_OPTION3, test.getQuestionsList().get(i).getOption3());
            contentValues1.put(Util.KEY_OPTION4, test.getQuestionsList().get(i).getOption4());
            contentValues1.put(Util.KEY_QUESTION, test.getQuestionsList().get(i).getQuestion());
            contentValues1.put(Util.KEY_USER_SELECTED_ANSWER, test.getQuestionsList().get(i).getUserSelectedAnswer());
            contentValues1.put(Util.KEY_ID_QUESTION_TEST, test.getId());
            contentValues1.put(Util.KEY_ANSWER, test.getQuestionsList().get(i).getAnswer());
            db.insert(Util.TABLE_NAME_QUESTIONS, null, contentValues1);
        }
        db.close();
    }
    //запись результатов
    public void setResult(int userId, String results, int testId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_USER_ID, userId);
        contentValues.put(Util.KEY_NUMBER_TEST, testId);
        contentValues.put(Util.KEY_RESULTS, results);
        db.insert(Util.TABLE_NAME_RESULTS, null, contentValues);
        db.close();
    }
    public List<Results> getUsersResults()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Results> results = new ArrayList<>();
        //получение всех данных из таблицы с результатами
        String selectAllResults = "Select * from " + Util.TABLE_NAME_RESULTS;
        Cursor cursor = db.rawQuery(selectAllResults, null);

        while(cursor.moveToNext()){
            Results result = new Results();

            int id_test= Integer.parseInt(cursor.getString(2));
            int id_user=Integer.parseInt(cursor.getString(1));
            result.setResult(cursor.getString(3));
            result.setAttempt(Integer.parseInt(cursor.getString(0)));
            //получение названия теста
            result.setTestName(getTestName(id_test));
            //получения ФИО пользоваиеля
            User user=getUser(id_user);
            result.setUserName(user.getName()+user.getSurname());

            results.add(result);
        }

        db.close();
        return results;
    }
    public List<Results> getUserResults(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Results> results = new ArrayList<>();
        //получение рузультатов пользователя по id
        String query = String.format("SELECT * FROM %s WHERE %s=?", Util.TABLE_NAME_RESULTS, Util.KEY_USER_ID);
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        while(cursor.moveToNext()){
            Results result = new Results();
            int id_test= Integer.parseInt(cursor.getString(2));
            result.setResult(cursor.getString(3));
            result.setAttempt(Integer.parseInt(cursor.getString(0)));
            //получение название теста
            String query2 = String.format("SELECT * FROM %s WHERE %s=?", Util.TABLE_NAME_TEST, Util.KEY_ID);
            Cursor cursor2 = db.rawQuery(query2, new String[]{String.valueOf(id_test)});
            if(cursor2.moveToFirst())
            {
                result.setTestName(cursor2.getString(3));
            }

            results.add(result);
        }

        db.close();
        return results;
    }
    //получение названий всех тестов
    public List<Test> getAllTestsNamesId() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Test> tests = new ArrayList<>();
        String selectAllTests = "Select * from " + Util.TABLE_NAME_TEST;
        Cursor cursor = db.rawQuery(selectAllTests, null);

            while(cursor.moveToNext()){
                Test test = new Test();
                test.setId(Integer.parseInt(cursor.getString(0)));
                test.setName(cursor.getString(3));
                tests.add(test);
            }

        db.close();
        return tests;
    }

    public String getTestName(int id_test)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query2 = String.format("SELECT * FROM %s WHERE %s=?", Util.TABLE_NAME_TEST, Util.KEY_ID);
        Cursor cursor2 = db.rawQuery(query2, new String[]{String.valueOf(id_test)});
        cursor2.moveToFirst();
        db.close();
        return cursor2.getString(3);
    }
    public Test getTest(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Test test = new Test();
        List<QuestionList> questionsList = new ArrayList<>();
        //получение данных теста по id
        String query = String.format("SELECT * FROM %s WHERE %s=?", Util.TABLE_NAME_TEST, Util.KEY_ID);
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {

            test.setId(Integer.parseInt(cursor.getString(0)));
            test.setSize(Integer.parseInt(cursor.getString(1)));
            test.setTimer(cursor.getString(2));
            test.setName(cursor.getString(3));
            //получение данных по каждому вопросу по id
            String query2 = String.format("SELECT * FROM %s WHERE %s=?", Util.TABLE_NAME_QUESTIONS, Util.KEY_ID_QUESTION_TEST);
            Cursor cursor2 = db.rawQuery(query2, new String[]{String.valueOf(id)});
            if(cursor2.moveToFirst())
            {
               do{
                    String option1 = cursor2.getString(1);
                    String option2 = cursor2.getString((cursor2.getColumnIndexOrThrow(Util.KEY_OPTION2)));
                    String option3 = cursor2.getString((cursor2.getColumnIndexOrThrow(Util.KEY_OPTION3)));
                    String option4 = cursor2.getString((cursor2.getColumnIndexOrThrow(Util.KEY_OPTION4)));
                    String question = cursor2.getString((cursor2.getColumnIndexOrThrow(Util.KEY_QUESTION)));
                    String userSelectedAnswer = cursor2.getString((cursor2.getColumnIndexOrThrow(Util.KEY_USER_SELECTED_ANSWER)));
                   // int id_test = Integer.parseInt(cursor2.getString(cursor2.getColumnIndexOrThrow(Util.KEY_ID_QUESTION_TEST)));
                    String answer = cursor2.getString((cursor2.getColumnIndexOrThrow(Util.KEY_ANSWER)));
                    //if(id_test==id)

                        questionsList.add(new QuestionList(question, option1, option2, option3, option4, answer, userSelectedAnswer));


                } while(cursor2.moveToNext());

            }
            test.setQuestionsList(questionsList);
        }
        db.close();
        return test;
    }
    //удаление теста
    public void deleteTest(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME_TEST,Util.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.delete(Util.TABLE_NAME_QUESTIONS,Util.KEY_ID_QUESTION_TEST+"=?",new String[]{String.valueOf(id)});
        db.delete(Util.TABLE_NAME_RESULTS,Util.KEY_NUMBER_TEST+"=?",new String[]{String.valueOf(id)});

    }
}

