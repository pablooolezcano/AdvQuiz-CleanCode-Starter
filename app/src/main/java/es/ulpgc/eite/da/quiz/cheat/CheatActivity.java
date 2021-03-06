package es.ulpgc.eite.da.quiz.cheat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.ulpgc.eite.da.quiz.R;

public class CheatActivity
    extends AppCompatActivity implements CheatContract.View {

  public static String TAG = CheatActivity.class.getSimpleName();

  private CheatContract.Presenter presenter;

  Button yesButton, noButton;
  TextView warningText, answerText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    getSupportActionBar().setTitle(R.string.cheat_title);

    yesButton = findViewById(R.id.yesButton);
    noButton = findViewById(R.id.noButton);
    warningText = findViewById(R.id.warningTextView);
    answerText = findViewById(R.id.answerTextView);
    //
    yesButton.setText(R.string.yes_button);
    noButton.setText(R.string.no_button);
    warningText.setText(R.string.warning_message);
    // do the setup
    CheatScreen.configure(this);

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
  public void onBackPressed() {
    super.onBackPressed();

    presenter.onBackPressed();
  }


  @Override
  public void onFinish() {
    finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    presenter.onDestroy();
  }

  @Override
  public void displayAnswer(CheatViewModel viewModel) {
    //Log.e(TAG, viewModel.answer);

    // deal with the answer
    answerText.setText(viewModel.answer);
    noButton.setEnabled(viewModel.noButton);
    yesButton.setEnabled(viewModel.yesButton);
  }

  @Override
  public void resetAnswer() {
    ((TextView) findViewById(R.id.answerTextView))
        .setText(R.string.empty_answer);
  }



  public void onWarningButtonClicked(View view) {

    int option = Integer.valueOf((String) view.getTag());
    presenter.onWarningButtonClicked(option);
  }

  @Override
  public void injectPresenter(CheatContract.Presenter presenter) {
    this.presenter = presenter;
  }

}
