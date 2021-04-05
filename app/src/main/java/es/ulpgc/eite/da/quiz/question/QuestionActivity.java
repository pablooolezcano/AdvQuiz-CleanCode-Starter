package es.ulpgc.eite.da.quiz.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.ulpgc.eite.da.quiz.R;
import es.ulpgc.eite.da.quiz.cheat.CheatActivity;

public class QuestionActivity
    extends AppCompatActivity implements QuestionContract.View {

  public static String TAG = QuestionActivity.class.getSimpleName();

  QuestionContract.Presenter presenter;
  TextView replyText, question;
  Button option1, option2, option3, cheatButton, nextButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    getSupportActionBar().setTitle(R.string.question_title);

    nextButton = findViewById(R.id.nextButton);
    nextButton.setText(R.string.next_button);
    cheatButton = findViewById(R.id.cheatButton);
    cheatButton.setText(R.string.cheat_button);
    question = findViewById(R.id.questionTextView);
    replyText = findViewById(R.id.answerTextView);
    option1 = findViewById(R.id.option1Button);
    option2 = findViewById(R.id.option2Button);
    option3 = findViewById(R.id.option3Button);

    cheatButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        presenter.onCheatButtonClicked();
      }
    });
    nextButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        presenter.onNextButtonClicked();
      }
    });
    option1.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        presenter.onOptionButtonClicked(1);
      }
    });
    option2.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        presenter.onOptionButtonClicked(2);
      }
    });
    option3.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        presenter.onOptionButtonClicked(3);
      }
    });

        /*
    if(savedInstanceState == null) {
      AppMediator.resetInstance();
    }
    */

    // do the setup
    QuestionScreen.configure(this);

    if(savedInstanceState == null) {
      presenter.onStart();

    } else {
      presenter.onRestart();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    // load the answer
    presenter.onResume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    presenter.onDestroy();
  }

  @Override
  public void navigateToCheatScreen() {

    Intent intent = new Intent(this, CheatActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  @Override
  public void displayQuestion(QuestionViewModel viewModel) {
    //Log.e(TAG, "displayQuestion()");

    // deal with the answer
    question.setText(viewModel.question);
    option1.setText(viewModel.option1);
    option2.setText(viewModel.option2);
    option3.setText(viewModel.option3);

    option1.setEnabled(viewModel.optionEnabled);
    option2.setEnabled(viewModel.optionEnabled);
    option3.setEnabled(viewModel.optionEnabled);
    nextButton.setEnabled(viewModel.nextEnabled);
    cheatButton.setEnabled(viewModel.cheatEnabled);
  }

  @Override
  public void resetReply() {
    ((TextView) findViewById(R.id.replyTextView))
        .setText(R.string.empty_reply);
  }

  @Override
  public void updateReply(boolean isCorrect) {
    if(isCorrect){
      ((TextView) findViewById(R.id.replyTextView))
              .setText(R.string.correct_reply);
    } else {
      ((TextView) findViewById(R.id.replyTextView))
              .setText(R.string.incorrect_reply);
    }
  }


  public void onNextButtonClicked(View view) {
    presenter.onNextButtonClicked();
  }

  public void onCheatButtonClicked(View view) {

    presenter.onCheatButtonClicked();

  }

  public void onOptionButtonClicked(View view) {

    int option = Integer.valueOf((String) view.getTag());
    presenter.onOptionButtonClicked(option);
  }


  @Override
  public void injectPresenter(QuestionContract.Presenter presenter) {
    this.presenter = presenter;
  }
}
